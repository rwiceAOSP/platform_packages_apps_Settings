package com.google.android.settings.biometrics.udfps.ui.viewmodel

import android.hardware.fingerprint.FingerprintEnrollOptions
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.settings.biometrics.fingerprint2.domain.interactor.AccessibilityInteractor
import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintSensorInteractor
import com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractor
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.CanEnrollFingerprintsInteractor
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.EnrollFingerprintInteractor
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.EnrolledFingerprintsInteractor
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.UserInteractor
import com.android.settings.biometrics.fingerprint2.lib.model.EnrollReason
import com.android.settings.biometrics.fingerprint2.lib.model.FingerEnrollState
import com.android.systemui.biometrics.shared.model.FingerprintSensor
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintEnrollStageThresholdInteractor
import com.google.android.settings.biometrics.fingerprint.interactor.SafetySourceUpdater
import com.google.android.settings.biometrics.fingerprint.ui.model.CredentialModel
import com.google.android.settings.biometrics.udfps.ui.model.EnrollStage
import kotlin.math.roundToInt
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class EnrollUdfpsViewModel : ViewModel() {
    abstract val progressFlow: StateFlow<FingerEnrollState.EnrollProgress?>
    abstract val stageFlow: StateFlow<EnrollStage>
    abstract val isStageHalfCompletedFlow: StateFlow<Boolean>
    abstract val helpFlow: SharedFlow<FingerEnrollState.EnrollHelp?>
    abstract val errorFlow: SharedFlow<FingerEnrollState.EnrollError?>
    abstract val acquiredFlow: SharedFlow<FingerEnrollState.Acquired?>
    abstract val pointerDownFlow: SharedFlow<FingerEnrollState.PointerDown?>
    abstract val pointerUpFlow: SharedFlow<FingerEnrollState.PointerUp?>
    abstract val rotation: Flow<Int>
    abstract val isSuw: Boolean
    abstract val isFastEnroll: Boolean

    abstract fun startEnroll(): Boolean

    abstract fun cancelEnroll()

    abstract fun isEnrolling(): Boolean

    abstract suspend fun getSensorProp(): FingerprintSensor

    abstract fun isAccessibilityEnabled(): Boolean

    abstract suspend fun isEnrollable(): Boolean
}

class EnrollUdfpsViewModelImpl(
    private val safetySourceUpdater: SafetySourceUpdater,
    override val isSuw: Boolean,
    override val isFastEnroll: Boolean,
    private val enrollReason: Int,
    private val credentialModel: CredentialModel,
    userInteractor: UserInteractor,
    private val sensorInteractor: FingerprintSensorInteractor,
    private val enrollStateThresholdInteractor: FingerprintEnrollStageThresholdInteractor,
    orientationInteractor: OrientationInteractor,
    private val accessibilityInteractor: AccessibilityInteractor,
    private val enroll2Interactor: EnrollFingerprintInteractor,
    private val enrolledFingerprintsInteractor: EnrolledFingerprintsInteractor,
    private val canEnrollFingerprintsInteractor: CanEnrollFingerprintsInteractor,
) : EnrollUdfpsViewModel() {

    private val _progressFlow = MutableStateFlow<FingerEnrollState.EnrollProgress?>(null)
    override val progressFlow: StateFlow<FingerEnrollState.EnrollProgress?> =
        _progressFlow.asStateFlow()

    override val stageFlow: StateFlow<EnrollStage> =
        flow {
                _progressFlow.collect { progress ->
                    if (progress == null) {
                        emit(EnrollStage.UNKNOWN)
                    } else {
                        val totalSteps = progress.totalStepsRequired
                        val remainingSteps = progress.remainingSteps
                        val completedSteps = totalSteps - remainingSteps
                        val centerThreshold =
                            (totalSteps *
                                    enrollStateThresholdInteractor.getThreshold(
                                        EnrollStage.CENTER.value
                                    ))
                                .roundToInt()
                        val guidedThreshold =
                            (totalSteps *
                                    enrollStateThresholdInteractor.getThreshold(
                                        EnrollStage.GUIDED.value
                                    ))
                                .roundToInt()
                        val fingertipThreshold =
                            (totalSteps *
                                    enrollStateThresholdInteractor.getThreshold(
                                        EnrollStage.FINGERTIP.value
                                    ))
                                .roundToInt()
                        val leftEdgeThreshold =
                            (totalSteps *
                                    enrollStateThresholdInteractor.getThreshold(
                                        EnrollStage.LEFT_EDGE.value
                                    ))
                                .roundToInt()
                        val rightEdgeThreshold =
                            (totalSteps *
                                    enrollStateThresholdInteractor.getThreshold(
                                        EnrollStage.RIGHT_EDGE.value
                                    ))
                                .roundToInt()

                        val stage =
                            when {
                                completedSteps < centerThreshold -> EnrollStage.CENTER
                                completedSteps < guidedThreshold -> EnrollStage.GUIDED
                                completedSteps < fingertipThreshold -> EnrollStage.FINGERTIP
                                completedSteps < leftEdgeThreshold -> EnrollStage.LEFT_EDGE
                                completedSteps < rightEdgeThreshold -> EnrollStage.RIGHT_EDGE
                                else -> EnrollStage.UNKNOWN
                            }
                        emit(stage)
                    }
                }
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, EnrollStage.UNKNOWN)

    override val isStageHalfCompletedFlow: StateFlow<Boolean> =
        flow {
                _progressFlow.collect { progress ->
                    if (progress == null) {
                        emit(false)
                    } else {
                        val completedSteps = progress.totalStepsRequired - progress.remainingSteps
                        var previousThreshold = 0
                        var halfCompleted = true
                        for (stage in EnrollStage.POSITIVE_STAGES) {
                            val threshold = getThresholdSteps(progress, stage)
                            if (previousThreshold <= completedSteps && completedSteps < threshold) {
                                halfCompleted =
                                    completedSteps - previousThreshold >=
                                        (threshold - previousThreshold) / 2
                                emit(halfCompleted)
                                return@collect
                            }
                            previousThreshold = threshold
                        }
                        emit(halfCompleted)
                    }
                }
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private fun getThresholdSteps(
        progress: FingerEnrollState.EnrollProgress,
        stage: EnrollStage,
    ): Int =
        (progress.totalStepsRequired * enrollStateThresholdInteractor.getThreshold(stage.value))
            .roundToInt()

    private val _helpFlow = MutableSharedFlow<FingerEnrollState.EnrollHelp?>(replay = 1)
    override val helpFlow: SharedFlow<FingerEnrollState.EnrollHelp?> = _helpFlow.asSharedFlow()

    private val _errorFlow = MutableSharedFlow<FingerEnrollState.EnrollError?>(replay = 1)
    override val errorFlow: SharedFlow<FingerEnrollState.EnrollError?> = _errorFlow.asSharedFlow()

    private val _acquiredFlow = MutableSharedFlow<FingerEnrollState.Acquired?>(replay = 1)
    override val acquiredFlow: SharedFlow<FingerEnrollState.Acquired?> =
        _acquiredFlow.asSharedFlow()

    private val _pointerDownFlow = MutableSharedFlow<FingerEnrollState.PointerDown?>(replay = 1)
    override val pointerDownFlow: SharedFlow<FingerEnrollState.PointerDown?> =
        _pointerDownFlow.asSharedFlow()

    private val _pointerUpFlow = MutableSharedFlow<FingerEnrollState.PointerUp?>(replay = 1)
    override val pointerUpFlow: SharedFlow<FingerEnrollState.PointerUp?> =
        _pointerUpFlow.asSharedFlow()

    override val rotation: Flow<Int> = orientationInteractor.rotation.distinctUntilChanged()

    private var enrollingJob: Job? = null

    init {
        userInteractor.updateUser(credentialModel.userId)
        viewModelScope.launch { _helpFlow.emit(null) }
    }

    override fun startEnroll(): Boolean {
        val token = credentialModel.token
        if (token == null) {
            Log.e(TAG, "Null hardware auth token for enroll")
            return false
        }
        enrollingJob?.cancel(CancellationException("RestartEnroll"))
        enrollingJob = viewModelScope.launch {
            enroll2Interactor
                .enroll(
                    token,
                    EnrollReason.EnrollEnrolling,
                    FingerprintEnrollOptions.Builder().setEnrollReason(enrollReason).build(),
                )
                .collect { state ->
                    when (state) {
                        is FingerEnrollState.EnrollProgress -> {
                            _progressFlow.value = state
                            if (state.remainingSteps == 0) {
                                safetySourceUpdater.onBiometricsChanged()
                            }
                        }
                        is FingerEnrollState.EnrollHelp -> {
                            _helpFlow.emit(state)
                        }
                        is FingerEnrollState.EnrollError -> {
                            _errorFlow.emit(state)
                        }
                        is FingerEnrollState.Acquired -> {
                            _acquiredFlow.emit(state)
                        }
                        is FingerEnrollState.PointerDown -> {
                            _pointerDownFlow.emit(state)
                        }
                        is FingerEnrollState.PointerUp -> {
                            _pointerUpFlow.emit(state)
                        }
                        else -> {}
                    }
                }
        }
        return true
    }

    override fun cancelEnroll() {
        enrollingJob?.cancel(CancellationException("CancelEnroll"))
        enrollingJob = null
    }

    override fun isEnrolling(): Boolean = enrollingJob != null

    override suspend fun getSensorProp(): FingerprintSensor {
        return sensorInteractor.fingerprintSensor.first()
    }

    override fun isAccessibilityEnabled(): Boolean {
        return accessibilityInteractor.isEnabled
    }

    override suspend fun isEnrollable(): Boolean {
        val enrolledList = enrolledFingerprintsInteractor.enrolledFingerprints.firstOrNull()
        val count = enrolledList?.size ?: 0
        val max = canEnrollFingerprintsInteractor.maxFingerprintsEnrollable.first()
        return count < max
    }

    companion object {
        private const val TAG = "EnrollUdfpsViewModel"
    }
}

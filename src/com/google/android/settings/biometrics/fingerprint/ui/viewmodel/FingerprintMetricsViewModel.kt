package com.google.android.settings.biometrics.fingerprint.ui.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.settings.biometrics.BiometricsOnboardingProto.OnboardingAction
import com.android.settings.biometrics.BiometricsOnboardingProto.OnboardingScreen
import com.android.settings.biometrics.metrics.BiometricsLogger
import com.android.settings.biometrics.metrics.OnboardingEvent
import com.android.settings.biometrics.metrics.OnboardingScreenInfoEvent
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintNumOfEnrolledInteractor
import com.google.android.settings.biometrics.fingerprint.interactor.ScreenProtectorInteractor
import com.google.android.settings.biometrics.fingerprint.interactor.Sp001AllowListInteractor
import com.google.android.settings.biometrics.fingerprint.model.CapybaraMetricsStatus
import com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequest
import java.time.Clock

open class FingerprintMetricsViewModel : ViewModel() {
    var skipNextCancelAction: Boolean = false

    open fun appendAction(action: OnboardingAction) {}

    open fun setScreen(screen: OnboardingScreen) {}

    open fun sendMetricsToLogger(resultCode: Int): OnboardingEvent = OnboardingEvent()
}

class FingerprintMetricsViewModelImpl(
    request: EnrollmentRequest,
    userId: Int,
    private val clock: Clock,
    private val numOfEnrolledInteractor: FingerprintNumOfEnrolledInteractor,
    private val spInteractor: ScreenProtectorInteractor,
    private val sp001AllowListInteractor: Sp001AllowListInteractor,
    private val biometricsLogger: BiometricsLogger?,
) : FingerprintMetricsViewModel() {

    private val onboardingEvent = OnboardingEvent()
    private var screenInfoBuilder: ScreenInfoBuilder? = null
    private val startMillis: Long = clock.millis()

    init {
        onboardingEvent.modality = MODALITY_FINGERPRINT
        onboardingEvent.fromSource =
            when {
                request.isSuw -> FROM_SOURCE_SUW
                request.isFromSafetySource -> FROM_SOURCE_SAFETY_SOURCE
                request.isFromFrrNotification -> FROM_SOURCE_FRR_NOTIFICATION
                else -> FROM_SOURCE_SETTINGS
            }
        onboardingEvent.userId = userId
    }

    override fun setScreen(screen: OnboardingScreen) {
        screenInfoBuilder?.let { builder ->
            if (builder.event.screen != screen.number) {
                addScreenInfo(builder, ", newScreen:${screen.number}")
            }
        }
        if (screenInfoBuilder == null) {
            screenInfoBuilder =
                ScreenInfoBuilder(
                    clock.millis(),
                    OnboardingScreenInfoEvent(screen.number, 0L, IntArray(0)),
                )
        }
    }

    override fun appendAction(action: OnboardingAction) {
        if (skipNextCancelAction && action == OnboardingAction.ACTION_CANCEL) {
            skipNextCancelAction = false
            return
        }
        val builder = screenInfoBuilder
        if (builder == null) {
            Log.d(TAG, "Null builder for $action")
            return
        }
        screenInfoBuilder =
            ScreenInfoBuilder(
                builder.startMillis,
                OnboardingScreenInfoEvent(
                    builder.event.screen,
                    0L,
                    builder.event.actions + action.number,
                ),
            )
    }

    override fun sendMetricsToLogger(resultCode: Int): OnboardingEvent {
        onboardingEvent.resultCode = toResultCodeValue(resultCode)
        onboardingEvent.enrolledCount = numOfEnrolledInteractor.getNumOfEnrolledFingerprints()
        val capybaraStatus =
            if (sp001AllowListInteractor.isEnabled()) {
                sp001AllowListInteractor
                    .getCapybaraMetricsStatus(spInteractor.screenProtector)
                    .value
            } else {
                CapybaraMetricsStatus.NOT_SUPPORTED.value
            }
        onboardingEvent.capybaraStatus = capybaraStatus
        onboardingEvent.duration = clock.millis() - startMillis
        screenInfoBuilder?.let { builder -> addScreenInfo(builder, "") }
        biometricsLogger?.logSettingsBiometricsOnboarding(onboardingEvent)
        return onboardingEvent
    }

    private fun addScreenInfo(builder: ScreenInfoBuilder, suffix: String) {
        val event =
            OnboardingScreenInfoEvent(
                builder.event.screen,
                clock.millis() - builder.startMillis,
                builder.event.actions,
            )
        Log.d(TAG, "addScreenInfo(${event.toString().trim()}$suffix)")
        onboardingEvent.addScreenInfo(event)
        screenInfoBuilder = null
    }

    private fun toResultCodeValue(resultCode: Int): Int =
        when (resultCode) {
            Activity.RESULT_OK -> RESULT_OK_VALUE
            Activity.RESULT_CANCELED -> RESULT_CANCELED_VALUE
            Activity.RESULT_FIRST_USER -> RESULT_OK_VALUE
            Activity.RESULT_FIRST_USER + 1 -> RESULT_FIRST_USER_VALUE
            Activity.RESULT_FIRST_USER + 2 -> RESULT_SECOND_USER_VALUE
            else -> RESULT_UNKNOWN_VALUE
        }

    private data class ScreenInfoBuilder(
        val startMillis: Long,
        val event: OnboardingScreenInfoEvent,
    )

    companion object {
        private const val TAG = "FingerprintMetricsViewModel"

        private const val MODALITY_FINGERPRINT = 1
        private const val FROM_SOURCE_SUW = 1
        private const val FROM_SOURCE_SETTINGS = 2
        private const val FROM_SOURCE_SAFETY_SOURCE = 3
        private const val FROM_SOURCE_FRR_NOTIFICATION = 4

        private const val RESULT_UNKNOWN_VALUE = 0
        private const val RESULT_OK_VALUE = 1
        private const val RESULT_FIRST_USER_VALUE = 2
        private const val RESULT_SECOND_USER_VALUE = 3
        private const val RESULT_CANCELED_VALUE = 4
    }
}

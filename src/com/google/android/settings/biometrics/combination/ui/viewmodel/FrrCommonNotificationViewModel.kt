package com.google.android.settings.biometrics.combination.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.android.settings.biometrics.BiometricUtils
import com.google.android.settings.biometrics.combination.ui.model.GkResult
import com.google.android.settings.biometrics.face.interactor.FaceEnrolledInteractor
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintChallengeGenerator
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintNumOfEnrolledInteractor
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintRemovalInteractor
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.LockPatternInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class FrrCommonNotificationViewModel : ViewModel() {
    abstract val userId: Int
    abstract var gkPwHandle: Long
    abstract var gkOkResult: GkResult.Ok?
    abstract val hasFingerprints: Boolean
    abstract val shouldShowFaceUnlockViews: Boolean
    abstract val shouldShowSpViews: Boolean
    abstract val shouldShowNonMfgSpText: Boolean
    abstract var isLaunchingActivity: Boolean
    abstract var isRemovingAllFingerprints: Boolean

    abstract fun isAllowFrrActivity(): Boolean

    abstract fun isButtonsClickableWhenActivityForeground(): Boolean

    abstract fun fetchGkHat(): Flow<GkResult>

    abstract fun removeAllFingerprints():
        Flow<com.google.android.settings.biometrics.fingerprint.model.FingerprintRemoval>

    abstract fun revokeChallenge()
}

class FrrCommonNotificationViewModelImpl(
    override val userId: Int,
    private val lockPatternInteractor: LockPatternInteractor,
    private val fingerprintChallengeGenerator: FingerprintChallengeGenerator,
    private val fingerprintRemovalInteractor: FingerprintRemovalInteractor,
    private val fingerprintNumOfEnrolledInteractor: FingerprintNumOfEnrolledInteractor,
    private val faceEnrolledInteractor: FaceEnrolledInteractor,
) : FrrCommonNotificationViewModel() {

    override var gkPwHandle: Long = 0L

    override var gkOkResult: GkResult.Ok? = null

    override var isLaunchingActivity: Boolean = false

    override var isRemovingAllFingerprints: Boolean = false

    override fun isAllowFrrActivity(): Boolean = !lockPatternInteractor.isUnspecifiedPassword()

    override val shouldShowFaceUnlockViews: Boolean
        get() = faceEnrolledInteractor.isSupportFaceUnlock() && !faceEnrolledInteractor.hasEnrolled

    override fun isButtonsClickableWhenActivityForeground(): Boolean =
        !isLaunchingActivity && !isRemovingAllFingerprints && gkPwHandle == 0L && gkOkResult == null

    override fun fetchGkHat(): Flow<GkResult> =
        fingerprintChallengeGenerator.generateChallenge2().map { challenge ->
            try {
                val gkHat = lockPatternInteractor.getGkHat(gkPwHandle, challenge)
                lockPatternInteractor.removeGkPwHandle(gkPwHandle)
                GkResult.Ok(challenge, gkHat) as GkResult
            } catch (e: BiometricUtils.GatekeeperCredentialNotMatchException) {
                GkResult.Failed(e)
            }
        }

    override fun revokeChallenge() {
        val challenge = gkOkResult?.challenge
        gkOkResult = null
        if (challenge != null) {
            fingerprintChallengeGenerator.revokeChallenge(challenge)
        }
    }

    override val hasFingerprints: Boolean
        get() = fingerprintNumOfEnrolledInteractor.getNumOfEnrolledFingerprints() > 0

    override fun removeAllFingerprints() = fingerprintRemovalInteractor.removeAll()

    override val shouldShowSpViews: Boolean = false

    override val shouldShowNonMfgSpText: Boolean = false
}

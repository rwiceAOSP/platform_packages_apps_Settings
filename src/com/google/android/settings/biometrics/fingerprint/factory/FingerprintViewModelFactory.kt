package com.google.android.settings.biometrics.fingerprint.factory

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.hardware.fingerprint.FingerprintManager
import android.os.UserManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.internal.widget.LockPatternUtils
import com.android.settings.Utils
import com.android.settings.biometrics.GatekeeperPasswordProvider
import com.android.settings.biometrics.ParentalControlsUtils
import com.android.settings.overlay.FeatureFactory
import com.android.settings.wifi.utils.userManager
import com.android.settingslib.RestrictedLockUtils
import com.android.settingslib.RestrictedLockUtilsInternal
import com.google.android.settings.biometrics.combination.data.repository.AccessRepository
import com.google.android.settings.biometrics.combination.data.repository.AccessRepositoryImpl
import com.google.android.settings.biometrics.combination.data.repository.VibratorRepositoryImpl
import com.google.android.settings.biometrics.combination.interactor.VibratorInteractorImpl
import com.google.android.settings.biometrics.combination.ui.viewmodel.FrrCommonNotificationViewModel
import com.google.android.settings.biometrics.combination.ui.viewmodel.FrrCommonNotificationViewModelImpl
import com.google.android.settings.biometrics.combination.ui.viewmodel.VibratorViewModel
import com.google.android.settings.biometrics.combination.ui.viewmodel.VibratorViewModelImpl
import com.google.android.settings.biometrics.face.data.repository.FaceManagerRepository
import com.google.android.settings.biometrics.face.data.repository.FaceManagerRepositoryImpl
import com.google.android.settings.biometrics.face.interactor.FaceEnrolledInteractorImpl
import com.google.android.settings.biometrics.fingerprint.data.datasource.FingerprintManagerDataSource
import com.google.android.settings.biometrics.fingerprint.data.repository.AccessibilityRepositoryImpl
import com.google.android.settings.biometrics.fingerprint.data.repository.FingerprintsRepository
import com.google.android.settings.biometrics.fingerprint.data.repository.FingerprintsRepositoryImpl
import com.google.android.settings.biometrics.fingerprint.data.repository.FrrRepository
import com.google.android.settings.biometrics.fingerprint.data.repository.FrrRepositoryImpl
import com.google.android.settings.biometrics.fingerprint.data.repository.Sp001AllowListRepositoryImpl
import com.google.android.settings.biometrics.fingerprint.interactor.CheckAccessibilityStatusInteractorImpl
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintChallengeGeneratorImpl
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintMaxTemplatesInteractorImpl
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintNumOfEnrolledInteractorImpl
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintRemovalInteractorImpl
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintSensorTypeInteractorImpl
import com.google.android.settings.biometrics.fingerprint.interactor.ScreenProtectorInteractorImpl
import com.google.android.settings.biometrics.fingerprint.interactor.Sp001AllowListInteractorImpl
import com.google.android.settings.biometrics.fingerprint.interactor.SpAccessPolicyImpl
import com.google.android.settings.biometrics.fingerprint.interactor.UsUdfpsCalibratorInteractor
import com.google.android.settings.biometrics.fingerprint.ui.model.CredentialModel
import com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequest
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.AccessibilityViewModel
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollmentViewModel
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModelImpl
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.LockPatternInteractorImpl
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.UsUdfpsCalibratorViewModel
import java.time.Clock
import kotlin.reflect.KClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class FingerprintViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        return super.create(modelClass, extras)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
        if (application == null) {
            Log.w(TAG, "create(), null application")
            return super.create(modelClass)
        }

        if (modelClass.isAssignableFrom(FingerprintMetricsViewModel::class.java)) {
            val fingerprintManager = getFingerprintManager(application)
            val enrollmentRequest = extras[ENROLLMENT_REQUEST_KEY]
            val credentialModel = extras[CREDENTIAL_MODEL_KEY]
            val userManager = application.userManager
            if (fingerprintManager != null && enrollmentRequest != null && credentialModel != null && userManager != null) {
                val resources = application.resources
                val fingerprintsRepository = FingerprintRepositoryFactory.getFingerprintsRepository(fingerprintManager, resources)
                val accessRepository = AccessRepositoryImpl.getInstance(userManager)
                val frrRepository = FrrRepositoryImpl.getInstance(UdfpsFingerprintExtSupplier)
                val userId = credentialModel.userId
                val clock = Clock.systemUTC()
                val fingerprintNumOfEnrolledInteractor = FingerprintNumOfEnrolledInteractorImpl(credentialModel.userId, fingerprintsRepository)
                val screenProtectorInteractor = ScreenProtectorInteractorImpl(frrRepository, accessRepository)
                val resources2 = application.resources
                return FingerprintMetricsViewModelImpl(
                    enrollmentRequest,
                    userId,
                    clock,
                    fingerprintNumOfEnrolledInteractor,
                    screenProtectorInteractor,
                    Sp001AllowListInteractorImpl(Sp001AllowListRepositoryImpl.getInstance(resources2)),
                    FeatureFactory.featureFactory.biometricsFeatureProvider.biometricsLogger
                ) as T
            }
            return FingerprintMetricsViewModel() as T
        }
        if (modelClass.isAssignableFrom(SetEnrollResultViewModel::class.java)) {
            return SetEnrollResultViewModel() as T
        }
        if (modelClass.isAssignableFrom(IntroViewModel::class.java)) {
            val fingerprintManager = getFingerprintManager(application)
            val enrollmentRequest = extras[ENROLLMENT_REQUEST_KEY]
            val credentialModel = extras[CREDENTIAL_MODEL_KEY]
            val enforcedAdminParentConsentRequired = ParentalControlsUtils.parentConsentRequired(application, 2)
            if (fingerprintManager != null && enrollmentRequest != null && credentialModel != null) {
                val enforcedAdminKeyguardFeaturesDisabled = RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(application, 32, credentialModel.userId)
                val resources = application.resources
                val fingerprintsRepository = FingerprintRepositoryFactory.getFingerprintsRepository(fingerprintManager, resources)
                return IntroViewModel(
                    enforcedAdminParentConsentRequired,
                    enforcedAdminKeyguardFeaturesDisabled,
                    FingerprintMaxTemplatesInteractorImpl(fingerprintsRepository),
                    FingerprintSensorTypeInteractorImpl(fingerprintsRepository),
                    FingerprintNumOfEnrolledInteractorImpl(credentialModel.userId, fingerprintsRepository),
                    enrollmentRequest
                ) as T
            }
        } else if (modelClass.isAssignableFrom(FingerprintEnrollmentViewModel::class.java)) {
            val fingerprintManager = getFingerprintManager(application)
            val enrollmentRequest = extras[ENROLLMENT_REQUEST_KEY]
            val credentialModel = extras[CREDENTIAL_MODEL_KEY]
            if (fingerprintManager != null && enrollmentRequest != null && credentialModel != null) {
                val resources = application.resources
                val fingerprintsRepository = FingerprintRepositoryFactory.getFingerprintsRepository(fingerprintManager, resources)
                val lockPatternUtils = FeatureFactory.featureFactory.securityFeatureProvider.getLockPatternUtils(application)
                val userId = credentialModel.userId
                val lockPatternInteractor = LockPatternInteractorImpl(userId, lockPatternUtils, GatekeeperPasswordProvider(lockPatternUtils))
                val fingerprintChallengeGenerator = FingerprintChallengeGeneratorImpl(credentialModel.userId, fingerprintsRepository)
                val fingerprintSensorTypeInteractor = FingerprintSensorTypeInteractorImpl(fingerprintsRepository)
                val resources2 = application.resources
                return FingerprintEnrollmentViewModel(
                    enrollmentRequest,
                    lockPatternInteractor,
                    fingerprintChallengeGenerator,
                    fingerprintSensorTypeInteractor,
                    credentialModel,
                    Sp001AllowListInteractorImpl(Sp001AllowListRepositoryImpl.getInstance(resources2))
                ) as T
            }
        } else if (modelClass.isAssignableFrom(UsUdfpsCalibratorViewModel::class.java)) {
            val enrollmentRequest = extras[ENROLLMENT_REQUEST_KEY]
            if (enrollmentRequest != null) {
                val mainThreadHandler = application.mainThreadHandler
                return UsUdfpsCalibratorViewModel(
                    UsUdfpsCalibratorInteractor(mainThreadHandler, enrollmentRequest.calibratorUuid),
                    enrollmentRequest
                ) as T
            }
        } else if (modelClass.isAssignableFrom(AccessibilityViewModel::class.java)) {
            return AccessibilityViewModel(CheckAccessibilityStatusInteractorImpl(AccessibilityRepositoryImpl(application))) as T
        } else if (modelClass.isAssignableFrom(FrrCommonNotificationViewModel::class.java)) {
            val userId = application.userId
            val fingerprintManager = getFingerprintManager(application)
            val userManager = application.getSystemService(UserManager::class.java)
            if (fingerprintManager != null && userManager != null) {
                val fingerprintManagerDataSource = FingerprintDataSourceFactory.getFingerprintManagerDataSource(fingerprintManager)
                val resources = application.resources
                val fingerprintsRepository = FingerprintsRepositoryImpl.getInstance(
                    fingerprintManagerDataSource,
                    FingerprintDataSourceFactory.getResourcesDataSource(resources)
                )
                val faceManagerRepository = FaceManagerRepositoryImpl.getInstance(userId, Utils.getFaceManagerOrNull(application))
                val lockPatternUtils = LockPatternUtils(application)
                val accessRepository = AccessRepositoryImpl.getInstance(userManager)
                val frrRepository = FrrRepositoryImpl.getInstance(UdfpsFingerprintExtSupplier)
                val lockPatternInteractor = LockPatternInteractorImpl(userId, lockPatternUtils, GatekeeperPasswordProvider(lockPatternUtils))
                val fingerprintChallengeGenerator = FingerprintChallengeGeneratorImpl(userId, fingerprintsRepository)
                val fingerprintRemovalInteractor = FingerprintRemovalInteractorImpl(userId, fingerprintsRepository)
                val fingerprintNumOfEnrolledInteractor = FingerprintNumOfEnrolledInteractorImpl(userId, fingerprintsRepository)
                val faceEnrolledInteractor = FaceEnrolledInteractorImpl(faceManagerRepository)
                val spAccessPolicy = SpAccessPolicyImpl(accessRepository)
                val screenProtectorInteractor = ScreenProtectorInteractorImpl(frrRepository, accessRepository)
                val resources2 = application.resources
                return FrrCommonNotificationViewModelImpl(
                    userId,
                    lockPatternInteractor,
                    fingerprintChallengeGenerator,
                    fingerprintRemovalInteractor,
                    fingerprintNumOfEnrolledInteractor,
                    faceEnrolledInteractor,
                    spAccessPolicy,
                    screenProtectorInteractor,
                    Sp001AllowListInteractorImpl(Sp001AllowListRepositoryImpl.getInstance(resources2))
                ) as T
            }
        } else if (modelClass.isAssignableFrom(VibratorViewModel::class.java)) {
            return VibratorViewModelImpl(VibratorInteractorImpl(VibratorRepositoryImpl(application))) as T
        }

        Log.e(TAG, "create(), missing factory method for $modelClass")
        return create(modelClass)
    }

    protected open fun getFingerprintManager(context: Context): FingerprintManager? {
        return Utils.getFingerprintManagerOrNull(context)
    }

    companion object {
        private const val TAG = "FingerprintViewModelFactory"

        private val appScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

        val ENROLLMENT_REQUEST_KEY: CreationExtras.Key<EnrollmentRequest> = object : CreationExtras.Key<EnrollmentRequest> {}
        val CREDENTIAL_MODEL_KEY: CreationExtras.Key<CredentialModel> = object : CreationExtras.Key<CredentialModel> {}
    }
}

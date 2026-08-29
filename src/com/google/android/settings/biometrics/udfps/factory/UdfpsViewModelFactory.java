package com.google.android.settings.biometrics.udfps.factory;

import android.app.Application;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.settings.SettingsApplication;
import com.android.settings.biometrics.fingerprint2.BiometricsEnvironment;
import com.google.android.settings.biometrics.fingerprint.data.datasource.FingerprintManagerDataSource;
import com.google.android.settings.biometrics.fingerprint.data.repository.FingerprintsRepositoryImpl;
import com.google.android.settings.biometrics.fingerprint.factory.FingerprintDataSourceFactory;
import com.google.android.settings.biometrics.fingerprint.factory.FingerprintViewModelFactory;
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintEnrollStageThresholdInteractorImpl;
import com.google.android.settings.biometrics.fingerprint.interactor.SafetySourceUpdaterImpl;
import com.google.android.settings.biometrics.fingerprint.ui.model.CredentialModel;
import com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequest;
import com.google.android.settings.biometrics.udfps.ui.viewmodel.ConfirmUdfpsViewModel;
import com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel;
import com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModelImpl;
import com.google.android.settings.biometrics.udfps.ui.viewmodel.FindUdfpsViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: UdfpsViewModelFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
public class UdfpsViewModelFactory extends FingerprintViewModelFactory {
    public static final Companion Companion = new Companion(null);

    @Override // com.google.android.settings.biometrics.fingerprint.factory.FingerprintViewModelFactory, androidx.lifecycle.ViewModelProvider.Factory
    public ViewModel create(Class cls, CreationExtras creationExtras) {
        cls.getClass();
        creationExtras.getClass();
        Application application = (Application) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
        if (application == null) {
            Log.w("UdfpsViewModelFactory", "create(), null application");
            return super.create(cls, creationExtras);
        }
        if (cls.isAssignableFrom(FindUdfpsViewModel.class)) {
            EnrollmentRequest enrollmentRequest = (EnrollmentRequest) creationExtras.get(FingerprintViewModelFactory.Companion.getENROLLMENT_REQUEST_KEY());
            BiometricsEnvironment biometricEnvironment = ((SettingsApplication) application).getBiometricEnvironment();
            if (enrollmentRequest != null && biometricEnvironment != null) {
                return new FindUdfpsViewModel(enrollmentRequest.isSuw());
            }
        } else if (cls.isAssignableFrom(EnrollUdfpsViewModel.class)) {
            FingerprintViewModelFactory.Companion companion = FingerprintViewModelFactory.Companion;
            EnrollmentRequest enrollmentRequest2 = (EnrollmentRequest) creationExtras.get(companion.getENROLLMENT_REQUEST_KEY());
            CredentialModel credentialModel = (CredentialModel) creationExtras.get(companion.getCREDENTIAL_MODEL_KEY());
            SettingsApplication settingsApplication = (SettingsApplication) application;
            BiometricsEnvironment biometricEnvironment2 = settingsApplication.getBiometricEnvironment();
            FingerprintManager fingerprintManager = getFingerprintManager(application);
            if (enrollmentRequest2 != null && credentialModel != null && biometricEnvironment2 != null && fingerprintManager != null) {
                FingerprintsRepositoryImpl.Companion companion2 = FingerprintsRepositoryImpl.Companion;
                FingerprintDataSourceFactory fingerprintDataSourceFactory = FingerprintDataSourceFactory.INSTANCE;
                FingerprintManagerDataSource fingerprintManagerDataSource = fingerprintDataSourceFactory.getFingerprintManagerDataSource(fingerprintManager);
                Resources resources = settingsApplication.getResources();
                resources.getClass();
                return new EnrollUdfpsViewModelImpl(new SafetySourceUpdaterImpl(application), enrollmentRequest2.isSuw(), enrollmentRequest2.isFastEnroll(), enrollmentRequest2.getEnrollReason(), credentialModel, biometricEnvironment2.createUserInteractor(), biometricEnvironment2.getSensorInteractor(), new FingerprintEnrollStageThresholdInteractorImpl(companion2.getInstance(fingerprintManagerDataSource, fingerprintDataSourceFactory.getResourcesDataSource(resources))), biometricEnvironment2.getOrientationInteractor(), biometricEnvironment2.createAccessibilityInteractor(), biometricEnvironment2.createFingerprintEnrollInteractor(), biometricEnvironment2.createFingerprintsEnrolledInteractor(), biometricEnvironment2.createCanEnrollFingerprintsInteractor());
            }
        } else if (cls.isAssignableFrom(ConfirmUdfpsViewModel.class)) {
            FingerprintViewModelFactory.Companion companion3 = FingerprintViewModelFactory.Companion;
            EnrollmentRequest enrollmentRequest3 = (EnrollmentRequest) creationExtras.get(companion3.getENROLLMENT_REQUEST_KEY());
            CredentialModel credentialModel2 = (CredentialModel) creationExtras.get(companion3.getCREDENTIAL_MODEL_KEY());
            BiometricsEnvironment biometricEnvironment3 = ((SettingsApplication) application).getBiometricEnvironment();
            if (enrollmentRequest3 != null && credentialModel2 != null && biometricEnvironment3 != null) {
                return new ConfirmUdfpsViewModel(enrollmentRequest3.isSuw(), credentialModel2.getUserId(), biometricEnvironment3.createUserInteractor(), biometricEnvironment3.createFingerprintsEnrolledInteractor(), biometricEnvironment3.createCanEnrollFingerprintsInteractor());
            }
        }
        Log.e("UdfpsViewModelFactory", "create(), missing factory method for " + cls);
        return create(cls);
    }

    /* JADX INFO: compiled from: UdfpsViewModelFactory.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.google.android.settings.biometrics.fingerprint.feature.UdfpsEnrollCalibratorImpl;
import com.google.android.settings.biometrics.fingerprint.interactor.UsUdfpsCalibratorInteractor;
import com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequest;

/* JADX INFO: compiled from: UsUdfpsCalibratorViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class UsUdfpsCalibratorViewModel extends ViewModel {
    private final UsUdfpsCalibratorInteractor calibratorInteractor;
    private final EnrollmentRequest request;

    public UsUdfpsCalibratorViewModel(UsUdfpsCalibratorInteractor usUdfpsCalibratorInteractor, EnrollmentRequest enrollmentRequest) {
        usUdfpsCalibratorInteractor.getClass();
        enrollmentRequest.getClass();
        this.calibratorInteractor = usUdfpsCalibratorInteractor;
        this.request = enrollmentRequest;
        if (usUdfpsCalibratorInteractor.getCalibrator() != null) {
            UdfpsEnrollCalibratorImpl calibrator = usUdfpsCalibratorInteractor.getCalibrator();
            enrollmentRequest.setCalibratorUuid(calibrator != null ? calibrator.getUuid() : null);
        }
    }
}

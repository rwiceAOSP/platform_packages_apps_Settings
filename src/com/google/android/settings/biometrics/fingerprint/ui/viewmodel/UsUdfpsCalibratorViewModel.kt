package com.google.android.settings.biometrics.fingerprint.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.settings.biometrics.fingerprint.interactor.UsUdfpsCalibratorInteractor
import com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequest

class UsUdfpsCalibratorViewModel(
    private val calibratorInteractor: UsUdfpsCalibratorInteractor,
    private val request: EnrollmentRequest,
) : ViewModel() {
    init {
        if (calibratorInteractor.calibrator != null) {
            request.calibratorUuid = calibratorInteractor.calibrator?.uuid
        }
    }
}

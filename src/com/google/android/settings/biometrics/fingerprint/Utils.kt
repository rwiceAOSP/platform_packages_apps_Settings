package com.google.android.settings.biometrics.fingerprint

import android.util.Log

object Utils {

    @JvmStatic
    fun resumeEnroll() {
        Log.d(TAG, "resumeEnroll: no-op for generic HAL")
    }

    private const val TAG = "BiometricUtil"
}

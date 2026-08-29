package com.google.android.settings.biometrics;

import android.content.Context;
import android.safetycenter.SafetySourceIssue;
import com.android.settings.biometrics.BiometricsFeatureProvider;
import com.android.settings.biometrics.metrics.BiometricsLogger;
import com.google.android.settings.biometrics.metrics.BiometricsLoggerImpl;

public class BiometricsFeatureProviderGoogleImpl implements BiometricsFeatureProvider {
    private Context mContext;
    private BiometricsSafetySourceIssueController mBiometricsSafetySourceIssueController = null;
    private BiometricsLogger mBiometricsLogger = null;

    public BiometricsFeatureProviderGoogleImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public SafetySourceIssue getSafetySourceIssue(String str) {
        return getBiometricsSafetySourceIssueController().getSafetySourceIssue(str);
    }

    @Override
    public void notifySafetyIssueActionLaunched() {
        getBiometricsSafetySourceIssueController().notifySafetyIssueActionLaunched();
    }

    private BiometricsSafetySourceIssueController getBiometricsSafetySourceIssueController() {
        if (this.mBiometricsSafetySourceIssueController == null) {
            this.mBiometricsSafetySourceIssueController = new BiometricsSafetySourceIssueController(this.mContext);
        }
        return this.mBiometricsSafetySourceIssueController;
    }

    @Override
    public BiometricsLogger getBiometricsLogger() {
        if (this.mBiometricsLogger == null) {
            this.mBiometricsLogger = new BiometricsLoggerImpl();
        }
        return this.mBiometricsLogger;
    }
}

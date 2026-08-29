package com.google.android.settings.biometrics.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.util.Log;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollActivityClassProvider;
import com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider;
import com.android.settings.biometrics.fingerprint.FingerprintSettingsFeatureProvider;
import com.android.settings.biometrics.fingerprint.UdfpsEnrollCalibrator;
import com.android.settings.biometrics.fingerprint.feature.FingerprintExtPreferencesProvider;
import com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature;
import com.google.android.settings.R$bool;
import com.google.android.settings.biometrics.fingerprint.factory.DynamicClassLoader;
import com.google.android.settings.biometrics.fingerprint.feature.FingerprintActivityProviderWithoutFastEnroll;
import com.google.android.settings.biometrics.fingerprint.feature.FingerprintEnrollActivityClassProviderGoogleImpl;
import com.google.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeatureGoogleImpl;
import com.google.android.settings.biometrics.fingerprint.feature.UdfpsEnrollCalibratorImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class FingerprintFeatureProviderGoogleImpl implements FingerprintFeatureProvider {
    private static final String TAG = "FingerprintFeatureProviderGoogleImpl";
    private SfpsEnrollmentFeature mSfpsEnrollmentFeatureImpl = null;

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider
    public SfpsEnrollmentFeature getSfpsEnrollmentFeature() {
        if (this.mSfpsEnrollmentFeatureImpl == null) {
            this.mSfpsEnrollmentFeatureImpl = new SfpsEnrollmentFeatureGoogleImpl();
            Log.v(TAG, "getSfpsEnrollmentFeature: impl=" + this.mSfpsEnrollmentFeatureImpl + ", flag=true");
        }
        return this.mSfpsEnrollmentFeatureImpl;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider
    public UdfpsEnrollCalibrator getUdfpsEnrollCalibrator(Context context, Bundle bundle, Intent intent) {
        if (context.getResources().getBoolean(R$bool.config_fingerprint_enroll_calibration)) {
            return UdfpsEnrollCalibratorImpl.getInstance(context.getMainThreadHandler(), bundle, intent);
        }
        return null;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider
    public FingerprintEnrollActivityClassProvider getEnrollActivityClassProvider(Context context) {
        FingerprintManager fingerprintManagerOrNull = com.android.settings.Utils.getFingerprintManagerOrNull(context);
        if (fingerprintManagerOrNull != null && (fingerprintManagerOrNull.isPowerbuttonFps() || isUdfps(fingerprintManagerOrNull))) {
            return FingerprintEnrollActivityClassProviderGoogleImpl.INSTANCE;
        }
        return FingerprintActivityProviderWithoutFastEnroll.INSTANCE;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider
    public FingerprintExtPreferencesProvider getExtPreferenceProvider(Context context) {
        List sensorPropertiesInternal;
        FingerprintExtPreferencesProvider fingerprintExtPreferencesProviderNewFingerprintExtPreferencesProvider;
        FingerprintManager fingerprintManagerOrNull = com.android.settings.Utils.getFingerprintManagerOrNull(context);
        return (fingerprintManagerOrNull == null || (sensorPropertiesInternal = fingerprintManagerOrNull.getSensorPropertiesInternal()) == null || sensorPropertiesInternal.isEmpty() || ((FingerprintSensorPropertiesInternal) sensorPropertiesInternal.get(0)).sensorType != 2 || (fingerprintExtPreferencesProviderNewFingerprintExtPreferencesProvider = DynamicClassLoader.INSTANCE.newFingerprintExtPreferencesProvider("com.google.android.settings.biometrics.usudfps.feature.UsudfpsExtPreferencesProvider", context)) == null) ? super.getExtPreferenceProvider(context) : fingerprintExtPreferencesProviderNewFingerprintExtPreferencesProvider;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider
    public FingerprintSettingsFeatureProvider getFingerprintSettingsFeatureProvider() {
        return FingerprintSettingsFeatureProviderGoogle.INSTANCE;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider
    public List getChallengeGeneratedInvokers() {
        ArrayList arrayList = new ArrayList(1);
        DynamicClassLoader.INSTANCE.newChallengeGeneratedInvoker("com.google.android.settings.biometrics.usudfps.feature.ScreenProtectorInvoker");
        return arrayList;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider
    public Class getParentalConsentPage() {
        return FingerprintEnrollParentalConsentGoogle.class;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider
    public int[] getParentalConsentStringRes() {
        return FingerprintEnrollParentalConsentGoogle.CONSENT_STRING_RESOURCES;
    }

    private boolean isUdfps(FingerprintManager fingerprintManager) {
        Iterator it = fingerprintManager.getSensorPropertiesInternal().iterator();
        while (it.hasNext()) {
            if (((FingerprintSensorPropertiesInternal) it.next()).isAnyUdfpsType()) {
                return true;
            }
        }
        return false;
    }
}

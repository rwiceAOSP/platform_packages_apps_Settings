package com.google.android.settings.biometrics.fingerprint

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.android.settings.biometrics.fingerprint.FingerprintEnrollActivityClassProvider
import com.android.settings.biometrics.fingerprint.FingerprintEnrollParentalConsent
import com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider
import com.android.settings.biometrics.fingerprint.FingerprintSettingsFeatureProvider
import com.android.settings.biometrics.fingerprint.UdfpsEnrollCalibrator
import com.android.settings.biometrics.fingerprint.feature.ChallengeGeneratedInvoker
import com.android.settings.biometrics.fingerprint.feature.FingerprintExtPreferencesProvider
import com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
import com.google.android.settings.biometrics.fingerprint.feature.FingerprintEnrollActivityClassProviderGoogleImpl
import com.google.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeatureGoogleImpl

class FingerprintFeatureProviderGoogleImpl : FingerprintFeatureProvider {

    private var sfpsEnrollmentFeatureImpl: SfpsEnrollmentFeature? = null

    override fun getSfpsEnrollmentFeature(): SfpsEnrollmentFeature {
        if (sfpsEnrollmentFeatureImpl == null) {
            sfpsEnrollmentFeatureImpl = SfpsEnrollmentFeatureGoogleImpl()
            Log.v(TAG, "getSfpsEnrollmentFeature: impl=$sfpsEnrollmentFeatureImpl, flag=true")
        }
        return sfpsEnrollmentFeatureImpl!!
    }

    override fun getUdfpsEnrollCalibrator(
        context: Context,
        savedInstanceState: Bundle?,
        intent: Intent?,
    ): UdfpsEnrollCalibrator? = null

    override fun getEnrollActivityClassProvider(
        context: Context
    ): FingerprintEnrollActivityClassProvider {
        return FingerprintEnrollActivityClassProviderGoogleImpl
    }

    override fun getExtPreferenceProvider(context: Context): FingerprintExtPreferencesProvider {
        return super.getExtPreferenceProvider(context)
    }

    override fun getFingerprintSettingsFeatureProvider(): FingerprintSettingsFeatureProvider {
        return FingerprintSettingsFeatureProviderGoogle
    }

    override fun getChallengeGeneratedInvokers(): List<ChallengeGeneratedInvoker> {
        return emptyList()
    }

    override fun getParentalConsentPage(): Class<out FingerprintEnrollParentalConsent> {
        return FingerprintEnrollParentalConsentGoogle::class.java
    }

    override fun getParentalConsentStringRes(): IntArray {
        return FingerprintEnrollParentalConsentGoogle.CONSENT_STRING_RESOURCES
    }

    companion object {
        private const val TAG = "FingerprintFeatureProviderGoogleImpl"
    }
}

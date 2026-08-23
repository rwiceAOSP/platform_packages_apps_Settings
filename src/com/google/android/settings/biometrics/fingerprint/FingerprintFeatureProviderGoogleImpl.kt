package com.google.android.settings.biometrics.fingerprint

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.util.Log
import com.android.settings.Utils
import com.android.settings.biometrics.fingerprint.FingerprintEnrollActivityClassProvider
import com.android.settings.biometrics.fingerprint.FingerprintEnrollParentalConsent
import com.android.settings.biometrics.fingerprint.FingerprintFeatureProvider
import com.android.settings.biometrics.fingerprint.FingerprintSettingsFeatureProvider
import com.android.settings.biometrics.fingerprint.feature.ChallengeGeneratedInvoker
import com.android.settings.biometrics.fingerprint.feature.FingerprintExtPreferencesProvider
import com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
import com.google.android.settings.biometrics.fingerprint.factory.DynamicClassLoader
import com.google.android.settings.biometrics.fingerprint.feature.FingerprintActivityProviderWithoutFastEnroll
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

    override fun getEnrollActivityClassProvider(
        context: Context
    ): FingerprintEnrollActivityClassProvider {
        val fm = Utils.getFingerprintManagerOrNull(context)
        if (fm != null && (fm.isPowerbuttonFps || isUdfps(fm))) {
            return FingerprintEnrollActivityClassProviderGoogleImpl
        }
        return FingerprintActivityProviderWithoutFastEnroll
    }

    override fun getExtPreferenceProvider(context: Context): FingerprintExtPreferencesProvider {
        val fm = Utils.getFingerprintManagerOrNull(context)
        val props = fm?.sensorPropertiesInternal
        if (props != null && props.isNotEmpty() && props[0].sensorType == 2) {
            val provider =
                DynamicClassLoader.newFingerprintExtPreferencesProvider(
                    "com.google.android.settings.biometrics.usudfps.feature.UsudfpsExtPreferencesProvider",
                    context,
                )
            if (provider != null) {
                return provider
            }
        }
        return super.getExtPreferenceProvider(context)
    }

    override fun getFingerprintSettingsFeatureProvider(): FingerprintSettingsFeatureProvider {
        return FingerprintSettingsFeatureProviderGoogle
    }

    override fun getChallengeGeneratedInvokers(): List<ChallengeGeneratedInvoker> {
        val list = mutableListOf<ChallengeGeneratedInvoker>()
        DynamicClassLoader.newChallengeGeneratedInvoker(
            "com.google.android.settings.biometrics.usudfps.feature.ScreenProtectorInvoker"
        )
        return list
    }

    override fun getParentalConsentPage(): Class<out FingerprintEnrollParentalConsent> {
        return FingerprintEnrollParentalConsentGoogle::class.java
    }

    override fun getParentalConsentStringRes(): IntArray {
        return FingerprintEnrollParentalConsentGoogle.CONSENT_STRING_RESOURCES
    }

    private fun isUdfps(fm: FingerprintManager): Boolean {
        for (prop in fm.sensorPropertiesInternal) {
            if (prop.isAnyUdfpsType) {
                return true
            }
        }
        return false
    }

    companion object {
        private const val TAG = "FingerprintFeatureProviderGoogleImpl"
    }
}

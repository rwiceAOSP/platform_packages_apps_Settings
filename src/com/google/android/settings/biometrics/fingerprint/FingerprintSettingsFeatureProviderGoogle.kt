package com.google.android.settings.biometrics.fingerprint

import com.android.settings.R
import com.android.settings.biometrics.fingerprint.FingerprintSettingsFeatureProvider

object FingerprintSettingsFeatureProviderGoogle : FingerprintSettingsFeatureProvider() {
    override fun getSettingPageDescription(): Int {
        return R.string.security_settings_fingerprint_description
    }

    override fun getSettingPageFooterLearnMoreDescription(): Int {
        return R.string.security_settings_fingerprint_settings_footer_learn_more
    }
}

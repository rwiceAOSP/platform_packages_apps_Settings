package com.google.android.settings.biometrics.fingerprint;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settings.R;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollParentalConsent;
import com.android.settingslib.widget.theme.R$color;
import com.google.android.settings.R$string;
import com.google.android.settings.biometrics.R$drawable;
import com.google.android.settings.biometrics.R$id;
import com.google.android.settings.biometrics.R$layout;
import com.google.android.setupdesign.util.ThemeHelper;

/* JADX INFO: loaded from: classes4.dex */
public class FingerprintEnrollParentalConsentGoogle extends FingerprintEnrollParentalConsent {
    public static final int[] CONSENT_STRING_RESOURCES = {R.string.security_settings_fingerprint_enroll_consent_introduction_title, R$string.security_settings_fingerprint_enroll_consent_message, R$string.security_settings_fingerprint_enroll_consent_secure_and_helpful_message_1, com.google.android.settings.biometrics.R$string.security_settings_face_enroll_introduction_secure_and_helpful_message_2, R.string.security_settings_fingerprint_enroll_introduction_footer_title_consent_1, R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_2, R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_3, R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_4, R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_5, R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_6};

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollParentalConsent, com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollBase, com.android.settings.core.InstrumentedActivity, com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        ImageView imageView;
        super.onCreate(bundle);
        setDescriptionText(R$string.security_settings_fingerprint_enroll_consent_message);
        ((TextView) findViewById(R$id.footer_message_7)).setText(R$string.security_settings_fingerprint_enroll_consent_secure_and_helpful_message_1);
        TextView textView = (TextView) findViewById(R$id.footer_learn_more);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml(getString(com.google.android.settings.biometrics.R$string.security_settings_fingerprint_v2_enroll_introduction_message_learn_more_2), 0));
        ImageView imageView2 = (ImageView) findViewById(R$id.icon_security_privacy_safe);
        ImageView imageView3 = (ImageView) findViewById(R$id.icon_privacy_tip);
        imageView2.setColorFilter(getIconColorFilter());
        imageView3.setColorFilter(getIconColorFilter());
        if (!ThemeHelper.shouldApplyGlifExpressiveStyle(getApplicationContext()) || (imageView = (ImageView) findViewById(R$id.illustrationImage)) == null) {
            return;
        }
        imageView.setImageResource(R$drawable.fingerprint_enroll_introduction_expressive);
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollIntroduction
    protected int getLayoutResource() {
        return R$layout.fingerprint_enroll_introduction_2;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    protected PorterDuffColorFilter getIconColorFilter() {
        if (this.mIconColorFilter == null) {
            this.mIconColorFilter = new PorterDuffColorFilter(getColor(R$color.settingslib_materialColorOnSurfaceVariant), PorterDuff.Mode.SRC_IN);
        }
        return this.mIconColorFilter;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollParentalConsent, com.android.settings.biometrics.BiometricEnrollIntroduction
    protected void updateDescriptionText() {
        super.updateDescriptionText();
        setDescriptionText(R$string.security_settings_fingerprint_enroll_consent_message);
    }
}

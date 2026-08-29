package com.google.android.settings.biometrics.face;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settings.R;
import com.android.settings.biometrics.face.FaceEnrollParentalConsent;
import com.android.settingslib.widget.theme.R$color;
import com.google.android.settings.R$string;
import com.google.android.settings.biometrics.R$drawable;
import com.google.android.settings.biometrics.R$id;
import com.google.android.settings.biometrics.R$layout;
import com.google.android.setupdesign.util.ThemeHelper;

/* JADX INFO: loaded from: classes4.dex */
public class FaceEnrollParentalConsentGoogle extends FaceEnrollParentalConsent {
    public static final int[] CONSENT_STRING_RESOURCES = {R.string.security_settings_face_enroll_consent_introduction_title, R$string.security_settings_face_enroll_consent_secure_and_helpful_message_1, com.google.android.settings.biometrics.R$string.security_settings_face_enroll_introduction_secure_and_helpful_message_2, R.string.security_settings_face_enroll_introduction_consent_message, R.string.security_settings_face_enroll_introduction_info_consent_glasses, R.string.security_settings_face_enroll_introduction_info_consent_looking, R.string.security_settings_face_enroll_introduction_info_consent_gaze, R$string.security_settings_face_enroll_introduction_how_consent_message_1, R.string.security_settings_face_enroll_introduction_control_consent_title, R.string.security_settings_face_enroll_introduction_control_consent_message, R.string.security_settings_face_enroll_introduction_consent_message_0, R$string.security_settings_face_enroll_consent_message, R.string.security_settings_face_enroll_introduction_info_consent_less_secure, com.google.android.settings.biometrics.R$string.security_settings_face_enroll_introduction_learn_more_message};

    @Override // com.android.settings.biometrics.face.FaceEnrollParentalConsent, com.android.settings.biometrics.face.FaceEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollBase, com.android.settings.core.InstrumentedActivity, com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        ImageView imageView;
        super.onCreate(bundle);
        ImageView imageView2 = (ImageView) findViewById(R.id.icon_glasses);
        ImageView imageView3 = (ImageView) findViewById(R.id.icon_looking);
        ImageView imageView4 = (ImageView) findViewById(R$id.icon_security_privacy_safe);
        ImageView imageView5 = (ImageView) findViewById(R$id.icon_privacy_tip);
        ImageView imageView6 = (ImageView) findViewById(R$id.icon_familiar_face_and_zone);
        ImageView imageView7 = (ImageView) findViewById(R.id.icon_trash_can);
        ImageView imageView8 = (ImageView) findViewById(R.id.icon_link);
        imageView2.getBackground().setColorFilter(getIconColorFilter());
        imageView3.getBackground().setColorFilter(getIconColorFilter());
        imageView4.getBackground().setColorFilter(getIconColorFilter());
        imageView5.getBackground().setColorFilter(getIconColorFilter());
        imageView6.getBackground().setColorFilter(getIconColorFilter());
        imageView7.getBackground().setColorFilter(getIconColorFilter());
        imageView8.getBackground().setColorFilter(getIconColorFilter());
        ((TextView) findViewById(R$id.footer_message_7)).setText(R$string.security_settings_face_enroll_consent_secure_and_helpful_message_1);
        TextView textView = (TextView) findViewById(R$id.message_learn_more);
        textView.setText(Html.fromHtml(getString(com.google.android.settings.biometrics.R$string.security_settings_face_enroll_introduction_learn_more_message), 0));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        if (!ThemeHelper.shouldApplyGlifExpressiveStyle(getApplicationContext()) || (imageView = (ImageView) findViewById(R$id.illustrationImage)) == null) {
            return;
        }
        imageView.setImageResource(R$drawable.face_enroll_intro_illustration_expressive);
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollIntroduction
    protected int getLayoutResource() {
        return R$layout.face_enroll_introduction_2;
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollParentalConsent, com.android.settings.biometrics.face.FaceEnrollIntroduction
    protected int getHowMessage() {
        return R$string.security_settings_face_enroll_introduction_how_consent_message_1;
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollParentalConsent, com.android.settings.biometrics.face.FaceEnrollIntroduction
    protected int getInControlMessage() {
        return R$string.security_settings_face_enroll_introduction_control_consent_message_1;
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollParentalConsent, com.android.settings.biometrics.face.FaceEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollIntroduction
    protected void updateDescriptionText() {
        super.updateDescriptionText();
        if (isFaceStrong()) {
            setDescriptionText(getString(R$string.security_settings_face_enroll_consent_message));
        } else {
            setDescriptionText(R.string.security_settings_face_enroll_introduction_consent_message_0);
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    protected PorterDuffColorFilter getIconColorFilter() {
        if (this.mIconColorFilter == null) {
            this.mIconColorFilter = new PorterDuffColorFilter(getColor(R$color.settingslib_materialColorOnSurfaceVariant), PorterDuff.Mode.SRC_IN);
        }
        return this.mIconColorFilter;
    }
}

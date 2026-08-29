package com.google.android.settings.biometrics.face;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settings.R;
import com.android.settings.biometrics.ParentalControlsUtils;
import com.android.settings.biometrics.face.FaceEnrollIntroduction;
import com.android.settings.biometrics.metrics.BiometricsLogger;
import com.android.settings.biometrics.metrics.OnboardingEvent;
import com.android.settings.overlay.FeatureFactory;
import com.android.settingslib.widget.theme.R$color;
import com.google.android.settings.biometrics.R$drawable;
import com.google.android.settings.biometrics.R$id;
import com.google.android.settings.biometrics.R$layout;
import com.google.android.settings.biometrics.R$string;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.util.ThemeHelper;

/* JADX INFO: loaded from: classes4.dex */
public class FaceEnrollIntroductionGoogle extends FaceEnrollIntroduction {
    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollBase, com.android.settings.core.InstrumentedActivity, com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        ImageView imageView;
        int i;
        super.onCreate(bundle);
        if (this.mBiometricsLogger != null && this.mOnboardingEvent == null) {
            boolean zIsAnySetupWizard = WizardManagerHelper.isAnySetupWizard(getIntent());
            boolean booleanExtra = getIntent().getBooleanExtra("launch_from_safety_source_issue", false);
            if (zIsAnySetupWizard) {
                i = 1;
            } else {
                i = booleanExtra ? 3 : 2;
            }
            int i2 = FeatureFactory.getFeatureFactory().getFingerprintFeatureProvider().getExtPreferenceProvider(getApplicationContext()).getSize() <= 0 ? 0 : 1;
            OnboardingEvent onboardingEvent = new OnboardingEvent();
            this.mOnboardingEvent = onboardingEvent;
            onboardingEvent.setModality(2);
            this.mOnboardingEvent.setFromSource(i);
            this.mOnboardingEvent.setUserId(this.mUserId);
            this.mOnboardingEvent.setCapybaraStatus(i2);
            if (BiometricsLogger.LOGGABLE.booleanValue()) {
                Log.d("BiometricsLogger", getClass().getSimpleName() + ": create event=" + this.mOnboardingEvent);
            }
        }
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
        ((TextView) findViewById(R.id.message_in_control)).setText(R$string.security_settings_face_enroll_introduction_control_message_2);
        TextView textView = (TextView) findViewById(R$id.message_learn_more);
        textView.setText(Html.fromHtml(getString(R$string.security_settings_face_enroll_introduction_learn_more_message), 0));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        if (!ThemeHelper.shouldApplyGlifExpressiveStyle(getApplicationContext()) || (imageView = (ImageView) findViewById(R$id.illustrationImage)) == null) {
            return;
        }
        imageView.setImageResource(R$drawable.face_enroll_intro_illustration_expressive);
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollBase, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        OnboardingEvent onboardingEventFromIntent = getOnboardingEventFromIntent(intent);
        if (BiometricsLogger.LOGGABLE.booleanValue()) {
            Log.d("BiometricsLogger", getClass().getSimpleName() + ": current event=" + this.mOnboardingEvent + ", eventFromData=" + onboardingEventFromIntent);
        }
        if (onboardingEventFromIntent != null) {
            this.mOnboardingEvent = onboardingEventFromIntent;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollIntroduction
    protected int getLayoutResource() {
        return R$layout.face_enroll_introduction_2;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    protected PorterDuffColorFilter getIconColorFilter() {
        if (this.mIconColorFilter == null) {
            this.mIconColorFilter = new PorterDuffColorFilter(getColor(R$color.settingslib_materialColorOnSurfaceVariant), PorterDuff.Mode.SRC_IN);
        }
        return this.mIconColorFilter;
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollIntroduction
    protected void updateDescriptionText() {
        if (isPrivateProfile()) {
            setDescriptionText(getString(R.string.private_space_face_enroll_introduction_message));
        } else if (isFaceStrong()) {
            setDescriptionText(getString(R$string.security_settings_face_enroll_introduction_message_class3_2));
        }
        boolean z = ParentalControlsUtils.parentConsentRequired(this, getModality()) != null;
        if (!isDisabledByAdmin() || z) {
            return;
        }
        setDescriptionText(getDescriptionDisabledByAdmin());
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction, com.android.settings.biometrics.BiometricEnrollIntroduction
    protected Intent getEnrollingIntent() {
        Intent intent = new Intent(this, (Class<?>) FaceEnrollEducationGoogle.class);
        WizardManagerHelper.copyWizardManagerExtras(getIntent(), intent);
        intent.putExtra("enroll_reason", getIntent().getIntExtra("enroll_reason", -1));
        return intent;
    }
}

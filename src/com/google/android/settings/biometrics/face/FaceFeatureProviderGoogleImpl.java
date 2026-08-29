package com.google.android.settings.biometrics.face;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.android.settings.R;
import com.android.settings.biometrics.face.FaceEnrollActivityClassProvider;
import com.android.settings.biometrics.face.FaceFeatureProvider;
import com.android.settings.biometrics.face.FaceSettingsFeatureProvider;
import com.google.android.settings.R$bool;
import com.google.android.settings.R$string;
import com.google.android.settings.biometrics.R$integer;

/* JADX INFO: loaded from: classes4.dex */
public class FaceFeatureProviderGoogleImpl implements FaceFeatureProvider {
    @Override // com.android.settings.biometrics.face.FaceFeatureProvider
    public Intent getPostureGuidanceIntent(Context context) {
        ComponentName componentNameUnflattenFromString;
        String string = context.getString(R$string.config_face_enroll_guidance_page);
        if (TextUtils.isEmpty(string) || (componentNameUnflattenFromString = ComponentName.unflattenFromString(string)) == null) {
            return null;
        }
        Intent intent = new Intent();
        intent.setComponent(componentNameUnflattenFromString);
        return intent;
    }

    @Override // com.android.settings.biometrics.face.FaceFeatureProvider
    public boolean isAttentionSupported(Context context) {
        return context.getResources().getBoolean(R$bool.config_face_settings_attention_supported);
    }

    @Override // com.android.settings.biometrics.face.FaceFeatureProvider
    public boolean isSetupWizardSupported(Context context) {
        return context.getResources().getBoolean(R.bool.config_suw_support_face_enroll);
    }

    @Override // com.android.settings.biometrics.face.FaceFeatureProvider
    public FaceEnrollActivityClassProvider getEnrollActivityClassProvider() {
        return FaceEnrollActivityClassProviderGoogle.INSTANCE;
    }

    @Override // com.android.settings.biometrics.face.FaceFeatureProvider
    public FaceSettingsFeatureProvider getFaceSettingsFeatureProvider() {
        return FaceSettingsFeatureProviderGoogle.INSTANCE;
    }

    @Override // com.android.settings.biometrics.face.FaceFeatureProvider
    public int getMaxEnrollableCount(Context context) {
        return context.getResources().getInteger(R$integer.settings_max_face_enrollable);
    }

    @Override // com.android.settings.biometrics.face.FaceFeatureProvider
    public Class getParentalConsentPage() {
        return FaceEnrollParentalConsentGoogle.class;
    }

    @Override // com.android.settings.biometrics.face.FaceFeatureProvider
    public int[] getParentalConsentStringRes() {
        return FaceEnrollParentalConsentGoogle.CONSENT_STRING_RESOURCES;
    }
}

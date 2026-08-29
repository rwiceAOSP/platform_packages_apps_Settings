package com.google.android.settings.biometrics.face;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.android.settings.R;
import com.android.settings.biometrics.face.FaceEnrollAccessibilityToggle;
import com.android.settings.biometrics.face.FaceEnrollEducation;
import com.android.settings.biometrics.metrics.BiometricsLogger;
import com.android.settings.biometrics.metrics.OnboardingEvent;
import com.android.settings.overlay.FeatureFactory;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.google.android.settings.R$id;

/* JADX INFO: loaded from: classes4.dex */
public class FaceEnrollEducationGoogle extends FaceEnrollEducation {
    private boolean mGazeEnabled;
    private MetricsFeatureProvider mMetricsFeatureProvider;
    private FaceEnrollAccessibilityToggle mSwitchGaze;

    @Override // com.android.settings.biometrics.face.FaceEnrollEducation, com.android.settings.biometrics.BiometricEnrollBase, com.android.settings.core.InstrumentedActivity, com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        boolean z = getResources().getBoolean(R.bool.config_gazeEnabled);
        this.mGazeEnabled = z;
        if (z) {
            this.mMetricsFeatureProvider = FeatureFactory.getFeatureFactory().getMetricsFeatureProvider();
            FaceEnrollAccessibilityToggle faceEnrollAccessibilityToggle = (FaceEnrollAccessibilityToggle) findViewById(R$id.toggle_gaze);
            this.mSwitchGaze = faceEnrollAccessibilityToggle;
            faceEnrollAccessibilityToggle.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollEducationGoogle$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onCreate$0(view);
                }
            });
            this.mSwitchGaze.setChecked(!isAccessibilityEnabled());
            this.mSwitchGaze.setVisibility(isAccessibilityEnabled() ? 0 : 8);
            ((TextView) this.mSwitchGaze.findViewById(R$id.subtitle)).setText(R.string.security_settings_face_settings_gaze_details);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        updateOnboardingScreenInfoActions(this.mSwitchGaze.isChecked() ? 11 : 12);
        this.mSwitchGaze.getSwitch().toggle();
        this.mMetricsFeatureProvider.action(getApplicationContext(), 2022, this.mSwitchGaze.isChecked());
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollEducation, com.android.settings.biometrics.BiometricEnrollBase, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
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

    @Override // com.android.settings.biometrics.face.FaceEnrollEducation
    protected void onAccessibilityButtonClicked(View view) {
        super.onAccessibilityButtonClicked(view);
        updateOnboardingScreenInfoActions(8);
        if (this.mGazeEnabled) {
            this.mSwitchGaze.setChecked(false);
            this.mSwitchGaze.setVisibility(0);
        }
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollEducation
    protected void onNextButtonClick(View view) {
        if (this.mGazeEnabled) {
            Intent intent = new Intent();
            this.mExtraInfoIntent = intent;
            intent.putExtra("gaze_enabled", this.mSwitchGaze.isChecked());
        }
        super.onNextButtonClick(view);
    }
}

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

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.setupdesign.util.ThemeHelper;

public class FaceEnrollEducationGoogle extends FaceEnrollEducation {
    private boolean mGazeEnabled;
    private MetricsFeatureProvider mMetricsFeatureProvider;
    private FaceEnrollAccessibilityToggle mSwitchGaze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGazeEnabled = getResources().getBoolean(R.bool.config_gazeEnabled);
        if (mGazeEnabled) {
            mMetricsFeatureProvider =
                    FeatureFactory.getFeatureFactory().getMetricsFeatureProvider();
            mSwitchGaze = findViewById(R.id.toggle_gaze);
            mSwitchGaze.setOnClickListener(view -> onGazeToggleClick());
            mSwitchGaze.setChecked(!isAccessibilityEnabled());
            mSwitchGaze.setVisibility(isAccessibilityEnabled() ? View.VISIBLE : View.GONE);
            ((TextView) mSwitchGaze.findViewById(R.id.subtitle))
                    .setText(R.string.security_settings_face_settings_gaze_details);
            if (ThemeHelper.shouldApplyGlifExpressiveStyle(getApplicationContext())) {
                final MaterialSwitch switchButton = (MaterialSwitch) mSwitchGaze.getSwitch();
                switchButton.setThumbIconDrawable(switchButton.getContext().getDrawable(
                        com.android.settingslib.widget.theme.R.drawable
                                .settingslib_expressive_switch_thumb_icon));
            }
        }
    }

    private void onGazeToggleClick() {
        updateOnboardingScreenInfoActions(mSwitchGaze.isChecked() ? 11 : 12);
        mSwitchGaze.getSwitch().toggle();
        mMetricsFeatureProvider.action(getApplicationContext(), 2022, mSwitchGaze.isChecked());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        OnboardingEvent onboardingEventFromIntent = getOnboardingEventFromIntent(data);
        if (BiometricsLogger.LOGGABLE) {
            Log.d(
                    BiometricsLogger.TAG,
                    getClass().getSimpleName()
                            + ": current event="
                            + mOnboardingEvent
                            + ", eventFromData="
                            + onboardingEventFromIntent);
        }
        if (onboardingEventFromIntent != null) {
            mOnboardingEvent = onboardingEventFromIntent;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onAccessibilityButtonClicked(View view) {
        super.onAccessibilityButtonClicked(view);
        updateOnboardingScreenInfoActions(8);
        if (mGazeEnabled) {
            mSwitchGaze.setChecked(false);
            mSwitchGaze.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onNextButtonClick(View view) {
        if (mGazeEnabled) {
            Intent intent = new Intent();
            mExtraInfoIntent = intent;
            intent.putExtra("gaze_enabled", mSwitchGaze.isChecked());
        }
        super.onNextButtonClick(view);
    }
}

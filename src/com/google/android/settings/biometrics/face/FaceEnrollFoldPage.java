package com.google.android.settings.biometrics.face;

import android.content.ComponentName;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentActivity;
import androidx.window.embedding.ActivityFilter;
import androidx.window.embedding.ActivityRule;
import androidx.window.embedding.RuleController;
import com.airbnb.lottie.LottieAnimationView;
import com.android.settings.SetupWizardUtils;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settingslib.widget.LottieColorUtils;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.unfold.updates.FoldProvider$FoldCallback;
import com.google.android.settings.R$id;
import com.google.android.settings.R$integer;
import com.google.android.settings.R$layout;
import com.google.android.settings.R$raw;
import com.google.android.settings.R$string;
import com.google.android.settings.R$style;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.util.ThemeHelper;
import java.util.HashSet;

/* JADX INFO: loaded from: classes4.dex */
public class FaceEnrollFoldPage extends FragmentActivity implements FoldProvider$FoldCallback {
    private int mDevicePostureState;
    private FooterBarMixin mFooterBarMixin;
    private GlifLayout mGlifLayout;
    private LottieAnimationView mIllustrationLottie;
    private boolean mKeepScreenOn;
    private int mOrientation;
    private ScreenSizeFoldProvider mScreenSizeFoldProvider;
    private Runnable mTimeoutRunnable = new Runnable() { // from class: com.google.android.settings.biometrics.face.FaceEnrollFoldPage$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$0();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        onSkipButtonClick(null);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mDevicePostureState = bundle.getInt("posture_state");
        }
        HashSet hashSet = new HashSet();
        hashSet.add(new ActivityFilter(new ComponentName(this, (Class<?>) FaceEnrollFoldPage.class), (String) null));
        RuleController.getInstance(this).addRule(new ActivityRule.Builder(hashSet).setAlwaysExpand(true).build());
        setTheme(SetupWizardUtils.getTheme(this, getIntent()));
        ThemeHelper.trySetDynamicColor(this);
        BiometricUtils.setDevicePosturesAllowEnroll(getResources().getInteger(R$integer.config_face_enroll_supported_posture));
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) { // from class: com.google.android.settings.biometrics.face.FaceEnrollFoldPage.1
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                FaceEnrollFoldPage.this.onBackInvoked();
            }
        });
        relayout();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("posture_state", this.mDevicePostureState);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ScreenSizeFoldProvider screenSizeFoldProvider = this.mScreenSizeFoldProvider;
        if (screenSizeFoldProvider != null) {
            screenSizeFoldProvider.onConfigurationChange(configuration);
        }
        if (configuration.orientation != getCurrentOrientation()) {
            relayout();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        if (BiometricUtils.isPostureAllowEnrollment(this.mDevicePostureState)) {
            onFinishPostureGuidance();
        } else {
            setupPostureChangeListener();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        ScreenSizeFoldProvider screenSizeFoldProvider = this.mScreenSizeFoldProvider;
        if (screenSizeFoldProvider != null) {
            screenSizeFoldProvider.unregisterCallback(this);
            this.mScreenSizeFoldProvider = null;
        }
    }

    @Override // com.android.systemui.unfold.updates.FoldProvider$FoldCallback
    public void onFoldUpdated(boolean z) {
        getMainThreadHandler().removeCallbacks(this.mTimeoutRunnable);
        int i = z ? 1 : 3;
        this.mDevicePostureState = i;
        if (BiometricUtils.isPostureAllowEnrollment(i)) {
            onFinishPostureGuidance();
        }
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    protected void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(R$style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, i, z);
    }

    private void setupPostureChangeListener() {
        if (this.mScreenSizeFoldProvider == null) {
            ScreenSizeFoldProvider screenSizeFoldProvider = new ScreenSizeFoldProvider(getApplicationContext());
            this.mScreenSizeFoldProvider = screenSizeFoldProvider;
            screenSizeFoldProvider.registerCallback(this, getMainExecutor());
        }
    }

    private void onFinishPostureGuidance() {
        if (isFinishing()) {
            return;
        }
        setResult(1);
        onRemoveCallbacksAndFinish();
    }

    private void onRemoveCallbacksAndFinish() {
        getMainThreadHandler().removeCallbacks(this.mTimeoutRunnable);
        finish();
        overridePendingTransition(0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackInvoked() {
        setResult(0);
        onRemoveCallbacksAndFinish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSkipButtonClick(View view) {
        setResult(2, getIntent());
        onRemoveCallbacksAndFinish();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        setKeepScreenOn(true);
        getMainThreadHandler().postDelayed(this.mTimeoutRunnable, 60000L);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        setKeepScreenOn(false);
    }

    private void setKeepScreenOn(boolean z) {
        if (z == this.mKeepScreenOn) {
            return;
        }
        if (z) {
            getWindow().addFlags(128);
            this.mKeepScreenOn = true;
        } else {
            getWindow().clearFlags(128);
            this.mKeepScreenOn = false;
        }
    }

    void relayout() {
        setCurrentOrientation(getResources().getConfiguration().orientation);
        setContentView(R$layout.face_enroll_fold_page);
        GlifLayout glifLayout = (GlifLayout) findViewById(R$id.setup_wizard_layout);
        this.mGlifLayout = glifLayout;
        glifLayout.setHeaderText(R$string.face_enrolling_close_to_continue);
        this.mGlifLayout.setDescriptionText(R$string.face_enrolling_close_to_continue_description);
        this.mIllustrationLottie = (LottieAnimationView) findViewById(R$id.illustration_lottie);
        LottieColorUtils.applyDynamicColors(getApplicationContext(), this.mIllustrationLottie);
        this.mIllustrationLottie.setAnimation(R$raw.face_posture_guidance_lottie);
        this.mIllustrationLottie.setVisibility(0);
        this.mIllustrationLottie.playAnimation();
        FooterBarMixin footerBarMixin = (FooterBarMixin) this.mGlifLayout.getMixin(FooterBarMixin.class);
        this.mFooterBarMixin = footerBarMixin;
        footerBarMixin.setSecondaryButton(new FooterButton.Builder(this).setText(R$string.face_enrolling_do_it_later).setListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollFoldPage$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onSkipButtonClick(view);
            }
        }).setButtonType(7).setTheme(com.google.android.setupdesign.R$style.SudGlifButton_Secondary).build());
    }

    void setCurrentOrientation(int i) {
        this.mOrientation = i;
    }

    int getCurrentOrientation() {
        return this.mOrientation;
    }
}

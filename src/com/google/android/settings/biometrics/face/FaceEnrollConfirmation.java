package com.google.android.settings.biometrics.face;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.hardware.biometrics.BiometricManager;
import android.hardware.display.AmbientDisplayConfiguration;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorProperties;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import androidx.fragment.app.FragmentActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.biometrics.BiometricEnrollBase$$ExternalSyntheticLambda0;
import com.android.settings.biometrics.face.FaceAttentionController;
import com.android.settings.biometrics.metrics.BiometricsLogger;
import com.android.settings.biometrics.metrics.OnboardingEvent;
import com.android.settings.biometrics.metrics.OnboardingScreenInfoEvent;
import com.android.settings.overlay.FeatureFactory;
import com.android.settingslib.widget.LottieColorUtils;
import com.android.settingslib.widget.theme.R$drawable;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.settings.R$array;
import com.google.android.settings.R$bool;
import com.google.android.settings.R$id;
import com.google.android.settings.R$layout;
import com.google.android.settings.R$raw;
import com.google.android.settings.R$string;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.R$style;
import com.google.android.setupdesign.template.FloatingBackButtonMixin;
import com.google.android.setupdesign.transition.TransitionHelper;
import com.google.android.setupdesign.util.DynamicColorPalette;
import com.google.android.setupdesign.util.LottieAnimationHelper;
import com.google.android.setupdesign.util.ThemeHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class FaceEnrollConfirmation extends FragmentActivity implements OnBackInvokedCallback {
    static final int REQUEST_FACE_SETTINGS = 1;
    protected ArrayList mActions = new ArrayList();
    private AmbientDisplayConfiguration mAmbientDisplayConfig;
    protected BiometricsLogger mBiometricsLogger;
    private FaceAttentionController mFaceAttentionController;
    private FaceEnrollLockScreenBypassToggle mFaceEnrollLockScreenBypassToggle;
    private FooterBarMixin mFooterBarMixin;
    private boolean mGazeEnabled;
    private PorterDuffColorFilter mIconColorFilter;
    private boolean mIsUsingExpressStyle;
    private LottieAnimationView mLockScreenBypassLottie;
    protected OnboardingEvent mOnboardingEvent;
    private boolean mShowIllustration;
    private boolean mShowLockScreenBypass;
    protected long mStartTimeMillis;
    protected byte[] mToken;
    protected int mUserId;

    /* JADX WARN: Code duplicated, block: B:32:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:38:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:41:0x00fc  */
    /* JADX WARN: Code duplicated, block: B:48:0x013e  */
    /* JADX WARN: Code duplicated, block: B:51:0x0143  */
    /* JADX WARN: Code duplicated, block: B:52:0x0146  */
    /* JADX WARN: Code duplicated, block: B:54:0x014a  */
    /* JADX WARN: Code duplicated, block: B:55:0x014d  */
    /* JADX WARN: Code duplicated, block: B:58:0x015b  */
    /* JADX WARN: Code duplicated, block: B:60:0x0165  */
    /* JADX WARN: Code duplicated, block: B:61:0x016a  */
    /* JADX WARN: Code duplicated, block: B:64:0x019b  */
    /* JADX WARN: Code duplicated, block: B:65:0x01af  */
    /* JADX WARN: Code duplicated, block: B:67:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:68:0x01c0  */
    /* JADX WARN: Code duplicated, block: B:71:0x01da  */
    /* JADX WARN: Code duplicated, block: B:74:0x0224  */
    /* JADX WARN: Code duplicated, block: B:75:0x0227  */
    /* JADX WARN: Instruction removed from duplicated block: B:41:0x00fc, please report this as an issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        int size;
        ?? r2;
        boolean z;
        int i;
        boolean z2;
        int i2;
        String useClass1BiometricDescription;
        OnboardingEvent onboardingEventFromIntent;
        boolean zHasEnrolledTemplates;
        if (ThemeHelper.shouldApplyGlifExpressiveStyle(getApplicationContext())) {
            if (!ThemeHelper.trySetSuwTheme(this)) {
                setTheme(ThemeHelper.getSuwDefaultTheme(getApplicationContext()));
                ThemeHelper.trySetDynamicColor(this);
            }
        } else {
            setTheme(SetupWizardUtils.getTheme(this, getIntent()));
            ThemeHelper.trySetDynamicColor(this);
        }
        this.mIsUsingExpressStyle = ThemeHelper.shouldApplyGlifExpressiveStyle(getApplicationContext());
        super.onCreate(bundle);
        this.mToken = getIntent().getByteArrayExtra("hw_auth_token");
        this.mUserId = getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        getIntent().putExtra("finished_enrolling_face", true);
        ?? r3 = "hw_auth_token";
        if (bundle != null) {
            if (this.mToken == null) {
                this.mToken = bundle.getByteArray("hw_auth_token");
            }
            this.mUserId = bundle.getInt("android.intent.extra.USER_ID", this.mUserId);
            this.mStartTimeMillis = bundle.getLong("start_time", 0L);
            this.mActions = bundle.getIntegerArrayList("onboarding_actions");
            Class<OnboardingEvent> cls = OnboardingEvent.class;
            this.mOnboardingEvent = (OnboardingEvent) bundle.getParcelable("onboarding_event", cls);
            r3 = cls;
        }
        try {
            FaceManager faceManagerOrNull = com.android.settings.Utils.getFaceManagerOrNull(this);
            try {
                if (faceManagerOrNull != null) {
                    zHasEnrolledTemplates = faceManagerOrNull.hasEnrolledTemplates(this.mUserId);
                    if (zHasEnrolledTemplates) {
                        r3 = zHasEnrolledTemplates;
                        size = faceManagerOrNull.getEnrolledFaces().size();
                        r2 = zHasEnrolledTemplates;
                    }
                    if (this.mStartTimeMillis <= 0) {
                        this.mStartTimeMillis = SystemClock.elapsedRealtime();
                    }
                    BiometricsLogger biometricsLogger = FeatureFactory.getFeatureFactory().getBiometricsFeatureProvider().getBiometricsLogger();
                    this.mBiometricsLogger = biometricsLogger;
                    if (this.mOnboardingEvent == null && biometricsLogger != null) {
                        onboardingEventFromIntent = getOnboardingEventFromIntent(getIntent());
                        this.mOnboardingEvent = onboardingEventFromIntent;
                        if (onboardingEventFromIntent != null) {
                            onboardingEventFromIntent.setEnrolledCount(size);
                        }
                    }
                    if (BiometricsLogger.LOGGABLE.booleanValue()) {
                        Log.d("BiometricsLogger", getClass().getSimpleName() + ".onCreate event=" + this.mOnboardingEvent);
                    }
                    this.mShowIllustration = getApplicationContext().getResources().getBoolean(R$bool.config_face_enroll_confirmation_show_illustration);
                    z = (!isManagedProfile(this) || isAnySetupWizard() || r2 == 0) ? false : true;
                    this.mShowLockScreenBypass = z;
                    if (z) {
                        i = R$layout.face_enroll_confirmation_show_lock_screen_bypass;
                    } else if (this.mShowIllustration) {
                        i = R$layout.face_enroll_confirmation_show_illustration;
                    } else {
                        i = R$layout.face_enroll_confirmation_hide_illustration;
                    }
                    setContentView(i);
                    setHeaderText(R$string.security_settings_face_enroll_finish_title);
                    if (this.mShowLockScreenBypass) {
                        GlifLayout layout = getLayout();
                        if (isFaceStrengthClass3()) {
                            useClass1BiometricDescription = getUseClass3BiometricDescription();
                        } else {
                            useClass1BiometricDescription = getUseClass1BiometricDescription(isBiometricClass1FoldableDevice());
                        }
                        layout.setDescriptionText(useClass1BiometricDescription);
                        FaceEnrollLockScreenBypassToggle faceEnrollLockScreenBypassToggle = (FaceEnrollLockScreenBypassToggle) findViewById(R$id.lock_screen_bypass_toggle);
                        this.mFaceEnrollLockScreenBypassToggle = faceEnrollLockScreenBypassToggle;
                        faceEnrollLockScreenBypassToggle.setIsEnabled(this.mShowLockScreenBypass);
                        setScreenLockBypassLottie(this.mFaceEnrollLockScreenBypassToggle.isChecked());
                        this.mFaceEnrollLockScreenBypassToggle.setInnerCompoundButtonCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollConfirmation$$ExternalSyntheticLambda0
                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                                this.f$0.lambda$onCreate$0(compoundButton, z3);
                            }
                        });
                        if (this.mIsUsingExpressStyle) {
                            MaterialSwitch switchButton = this.mFaceEnrollLockScreenBypassToggle.getSwitchButton();
                            switchButton.setThumbIconDrawable(switchButton.getContext().getDrawable(R$drawable.settingslib_expressive_switch_thumb_icon));
                        }
                    } else if (!this.mShowIllustration) {
                        getLayout().setDescriptionText(R$string.face_confirmation_hide_illustration_description);
                        setNonIllustrationThemeColor();
                    } else {
                        setScreenLockBypassLottie(false);
                        getLayout().setDescriptionText(R$string.face_enroll_finish_subtitle);
                    }
                    z2 = getResources().getBoolean(R.bool.config_gazeEnabled);
                    this.mGazeEnabled = z2;
                    if (z2) {
                        boolean booleanExtra = getIntent().getBooleanExtra("gaze_enabled", false);
                        FaceAttentionController faceAttentionController = new FaceAttentionController(getApplicationContext());
                        this.mFaceAttentionController = faceAttentionController;
                        faceAttentionController.setToken(this.mToken);
                        this.mFaceAttentionController.setAttentionStatus(this.mUserId, booleanExtra, new FaceAttentionController.OnSetAttentionListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollConfirmation$$ExternalSyntheticLambda1
                            @Override // com.android.settings.biometrics.face.FaceAttentionController.OnSetAttentionListener
                            public final void onSetAttentionCompleted(boolean z3) {
                                FaceEnrollConfirmation.m7948$r8$lambda$DZaV0QEOIu4PbzkswOVGDNcg8(z3);
                            }
                        });
                    }
                    this.mAmbientDisplayConfig = new AmbientDisplayConfiguration(this);
                    FooterBarMixin footerBarMixin = (FooterBarMixin) getLayout().getMixin(FooterBarMixin.class);
                    this.mFooterBarMixin = footerBarMixin;
                    FooterButton.Builder builder = new FooterButton.Builder(this);
                    if (WizardManagerHelper.isAnySetupWizard(getIntent())) {
                        i2 = R$string.security_settings_face_enroll_next;
                    } else {
                        i2 = R$string.security_settings_face_enroll_done;
                    }
                    footerBarMixin.setPrimaryButton(builder.setText(i2).setListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollConfirmation$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.onButtonPositive(view);
                        }
                    }).setButtonType(5).setTheme(R$style.SudGlifButton_Primary).build());
                    enablePickupGesture();
                    enableTapScreenGesture();
                    getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this);
                    ((FloatingBackButtonMixin) getLayout().getMixin(FloatingBackButtonMixin.class)).setOnBackPressedCallback(new FloatingBackButtonMixin.BackButtonListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollConfirmation$$ExternalSyntheticLambda3
                        @Override // com.google.android.setupdesign.template.FloatingBackButtonMixin.BackButtonListener
                        public final void onBackPressed() {
                            this.f$0.onBackInvoked();
                        }
                    });
                }
                boolean booleanExtra2 = getIntent().getBooleanExtra("finished_enrolling_face", true);
                Log.w("FaceEnrollConfirmation", "FaceManager is null, respect EXTRA_FINISHED_ENROLL_FACE instead");
                r3 = booleanExtra2;
                r3 = zHasEnrolledTemplates;
            } catch (NullPointerException e) {
                e = e;
                Log.e("FaceEnrollConfirmation", "Failed to check enrolled templates", e);
            }
        } catch (NullPointerException e2) {
            e = e2;
            r3 = 0;
        }
        size = 0;
        r2 = r3;
        if (this.mStartTimeMillis <= 0) {
            this.mStartTimeMillis = SystemClock.elapsedRealtime();
        }
        BiometricsLogger biometricsLogger2 = FeatureFactory.getFeatureFactory().getBiometricsFeatureProvider().getBiometricsLogger();
        this.mBiometricsLogger = biometricsLogger2;
        if (this.mOnboardingEvent == null) {
            onboardingEventFromIntent = getOnboardingEventFromIntent(getIntent());
            this.mOnboardingEvent = onboardingEventFromIntent;
            if (onboardingEventFromIntent != null) {
                onboardingEventFromIntent.setEnrolledCount(size);
            }
        }
        if (BiometricsLogger.LOGGABLE.booleanValue()) {
            Log.d("BiometricsLogger", getClass().getSimpleName() + ".onCreate event=" + this.mOnboardingEvent);
        }
        this.mShowIllustration = getApplicationContext().getResources().getBoolean(R$bool.config_face_enroll_confirmation_show_illustration);
        if (!isManagedProfile(this)) {
        }
        this.mShowLockScreenBypass = z;
        if (z) {
            i = R$layout.face_enroll_confirmation_show_lock_screen_bypass;
        } else if (this.mShowIllustration) {
            i = R$layout.face_enroll_confirmation_show_illustration;
        } else {
            i = R$layout.face_enroll_confirmation_hide_illustration;
        }
        setContentView(i);
        setHeaderText(R$string.security_settings_face_enroll_finish_title);
        if (this.mShowLockScreenBypass) {
            GlifLayout layout2 = getLayout();
            if (isFaceStrengthClass3()) {
                useClass1BiometricDescription = getUseClass3BiometricDescription();
            } else {
                useClass1BiometricDescription = getUseClass1BiometricDescription(isBiometricClass1FoldableDevice());
            }
            layout2.setDescriptionText(useClass1BiometricDescription);
            FaceEnrollLockScreenBypassToggle faceEnrollLockScreenBypassToggle2 = (FaceEnrollLockScreenBypassToggle) findViewById(R$id.lock_screen_bypass_toggle);
            this.mFaceEnrollLockScreenBypassToggle = faceEnrollLockScreenBypassToggle2;
            faceEnrollLockScreenBypassToggle2.setIsEnabled(this.mShowLockScreenBypass);
            setScreenLockBypassLottie(this.mFaceEnrollLockScreenBypassToggle.isChecked());
            this.mFaceEnrollLockScreenBypassToggle.setInnerCompoundButtonCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollConfirmation$$ExternalSyntheticLambda0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                    this.f$0.lambda$onCreate$0(compoundButton, z3);
                }
            });
            if (this.mIsUsingExpressStyle) {
                MaterialSwitch switchButton2 = this.mFaceEnrollLockScreenBypassToggle.getSwitchButton();
                switchButton2.setThumbIconDrawable(switchButton2.getContext().getDrawable(R$drawable.settingslib_expressive_switch_thumb_icon));
            }
        } else if (!this.mShowIllustration) {
            getLayout().setDescriptionText(R$string.face_confirmation_hide_illustration_description);
            setNonIllustrationThemeColor();
        } else {
            setScreenLockBypassLottie(false);
            getLayout().setDescriptionText(R$string.face_enroll_finish_subtitle);
        }
        z2 = getResources().getBoolean(R.bool.config_gazeEnabled);
        this.mGazeEnabled = z2;
        if (z2) {
            boolean booleanExtra3 = getIntent().getBooleanExtra("gaze_enabled", false);
            FaceAttentionController faceAttentionController2 = new FaceAttentionController(getApplicationContext());
            this.mFaceAttentionController = faceAttentionController2;
            faceAttentionController2.setToken(this.mToken);
            this.mFaceAttentionController.setAttentionStatus(this.mUserId, booleanExtra3, new FaceAttentionController.OnSetAttentionListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollConfirmation$$ExternalSyntheticLambda1
                @Override // com.android.settings.biometrics.face.FaceAttentionController.OnSetAttentionListener
                public final void onSetAttentionCompleted(boolean z3) {
                    FaceEnrollConfirmation.m7948$r8$lambda$DZaV0QEOIu4PbzkswOVGDNcg8(z3);
                }
            });
        }
        this.mAmbientDisplayConfig = new AmbientDisplayConfiguration(this);
        FooterBarMixin footerBarMixin2 = (FooterBarMixin) getLayout().getMixin(FooterBarMixin.class);
        this.mFooterBarMixin = footerBarMixin2;
        FooterButton.Builder builder2 = new FooterButton.Builder(this);
        if (WizardManagerHelper.isAnySetupWizard(getIntent())) {
            i2 = R$string.security_settings_face_enroll_next;
        } else {
            i2 = R$string.security_settings_face_enroll_done;
        }
        footerBarMixin2.setPrimaryButton(builder2.setText(i2).setListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollConfirmation$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onButtonPositive(view);
            }
        }).setButtonType(5).setTheme(R$style.SudGlifButton_Primary).build());
        enablePickupGesture();
        enableTapScreenGesture();
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this);
        ((FloatingBackButtonMixin) getLayout().getMixin(FloatingBackButtonMixin.class)).setOnBackPressedCallback(new FloatingBackButtonMixin.BackButtonListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollConfirmation$$ExternalSyntheticLambda3
            @Override // com.google.android.setupdesign.template.FloatingBackButtonMixin.BackButtonListener
            public final void onBackPressed() {
                this.f$0.onBackInvoked();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(CompoundButton compoundButton, boolean z) {
        updateOnboardingScreenInfoActions(13);
        setScreenLockBypassLottie(z);
    }

    /* JADX INFO: renamed from: $r8$lambda$DZaV0QEOIu4PbzkswOVGDNc--g8, reason: not valid java name */
    public static /* synthetic */ void m7948$r8$lambda$DZaV0QEOIu4PbzkswOVGDNcg8(boolean z) {
        if (z) {
            return;
        }
        Log.w("FaceEnrollConfirmation", "Failed to set attention status");
    }

    private void setScreenLockBypassLottie(boolean z) {
        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R$id.lock_screen_bypass_lottie);
        this.mLockScreenBypassLottie = lottieAnimationView;
        if (lottieAnimationView != null) {
            boolean zIsAnimating = lottieAnimationView.isAnimating();
            this.mLockScreenBypassLottie.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollConfirmation.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    boolean zIsAnimating2 = FaceEnrollConfirmation.this.mLockScreenBypassLottie.isAnimating();
                    FaceEnrollConfirmation faceEnrollConfirmation = FaceEnrollConfirmation.this;
                    if (zIsAnimating2) {
                        faceEnrollConfirmation.mLockScreenBypassLottie.pauseAnimation();
                    } else {
                        faceEnrollConfirmation.mLockScreenBypassLottie.resumeAnimation();
                    }
                }
            });
            this.mLockScreenBypassLottie.setAnimation(getConfirmationLockBypassIllustration(this.mIsUsingExpressStyle, z));
            LottieColorUtils.applyDynamicColors(getApplicationContext(), this.mLockScreenBypassLottie);
            if (zIsAnimating) {
                this.mLockScreenBypassLottie.playAnimation();
            }
            if (this.mIsUsingExpressStyle) {
                applyIllustrationLottieThemeColor();
            }
        }
    }

    private int getConfirmationLockBypassIllustration(boolean z, boolean z2) {
        if (z) {
            if (z2) {
                return R$raw.face_confirmation_lockscreen_bypass_enable_lottie_expressive;
            }
            return R$raw.face_confirmation_lockscreen_bypass_disable_lottie_expressive;
        }
        if (z2) {
            return R$raw.face_confirmation_lockscreen_bypass_enable_lottie;
        }
        return R$raw.face_confirmation_lockscreen_bypass_disable_lottie;
    }

    private void applyIllustrationLottieThemeColor() {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, getResources().getStringArray(R$array.face_unlock_bypass));
        LottieAnimationHelper.get().applyColor(getApplicationContext(), this.mLockScreenBypassLottie, arrayList);
    }

    private boolean isManagedProfile(Context context) {
        return ((UserManager) context.getSystemService(UserManager.class)).isManagedProfile(this.mUserId);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        LottieAnimationView lottieAnimationView = this.mLockScreenBypassLottie;
        if (lottieAnimationView != null) {
            lottieAnimationView.playAnimation();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        LottieAnimationView lottieAnimationView = this.mLockScreenBypassLottie;
        if (lottieAnimationView != null) {
            lottieAnimationView.cancelAnimation();
        }
        if (isChangingConfigurations() || WizardManagerHelper.isAnySetupWizard(getIntent()) || isFinishing()) {
            return;
        }
        setResult(3, newResultIntent());
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        FaceEnrollLockScreenBypassToggle faceEnrollLockScreenBypassToggle = this.mFaceEnrollLockScreenBypassToggle;
        if (faceEnrollLockScreenBypassToggle != null) {
            faceEnrollLockScreenBypassToggle.setInnerCompoundButtonCheckedChangeListener(null);
        }
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this);
        super.onDestroy();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putByteArray("hw_auth_token", this.mToken);
        bundle.putInt("android.intent.extra.USER_ID", this.mUserId);
        if (this.mOnboardingEvent != null) {
            bundle.putLong("start_time", this.mStartTimeMillis);
            bundle.putIntegerArrayList("onboarding_actions", this.mActions);
            bundle.putParcelable("onboarding_event", this.mOnboardingEvent);
        }
    }

    private GlifLayout getLayout() {
        return (GlifLayout) findViewById(R$id.face_enroll_confirmation);
    }

    void onButtonPositive(View view) {
        updateOnboardingScreenInfoActions(1);
        if (WizardManagerHelper.isAnySetupWizard(getIntent())) {
            revokeChallenge();
        }
        TransitionHelper.applyForwardTransition(this, 6);
        Intent intentNewResultIntent = newResultIntent();
        if (intentNewResultIntent == null) {
            intentNewResultIntent = new Intent();
        }
        intentNewResultIntent.putExtra("hw_auth_token", this.mToken);
        intentNewResultIntent.putExtra("challenge", getIntent().getLongExtra("challenge", 0L));
        intentNewResultIntent.putExtra("sensor_id", getIntent().getIntExtra("sensor_id", 0));
        setResult(1, intentNewResultIntent);
        finish();
    }

    private void setHeaderText(int i) {
        TextView headerTextView = getLayout().getHeaderTextView();
        CharSequence text = headerTextView.getText();
        CharSequence text2 = getText(i);
        if (text != text2) {
            if (!TextUtils.isEmpty(text2)) {
                headerTextView.setAccessibilityLiveRegion(1);
            }
            getLayout().setHeaderText(text2);
            setTitle(text2);
        }
    }

    private void enablePickupGesture() {
        if (this.mAmbientDisplayConfig.pickupGestureEnabled(this.mUserId)) {
            return;
        }
        Log.d("FaceEnrollConfirmation", "Reset DOZE_PICK_UP_GESTURE null, pickupGestureEnabled is default ON.");
        Settings.Secure.putStringForUser(getContentResolver(), "doze_pulse_on_pick_up", null, this.mUserId);
    }

    private void enableTapScreenGesture() {
        if (this.mAmbientDisplayConfig.tapGestureEnabled(this.mUserId)) {
            return;
        }
        Log.d("FaceEnrollConfirmation", "Reset DOZE_TAP_SCREEN_GESTURE null, tapGestureEnabled is default ON.");
        Settings.Secure.putStringForUser(getContentResolver(), "doze_tap_gesture", null, this.mUserId);
    }

    private void revokeChallenge() {
        FaceManager faceManager = (FaceManager) getSystemService(FaceManager.class);
        if (faceManager != null) {
            faceManager.revokeChallenge(getIntent().getIntExtra("sensor_id", -1), this.mUserId, getIntent().getLongExtra("challenge", 0L));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        OnboardingEvent onboardingEventFromIntent = getOnboardingEventFromIntent(intent);
        Boolean bool = BiometricsLogger.LOGGABLE;
        if (bool.booleanValue()) {
            Log.d("BiometricsLogger", getClass().getSimpleName() + ": current event=" + this.mOnboardingEvent + ", eventFromData=" + onboardingEventFromIntent);
        }
        if (onboardingEventFromIntent != null) {
            this.mOnboardingEvent = onboardingEventFromIntent;
        }
        this.mStartTimeMillis = SystemClock.elapsedRealtime();
        if (bool.booleanValue()) {
            Log.d("BiometricsLogger", getClass().getSimpleName() + ": received event=" + this.mOnboardingEvent);
        }
        if (i == 1) {
            setResult(i2 == 3 ? 3 : 1, intent);
            finish();
        } else if (i == 2) {
            Log.d("FaceEnrollConfirmation", "Next biometric's result: " + i2);
            setResult(1, intent);
            finish();
        }
    }

    protected PorterDuffColorFilter getIconColorFilter() {
        if (this.mIconColorFilter == null) {
            this.mIconColorFilter = new PorterDuffColorFilter(DynamicColorPalette.getColor(this, 0), PorterDuff.Mode.SRC_IN);
        }
        return this.mIconColorFilter;
    }

    private void setNonIllustrationThemeColor() {
        ImageView imageView = (ImageView) findViewById(R$id.icon_fold);
        ImageView imageView2 = (ImageView) findViewById(R$id.icon_light);
        if (imageView == null || imageView2 == null) {
            return;
        }
        imageView.getBackground().setColorFilter(getIconColorFilter());
        imageView2.getBackground().setColorFilter(getIconColorFilter());
    }

    @Override // android.window.OnBackInvokedCallback
    public void onBackInvoked() {
        updateOnboardingScreenInfoActions(3);
        Intent intentNewResultIntent = newResultIntent();
        if (intentNewResultIntent == null) {
            intentNewResultIntent = new Intent();
        }
        intentNewResultIntent.putExtra("finished_enrolling_face", true);
        setResult(0, intentNewResultIntent);
        finish();
        TransitionHelper.applyBackwardTransition(this, 6);
    }

    boolean isAnySetupWizard() {
        return WizardManagerHelper.isAnySetupWizard(getIntent());
    }

    boolean isFaceStrengthClass3() {
        boolean z;
        List listHasFaceSensorProperties = hasFaceSensorProperties();
        if (listHasFaceSensorProperties == null) {
            return false;
        }
        Iterator it = listHasFaceSensorProperties.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (((FaceSensorProperties) it.next()).getSensorStrength() == 2) {
                z = true;
                break;
            }
        }
        BiometricManager biometricManager = (BiometricManager) getSystemService(BiometricManager.class);
        return z && (biometricManager != null && biometricManager.canAuthenticate(15) == 0);
    }

    private List hasFaceSensorProperties() {
        FaceManager faceManager = (FaceManager) getSystemService(FaceManager.class);
        if (faceManager == null) {
            Log.w("FaceEnrollConfirmation", "Unable to get face manager...");
            return null;
        }
        List sensorProperties = faceManager.getSensorProperties();
        if (sensorProperties != null && !sensorProperties.isEmpty()) {
            return sensorProperties;
        }
        Log.d("FaceEnrollConfirmation", "FaceSensorProperties were empty");
        return null;
    }

    boolean isBiometricClass1FoldableDevice() {
        List listHasFaceSensorProperties = hasFaceSensorProperties();
        if (listHasFaceSensorProperties == null) {
            return false;
        }
        Iterator it = listHasFaceSensorProperties.iterator();
        while (it.hasNext()) {
            if (((FaceSensorProperties) it.next()).getSensorStrength() == 0) {
                if (FaceUtils.isFoldable(getApplicationContext())) {
                    return true;
                }
            }
        }
        return false;
    }

    String getUseClass1BiometricDescription(boolean z) {
        int i;
        if (z) {
            i = R$string.security_settings_face_enroll_finish_description_without_bp_folded;
        } else {
            i = R$string.security_settings_face_enroll_finish_description_without_bp;
        }
        return getString(i);
    }

    String getUseClass3BiometricDescription() {
        return getString(R$string.face_enroll_finish_subtitle);
    }

    private void updateOnboardingScreenInfoActions(int i) {
        if (this.mBiometricsLogger == null || this.mOnboardingEvent == null) {
            return;
        }
        this.mActions.add(Integer.valueOf(i));
    }

    private Intent newResultIntent() {
        if (this.mBiometricsLogger == null || this.mOnboardingEvent == null) {
            return null;
        }
        addScreenInfoToEvent();
        Intent intent = new Intent();
        intent.putExtra("biometrics_onboarding_event_bytes", this.mBiometricsLogger.eventToMessageByteArray(this.mOnboardingEvent));
        return intent;
    }

    private OnboardingEvent getOnboardingEventFromIntent(Intent intent) {
        if (this.mBiometricsLogger == null || intent == null || !intent.hasExtra("biometrics_onboarding_event_bytes")) {
            return null;
        }
        return this.mBiometricsLogger.messageByteArrayToEvent(intent.getByteArrayExtra("biometrics_onboarding_event_bytes"));
    }

    private void addScreenInfoToEvent() {
        OnboardingEvent onboardingEvent;
        if (this.mBiometricsLogger == null || (onboardingEvent = this.mOnboardingEvent) == null) {
            return;
        }
        onboardingEvent.addScreenInfo(new OnboardingScreenInfoEvent(4, SystemClock.elapsedRealtime() - this.mStartTimeMillis, this.mActions.stream().mapToInt(new BiometricEnrollBase$$ExternalSyntheticLambda0()).toArray()));
        if (BiometricsLogger.LOGGABLE.booleanValue()) {
            Log.d("BiometricsLogger", getClass().getSimpleName() + ": add screen info=" + this.mOnboardingEvent.getScreenInfos());
        }
        this.mActions.clear();
    }
}

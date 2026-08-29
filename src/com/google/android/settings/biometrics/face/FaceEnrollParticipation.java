package com.google.android.settings.biometrics.face;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import androidx.fragment.app.FragmentActivity;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.overlay.FeatureFactory;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.unfold.updates.FoldProvider$FoldCallback;
import com.google.android.settings.R$bool;
import com.google.android.settings.R$id;
import com.google.android.settings.R$integer;
import com.google.android.settings.R$layout;
import com.google.android.settings.R$string;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.R$anim;
import com.google.android.setupdesign.R$style;
import com.google.android.setupdesign.util.ThemeHelper;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
public class FaceEnrollParticipation extends FragmentActivity {
    private boolean mDebugConsent;
    private int mDevicePostureState;
    private IBinder mFaceService;
    private boolean mLaunchedPostureGuidance;
    private boolean mNextLaunched;
    private FooterButton mPrimaryButton;
    private ScreenSizeFoldProvider mScreenSizeFoldProvider;
    private int mUserId;
    private FoldProvider$FoldCallback mFoldCallback = null;
    private Intent mPostureGuidanceIntent = null;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        ThemeHelper.applyTheme(this);
        ThemeHelper.trySetDynamicColor(this);
        super.onCreate(bundle);
        if (!getPackageName().equals(getLaunchedFromPackage())) {
            Log.w("FaceEnrollParticipation", "Invalid caller: " + getLaunchedFromPackage());
            finish();
            return;
        }
        this.mUserId = getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        if (bundle != null) {
            this.mLaunchedPostureGuidance = bundle.getBoolean("launched_posture_guidance");
            this.mNextLaunched = bundle.getBoolean("next_launched");
        }
        setContentView(R$layout.face_enroll_participation);
        FooterBarMixin footerBarMixin = (FooterBarMixin) getLayout().getMixin(FooterBarMixin.class);
        FooterButton footerButtonBuild = new FooterButton.Builder(this).setText(R$string.face_enrolling_confirm_help_debug).setListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollParticipation$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Throwable {
                this.f$0.onButtonPositive(view);
            }
        }).setButtonType(5).setTheme(R$style.SudGlifButton_Primary).build();
        this.mPrimaryButton = footerButtonBuild;
        footerButtonBuild.setEnabled(false);
        footerBarMixin.setPrimaryButton(this.mPrimaryButton);
        footerBarMixin.setSecondaryButton(new FooterButton.Builder(this).setText(R$string.face_enrolling_skip_help_debug).setListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollParticipation$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Throwable {
                this.f$0.onButtonNegative(view);
            }
        }).setButtonType(7).setTheme(R$style.SudGlifButton_Secondary).build());
        ((CheckBox) findViewById(R$id.agree_to_participate)).setOnClickListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.face.FaceEnrollParticipation$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$0(view);
            }
        });
        this.mDebugConsent = false;
        getApplicationContext();
        IBinder service = ServiceManager.getService("face");
        this.mFaceService = service;
        if (service == null) {
            Log.e("FaceEnrollParticipation", "Could not connect to face service");
        }
        this.mPostureGuidanceIntent = FeatureFactory.getFeatureFactory().getFaceFeatureProvider().getPostureGuidanceIntent(getApplicationContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        this.mPrimaryButton.setEnabled(((CheckBox) view).isChecked());
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    protected void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(com.google.android.settings.R$style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, i, z);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        if (this.mPostureGuidanceIntent == null) {
            Log.d("FaceEnrollParticipation", "Device do not support posture guidance");
            return;
        }
        BiometricUtils.setDevicePosturesAllowEnroll(getResources().getInteger(R$integer.config_face_enroll_supported_posture));
        if (this.mFoldCallback == null) {
            this.mFoldCallback = new FoldProvider$FoldCallback() { // from class: com.google.android.settings.biometrics.face.FaceEnrollParticipation$$ExternalSyntheticLambda0
                @Override // com.android.systemui.unfold.updates.FoldProvider$FoldCallback
                public final void onFoldUpdated(boolean z) {
                    this.f$0.lambda$onStart$1(z);
                }
            };
        }
        if (this.mScreenSizeFoldProvider == null) {
            ScreenSizeFoldProvider screenSizeFoldProvider = new ScreenSizeFoldProvider(getApplicationContext());
            this.mScreenSizeFoldProvider = screenSizeFoldProvider;
            screenSizeFoldProvider.registerCallback(this.mFoldCallback, getMainExecutor());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$1(boolean z) {
        int i = z ? 1 : 3;
        this.mDevicePostureState = i;
        if (!BiometricUtils.shouldShowPostureGuidance(i, this.mLaunchedPostureGuidance) || this.mNextLaunched) {
            return;
        }
        launchPostureGuidance();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("launched_posture_guidance", this.mLaunchedPostureGuidance);
        bundle.putBoolean("next_launched", this.mNextLaunched);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        ScreenSizeFoldProvider screenSizeFoldProvider = this.mScreenSizeFoldProvider;
        if (screenSizeFoldProvider != null) {
            screenSizeFoldProvider.unregisterCallback(this.mFoldCallback);
            this.mScreenSizeFoldProvider = null;
            this.mFoldCallback = null;
        }
        if (isChangingConfigurations() || this.mNextLaunched || WizardManagerHelper.isAnySetupWizard(getIntent()) || BiometricUtils.isPostureGuidanceShowing(this.mDevicePostureState, this.mLaunchedPostureGuidance)) {
            return;
        }
        setResult(3);
        finish();
    }

    private GlifLayout getLayout() {
        return (GlifLayout) findViewById(R$id.face_enroll_participation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onButtonPositive(View view) throws Throwable {
        Log.d("FaceEnrollParticipation", "Participant agreed to data collection");
        sendDebugMessageToFaceService("--enable");
        this.mDebugConsent = true;
        Settings.Secure.putIntForUser(getContentResolver(), "biometric_debug_enabled", 1, this.mUserId);
        startEnrolling();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onButtonNegative(View view) throws Throwable {
        sendDebugMessageToFaceService("--disable");
        Settings.Secure.putIntForUser(getContentResolver(), "biometric_debug_enabled", 0, this.mUserId);
        startEnrolling();
    }

    void sendDebugMessageToFaceService(String str) throws Throwable {
        if (this.mFaceService != null) {
            FileOutputStream fileOutputStream = null;
            fileOutputStream = null;
            fileOutputStream = null;
            try {
                try {
                    try {
                        try {
                            FileOutputStream fileOutputStream2 = new FileOutputStream("/dev/null");
                            try {
                                IBinder iBinder = this.mFaceService;
                                FileDescriptor fd = fileOutputStream2.getFD();
                                iBinder.dump(fd, new String[]{"--hal", str});
                                fileOutputStream2.close();
                                fileOutputStream = fd;
                            } catch (IOException e) {
                                e = e;
                                fileOutputStream = fileOutputStream2;
                                e.printStackTrace();
                                Log.e("FaceEnrollParticipation", "IOException", e);
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                    fileOutputStream = fileOutputStream;
                                }
                            } catch (Throwable th) {
                                th = th;
                                fileOutputStream = fileOutputStream2;
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e2) {
                                        Log.e("FaceEnrollParticipation", "IOException", e2);
                                    }
                                }
                                throw th;
                            }
                        } catch (IOException e3) {
                            Log.e("FaceEnrollParticipation", "IOException", e3);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (IOException e4) {
                    e = e4;
                }
            } catch (RemoteException e5) {
                e5.printStackTrace();
            }
        }
    }

    private void startEnrolling() {
        Intent intent;
        boolean z = getResources().getBoolean(R$bool.config_face_enroll_use_traffic_light);
        if (z) {
            intent = new Intent("com.google.android.settings.future.biometrics.faceenroll.action.ENROLL");
        } else {
            intent = new Intent(this, (Class<?>) FaceEnrollEnrolling.class);
        }
        if (z) {
            String string = getString(R$string.config_face_enroll_traffic_light_package);
            if (TextUtils.isEmpty(string)) {
                Segment$$ExternalSyntheticBUOutline1.m("Package name must not be empty");
                return;
            }
            intent.setPackage(string);
        }
        intent.putExtras(getIntent());
        intent.putExtra("debug_consent", this.mDebugConsent);
        startActivityForResult(intent, 1);
        this.mNextLaunched = true;
    }

    private void launchPostureGuidance() {
        if (this.mPostureGuidanceIntent == null || this.mLaunchedPostureGuidance) {
            return;
        }
        BiometricUtils.copyMultiBiometricExtras(getIntent(), this.mPostureGuidanceIntent);
        startActivityForResult(this.mPostureGuidanceIntent, 7);
        this.mLaunchedPostureGuidance = true;
        int i = R$anim.sud_stay;
        overridePendingTransition(i, i);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == 7) {
            this.mLaunchedPostureGuidance = false;
            if (i2 == 0 || i2 == 2) {
                setResult(i2);
                finish();
                return;
            }
            return;
        }
        if (i == 1) {
            setResult(i2, intent);
            finish();
        }
        super.onActivityResult(i, i2, intent);
    }
}

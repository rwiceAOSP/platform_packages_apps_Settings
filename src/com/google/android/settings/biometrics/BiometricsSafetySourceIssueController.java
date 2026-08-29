package com.google.android.settings.biometrics;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.UserInfo;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Process;
import android.os.UserManager;
import android.safetycenter.SafetySourceIssue;
import android.util.Log;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricEnrollActivity;
import com.android.settings.biometrics.face.FaceEnroll;
import com.android.settings.biometrics.fingerprint.FingerprintEnroll;
import com.android.settings.security.ScreenLockPreferenceDetailsUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

public class BiometricsSafetySourceIssueController {
    private Context mContext;
    private int mCurrentSafetyIssueActionLaunchCount;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("biometric_safety_issue_dismiss_action".equals(intent.getAction())) {
                Log.d("BiometricsIssueCtr", "SafetyIssue dismissed");
                BiometricsSafetySourceIssueController.this.notifySafetyIssueActionLaunched();
            }
        }
    };
    private long mSafetyIssueActionLastLaunchTime;
    private SharedPreferences mSharedPreferences;

    public BiometricsSafetySourceIssueController(Context context) {
        this.mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("BiometricSafetyIssue", 0);
        this.mSharedPreferences = sharedPreferences;
        this.mSafetyIssueActionLastLaunchTime = sharedPreferences.getLong("safety_issue_last_launch_time", 0L);
        this.mCurrentSafetyIssueActionLaunchCount = this.mSharedPreferences.getInt("safety_issue_launch_count", 0);
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter("biometric_safety_issue_dismiss_action"), 2);
    }

    private boolean shouldShowSafetyIssue(int i) {
        long timePassedSinceLastLaunch = getTimePassedSinceLastLaunch();
        int i2 = this.mCurrentSafetyIssueActionLaunchCount;
        if (i2 > 2) {
            Log.d("BiometricsIssueCtr", "Skip for {" + i + "}: Exceeded maximum launch count");
            return false;
        }
        if (i2 == 1 && timePassedSinceLastLaunch < 7) {
            Log.d("BiometricsIssueCtr", "Skip for {" + i + "}: The block duration after first launch has not yet passed");
            return false;
        }
        if (i2 == 2 && timePassedSinceLastLaunch < 14) {
            Log.d("BiometricsIssueCtr", "Skip for {" + i + "}: The block duration after second launch has not yet passed");
            return false;
        }
        if (isLockscreenSet()) {
            return true;
        }
        Log.d("BiometricsIssueCtr", "Skip for {" + i + "}: no lockscreen set");
        return false;
    }

    boolean isLockscreenSet() {
        int identifier = Process.myUserHandle().getIdentifier();
        RestrictedLockUtils.EnforcedAdmin enforcedAdminCheckIfPasswordQualityIsSet = RestrictedLockUtilsInternal.checkIfPasswordQualityIsSet(this.mContext, identifier);
        ScreenLockPreferenceDetailsUtils screenLockPreferenceDetailsUtils = new ScreenLockPreferenceDetailsUtils(this.mContext);
        return screenLockPreferenceDetailsUtils.isPasswordQualityManaged(identifier, enforcedAdminCheckIfPasswordQualityIsSet) || screenLockPreferenceDetailsUtils.isLockPatternSecure();
    }

    long getTimePassedSinceLastLaunch() {
        return (System.currentTimeMillis() - this.mSafetyIssueActionLastLaunchTime) / 86400000;
    }

    public void notifySafetyIssueActionLaunched() {
        this.mSafetyIssueActionLastLaunchTime = System.currentTimeMillis();
        this.mCurrentSafetyIssueActionLaunchCount++;
        Log.d("BiometricsIssueCtr", "Action is launched: count=" + this.mCurrentSafetyIssueActionLaunchCount);
        this.mSharedPreferences.edit().putLong("safety_issue_last_launch_time", this.mSafetyIssueActionLastLaunchTime).putInt("safety_issue_launch_count", this.mCurrentSafetyIssueActionLaunchCount).apply();
    }

    public SafetySourceIssue getSafetySourceIssue(String str) {
        int i;
        String string;
        String string2;
        String string3;
        PendingIntent pendingIntentCreateActionPendingIntent;
        int i2;
        String string4;
        String string5;
        String string6;
        String str2;
        String str3;
        int i3;
        int i4;
        int identifier = Process.myUserHandle().getIdentifier();
        if (!shouldShowSafetyIssue(identifier)) {
            return null;
        }
        UserInfo userInfo = ((UserManager) this.mContext.getSystemService(UserManager.class)).getUserInfo(identifier);
        boolean zIsManagedProfile = userInfo != null ? userInfo.isManagedProfile() : false;
        boolean zEquals = "AndroidFingerprintUnlock".equals(str);
        boolean zEquals2 = "AndroidFaceUnlock".equals(str);
        FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(this.mContext);
        FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(this.mContext);
        boolean zHasFingerprintHardware = Utils.hasFingerprintHardware(this.mContext);
        boolean zHasFaceHardware = Utils.hasFaceHardware(this.mContext);
        boolean z = zHasFingerprintHardware && !fingerprintManagerOrNull.hasEnrolledFingerprints(identifier);
        boolean z2 = zHasFaceHardware && !faceManagerOrNull.hasEnrolledTemplates(identifier);
        int size = zHasFingerprintHardware ? fingerprintManagerOrNull.getEnrolledFingerprints(identifier).size() : 0;
        Log.d("BiometricsIssueCtr", "getSafetySourceIssue source=" + str + ", isFpEnrollNeeded=" + z + ", isFaceEnrollNeeded=" + z2 + ", fpEnrolledCount=" + size + ", userId=" + identifier + ", isWorkProfile=" + zIsManagedProfile);
        if (z && z2 && zEquals) {
            Context context = this.mContext;
            if (zIsManagedProfile) {
                i4 = R.string.biometric_safety_issue_setup_fingerprint_face_unlock_title_for_work;
            } else {
                i4 = R.string.biometric_safety_issue_setup_fingerprint_face_unlock_title;
            }
            string4 = context.getString(i4);
            string5 = this.mContext.getString(R.string.biometric_safety_issue_setup_fingerprint_face_unlock_subtitle);
            string6 = this.mContext.getString(R.string.biometric_safety_issue_setup_action_title);
            pendingIntentCreateActionPendingIntent = createActionPendingIntent(BiometricEnrollActivity.class, 0, identifier);
        } else {
            if (z && !z2 && zEquals) {
                Context context2 = this.mContext;
                if (zIsManagedProfile) {
                    i3 = R.string.biometric_safety_issue_setup_fingerprint_unlock_title_for_work;
                } else {
                    i3 = R.string.biometric_safety_issue_setup_fingerprint_unlock_title;
                }
                string = context2.getString(i3);
                string2 = this.mContext.getString(R.string.biometric_safety_issue_setup_fingerprint_unlock_subtitle);
                string3 = this.mContext.getString(R.string.biometric_safety_issue_setup_action_title);
                pendingIntentCreateActionPendingIntent = createActionPendingIntent(FingerprintEnroll.class, 0, identifier);
            } else if (!z && z2 && zEquals2) {
                Context context3 = this.mContext;
                if (zIsManagedProfile) {
                    i2 = R.string.biometric_safety_issue_setup_face_unlock_title_for_work;
                } else {
                    i2 = R.string.biometric_safety_issue_setup_face_unlock_title;
                }
                string4 = context3.getString(i2);
                string5 = this.mContext.getString(R.string.biometric_safety_issue_setup_face_unlock_subtitle);
                string6 = this.mContext.getString(R.string.biometric_safety_issue_setup_action_title);
                pendingIntentCreateActionPendingIntent = createActionPendingIntent(FaceEnroll.class, 0, identifier);
            } else {
                if (size != 1 || z2 || !zEquals) {
                    return null;
                }
                Context context4 = this.mContext;
                if (zIsManagedProfile) {
                    i = R.string.biometric_safety_issue_add_more_fingerprints_title_for_work;
                } else {
                    i = R.string.biometric_safety_issue_add_more_fingerprints_title;
                }
                string = context4.getString(i);
                string2 = this.mContext.getString(R.string.biometric_safety_issue_add_more_fingerprints_subtitle);
                string3 = this.mContext.getString(R.string.biometric_safety_issue_add_more_action_title);
                pendingIntentCreateActionPendingIntent = createActionPendingIntent(FingerprintEnroll.class, 0, identifier);
            }
            str2 = string;
            str3 = string2;
            string6 = string3;
            return new SafetySourceIssue.Builder("BiometricUnlockIssue", str2, str3, 200, "BiometricUnlockIssueType").setIssueCategory(100).addAction(new SafetySourceIssue.Action.Builder("SetBiometricUnlockActionId", string6, pendingIntentCreateActionPendingIntent).build()).setIssueActionability(0).setOnDismissPendingIntent(createOnDismissPendingIntent()).build();
        }
        str2 = string4;
        str3 = string5;
        return new SafetySourceIssue.Builder("BiometricUnlockIssue", str2, str3, 200, "BiometricUnlockIssueType").setIssueCategory(100).addAction(new SafetySourceIssue.Action.Builder("SetBiometricUnlockActionId", string6, pendingIntentCreateActionPendingIntent).build()).setIssueActionability(0).setOnDismissPendingIntent(createOnDismissPendingIntent()).build();
    }

    private PendingIntent createActionPendingIntent(Class cls, int i, int i2) {
        return PendingIntent.getActivity(this.mContext, i, new Intent(this.mContext, (Class<?>) cls).putExtra("launch_from_safety_source_issue", true).putExtra("android.intent.extra.USER_ID", i2), 67108864);
    }

    private PendingIntent createOnDismissPendingIntent() {
        return PendingIntent.getBroadcast(this.mContext, 0, new Intent("biometric_safety_issue_dismiss_action"), 67108864);
    }
}

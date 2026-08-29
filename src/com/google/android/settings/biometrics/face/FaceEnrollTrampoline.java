package com.google.android.settings.biometrics.face;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import com.google.android.settings.R$bool;
import com.google.android.settings.R$string;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
public class FaceEnrollTrampoline extends FragmentActivity {
    private Intent mExtras;
    private boolean mNextLaunched;
    private int mUserId;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!getPackageName().equals(getLaunchedFromPackage())) {
            Log.w("FaceEnrollTrampoline", "Invalid caller: " + getLaunchedFromPackage());
            finish();
            this.mNextLaunched = true;
            return;
        }
        this.mUserId = getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        if (bundle != null) {
            this.mNextLaunched = bundle.getBoolean("next_launched");
        }
        this.mExtras = getIntent();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("next_launched", this.mNextLaunched);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == 4) {
            Intent intent2 = new Intent(this.mExtras);
            intent2.putExtra("accessibility_diversity", false);
            intent2.putExtra("from_multi_timeout", true);
            startEnrollActivity(intent2);
            return;
        }
        if (i2 == 5) {
            startEnrollActivity(this.mExtras);
            return;
        }
        if (i2 == 11) {
            setResult(0, intent);
            finish();
        } else if (i2 == 13) {
            Log.d("FaceEnrollTrampoline", "Overlay reported no pending intent... launching enrollment anyway");
            startEnrollActivity(this.mExtras);
        } else {
            setResult(i2, intent);
            finish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.mNextLaunched) {
            return;
        }
        this.mNextLaunched = true;
        if (Build.IS_ENG || Build.IS_USERDEBUG) {
            if (new FaceEnrollOverlayLauncher(this).maybeLaunchOverlayParticipationFlow(this.mExtras)) {
                return;
            }
            Log.e("FaceEnrollTrampoline", "Using FaceEnrollParticipation");
            Intent intent = new Intent(this, (Class<?>) FaceEnrollParticipation.class);
            intent.putExtras(this.mExtras);
            startActivityForResult(intent, 2);
            return;
        }
        startEnrollActivity(this.mExtras);
    }

    private void startEnrollActivity(Intent intent) {
        Intent intent2;
        boolean z = getResources().getBoolean(R$bool.config_face_enroll_use_traffic_light);
        if (z) {
            intent2 = new Intent("com.google.android.settings.future.biometrics.faceenroll.action.ENROLL");
        } else {
            intent2 = new Intent(this, (Class<?>) FaceEnrollEnrolling.class);
        }
        if (z) {
            String string = getString(R$string.config_face_enroll_traffic_light_package);
            if (TextUtils.isEmpty(string)) {
                Segment$$ExternalSyntheticBUOutline1.m("Package name must not be empty");
                return;
            }
            intent2.setPackage(string);
        }
        intent2.putExtras(intent);
        intent2.putExtra("android.intent.extra.USER_ID", this.mUserId);
        startActivityForResult(intent2, 1);
    }
}

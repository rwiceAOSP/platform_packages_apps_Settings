package com.google.android.settings.biometrics.fingerprint.feature;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.fragment.app.DialogFragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling;
import com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature;
import com.android.settingslib.Utils$$ExternalSyntheticBUOutline0;
import com.android.settingslib.widget.LottieColorUtils;
import com.google.android.settings.R$array;
import com.google.android.settings.R$color;
import com.google.android.settings.R$dimen;
import com.google.android.settings.R$id;
import com.google.android.settings.R$layout;
import com.google.android.settings.R$raw;
import com.google.android.settings.R$string;
import com.google.android.settings.biometrics.fingerprint.Utils;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes4.dex */
public class SfpsEnrollmentFeatureGoogleImpl implements SfpsEnrollmentFeature {
    private static final String TAG = "SfpsEnrollmentFeatureGoogleImpl";
    private float[] mEnrollStageThresholds = null;
    private boolean mIsAcquiredGood = false;
    private boolean mIsAcquiredImmobile = false;

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public CharSequence getFeaturedVendorString(Context context, int i, CharSequence charSequence) {
        return (context == null || i != 1000 || TextUtils.isEmpty(charSequence)) ? charSequence : getVendorString(context, 0);
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public void handleOnEnrollmentLottieComposition(LottieAnimationView lottieAnimationView) {
        Context context = lottieAnimationView.getContext();
        lottieAnimationView.setSpeed(1.0f);
        LottieColorUtils.applyDynamicColors(context, lottieAnimationView);
        if (isDarkMode(context)) {
            return;
        }
        final int color = context.getColor(R$color.sfps_enroll_grey300_light);
        lottieAnimationView.addValueCallback(new KeyPath("**", ".grey300", "**"), LottieProperty.COLOR_FILTER, new SimpleLottieValueCallback() { // from class: com.google.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeatureGoogleImpl$$ExternalSyntheticLambda0
            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
            public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                return SfpsEnrollmentFeatureGoogleImpl.$r8$lambda$JSLKtsslh6BtFwxYR0SK2hMzisQ(color, lottieFrameInfo);
            }
        });
    }

    public static /* synthetic */ ColorFilter $r8$lambda$JSLKtsslh6BtFwxYR0SK2hMzisQ(int i, LottieFrameInfo lottieFrameInfo) {
        return new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_ATOP);
    }

    private boolean isDarkMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public int getCurrentSfpsEnrollStage(int i, Function function) {
        if (i < ((Integer) function.apply(0)).intValue()) {
            return 0;
        }
        if (i < ((Integer) function.apply(1)).intValue()) {
            return 1;
        }
        if (i < ((Integer) function.apply(2)).intValue()) {
            return 2;
        }
        return i < ((Integer) function.apply(3)).intValue() ? 3 : 4;
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public int getFeaturedStageHeaderResource(int i) {
        if (i == 0) {
            return R$string.security_settings_fingerprint_enroll_repeat_title_immobile_overlay;
        }
        if (i == 1) {
            return R$string.security_settings_sfps_enroll_finger_center_title_immobile_overlay;
        }
        if (i == 2) {
            return R$string.security_settings_sfps_enroll_fingertip_title_immobile_overlay;
        }
        if (i == 3) {
            return R$string.security_settings_sfps_enroll_left_edge_title_immobile_overlay;
        }
        if (i == 4) {
            return R$string.security_settings_sfps_enroll_right_edge_title_immobile_overlay;
        }
        Utils$$ExternalSyntheticBUOutline0.m("Invalid stage: ", i);
        return 0;
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public int getSfpsEnrollLottiePerStage(int i) {
        if (i == 0) {
            return R$raw.sfps_lift_then_touch_lottie;
        }
        if (i == 1) {
            return R$raw.sfps_lift_then_touch_lottie;
        }
        if (i == 2) {
            return R$raw.sfps_lift_then_touch_lottie;
        }
        if (i == 3) {
            return R$raw.sfps_lift_then_touch_lottie;
        }
        if (i == 4) {
            return R$raw.sfps_reposition_finger_right_lottie;
        }
        Utils$$ExternalSyntheticBUOutline0.m("Invalid stage: ", i);
        return 0;
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public float getEnrollStageThreshold(Context context, int i) {
        if (this.mEnrollStageThresholds == null) {
            String[] stringArray = context.getResources().getStringArray(R$array.config_sfps_enroll_stage_thresholds);
            this.mEnrollStageThresholds = new float[stringArray.length];
            int i2 = 0;
            while (true) {
                float[] fArr = this.mEnrollStageThresholds;
                if (i2 >= fArr.length) {
                    break;
                }
                fArr[i2] = Float.parseFloat(stringArray[i2]);
                i2++;
            }
        }
        if (i >= 0) {
            float[] fArr2 = this.mEnrollStageThresholds;
            if (i <= fArr2.length) {
                if (i == fArr2.length) {
                    return 1.0f;
                }
                return fArr2[i];
            }
        }
        Log.w(TAG, "Unsupported enroll stage index: " + i);
        return i < 0 ? 0.0f : 1.0f;
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public boolean shouldUpdateTitleAndDescription() {
        return this.mIsAcquiredGood || !this.mIsAcquiredImmobile;
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public void handleOnAcquired(boolean z) {
        this.mIsAcquiredGood = z;
        this.mIsAcquiredImmobile = false;
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public void handleOnEnrollmentProgressChange(int i, int i2) {
        Log.d(TAG, "handleOnEnrollmentProgressChange: good=" + this.mIsAcquiredGood + ", immobile=" + this.mIsAcquiredImmobile + ", remaining=" + i2);
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public void handleOnEnrollmentHelp(int i, CharSequence charSequence, Supplier supplier) {
        this.mIsAcquiredImmobile = i == 9 || i == 1000;
        Log.d(TAG, "handleOnEnrollmentHelp: good=" + this.mIsAcquiredGood + ", immobile=" + this.mIsAcquiredImmobile + ", helpMsgId=" + i);
        if (supplier == null || TextUtils.isEmpty(charSequence)) {
            return;
        }
        showPauseEnrollmentDialogIfNecessary(i, charSequence.toString(), supplier);
    }

    private void showPauseEnrollmentDialogIfNecessary(int i, String str, Supplier supplier) {
        if (i == 1000) {
            boolean z = false;
            String vendorString = getVendorString((Context) supplier.get(), 0);
            if (!TextUtils.isEmpty(vendorString) && vendorString.equals(str)) {
                getImmobileDialog().show(((FingerprintEnrollEnrolling) supplier.get()).getSupportFragmentManager(), "immobile_dialog");
                return;
            }
            boolean zIsEmpty = TextUtils.isEmpty(vendorString);
            String str2 = TAG;
            StringBuilder sb = new StringBuilder("ImmobileHelpDialog not showing: has vendor string=");
            sb.append(!zIsEmpty);
            sb.append(", msg matches=");
            if (!zIsEmpty && vendorString.equals(str)) {
                z = true;
            }
            sb.append(z);
            Log.d(str2, sb.toString());
        }
    }

    public ImmobileHelpDialog getImmobileDialog() {
        return new ImmobileHelpDialog();
    }

    public String getVendorString(Context context, int i) {
        String[] stringArray;
        if (i >= 0 && (stringArray = context.getResources().getStringArray(R$array.fingerprint_acquired_vendor)) != null && i < stringArray.length) {
            return stringArray[i];
        }
        return null;
    }

    public class ImmobileHelpDialog extends DialogFragment {
        @Override // androidx.fragment.app.DialogFragment
        public Dialog onCreateDialog(Bundle bundle) {
            return new AlertDialog.Builder(getContext()).setView(R$layout.biometric_enrollment_immobile_dialog).create();
        }

        @Override // androidx.fragment.app.Fragment
        public void onResume() {
            Dialog dialog = getDialog();
            if (dialog != null) {
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                View viewFindViewById = dialog.findViewById(R$id.immobile_continue_btn);
                viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeatureGoogleImpl$ImmobileHelpDialog$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onResume$0(view);
                    }
                });
                setUpTouchDelegate(dialog.findViewById(R$id.biometric_enrollment_immobile_help), viewFindViewById);
                adjustDimensionsIfNeeded(dialog);
            } else {
                Log.w(SfpsEnrollmentFeatureGoogleImpl.TAG, "No dialog created!");
                Utils.resumeEnroll();
            }
            super.onResume();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResume$0(View view) {
            Utils.resumeEnroll();
            getDialog().dismiss();
        }

        private void setUpTouchDelegate(final View view, final View view2) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(this) { // from class: com.google.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeatureGoogleImpl.ImmobileHelpDialog.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    view.setTouchDelegate(new TouchDelegate(new Rect(view.getLeft(), view2.getTop(), view.getRight(), view2.getBottom()), view2));
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }

        private void adjustDimensionsIfNeeded(Dialog dialog) {
            int i = getResources().getDisplayMetrics().widthPixels;
            int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.immobile_dialog_width);
            dialog.getWindow().setLayout(dimensionPixelSize > i ? -2 : dimensionPixelSize, -2);
            Log.d(SfpsEnrollmentFeatureGoogleImpl.TAG, "Immobile dialog: dialogWidth=" + dimensionPixelSize + ", displayWidth=" + i);
        }
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public Animator getHelpAnimator(View view) {
        float fApplyDimension = TypedValue.applyDimension(1, 10.0f, view.getContext().getResources().getDisplayMetrics());
        float f = (-1.0f) * fApplyDimension;
        ObjectAnimator duration = ObjectAnimator.ofFloat(view, "translationX", f).setDuration(67L);
        float f2 = 2.0f * fApplyDimension;
        ObjectAnimator duration2 = ObjectAnimator.ofFloat(view, "translationX", f2).setDuration(100L);
        ObjectAnimator duration3 = ObjectAnimator.ofFloat(view, "translationX", fApplyDimension * (-2.0f)).setDuration(100L);
        ObjectAnimator duration4 = ObjectAnimator.ofFloat(view, "translationX", f2).setDuration(100L);
        ObjectAnimator duration5 = ObjectAnimator.ofFloat(view, "translationX", f).setDuration(150L);
        Interpolator interpolatorCreate = PathInterpolatorCompat.create(0.6f, 0.0f, 0.4f, 1.0f);
        duration.setInterpolator(interpolatorCreate);
        duration2.setInterpolator(interpolatorCreate);
        duration3.setInterpolator(interpolatorCreate);
        duration4.setInterpolator(interpolatorCreate);
        duration5.setInterpolator(new FastOutSlowInInterpolator());
        duration.setAutoCancel(false);
        duration2.setAutoCancel(false);
        duration3.setAutoCancel(false);
        duration4.setAutoCancel(false);
        duration5.setAutoCancel(false);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(duration, duration2, duration3, duration4, duration5);
        return animatorSet;
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public boolean shouldAdjustHeaderText(Configuration configuration, boolean z) {
        return !z || configuration.orientation == 2;
    }
}

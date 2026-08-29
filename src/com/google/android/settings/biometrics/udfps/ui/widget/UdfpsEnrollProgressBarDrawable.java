package com.google.android.settings.biometrics.udfps.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import com.android.settings.R;

/* JADX INFO: loaded from: classes4.dex */
public class UdfpsEnrollProgressBarDrawable extends Drawable {
    private boolean mAfterFirstTouch;
    private ValueAnimator mBackgroundColorAnimator;
    private final ValueAnimator.AnimatorUpdateListener mBackgroundColorUpdateListener;
    private final Paint mBackgroundPaint;
    private ValueAnimator mCheckmarkAnimator;
    private final Drawable mCheckmarkDrawable;
    private final Interpolator mCheckmarkInterpolator;
    private final ValueAnimator.AnimatorUpdateListener mCheckmarkUpdateListener;
    private final Context mContext;
    private int mEnrollProgress;
    private int mEnrollProgressHelp;
    private int mEnrollProgressHelpWithTalkback;
    private ValueAnimator mFillColorAnimator;
    private final ValueAnimator.AnimatorUpdateListener mFillColorUpdateListener;
    final Paint mFillPaint;
    private final int mHelpColor;
    private final boolean mIsAccessibilityEnabled;
    private int mMovingTargetFill;
    private int mMovingTargetFillError;
    private OnDrawFinishedListener mOnDrawFinishedListener;
    private final int mOnFirstBucketFailedColor;
    private ValueAnimator mProgressAnimator;
    private final int mProgressColor;
    private final ValueAnimator.AnimatorUpdateListener mProgressUpdateListener;
    private final float mStrokeWidthPx;
    private final Vibrator mVibrator;
    private static final Interpolator DEACCEL = new DecelerateInterpolator();
    private static final VibrationEffect VIBRATE_EFFECT_ERROR = VibrationEffect.createWaveform(new long[]{0, 5, 55, 60}, -1);
    private static final VibrationAttributes FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES = VibrationAttributes.createForUsage(66);
    private static final VibrationAttributes HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(50);
    private static final VibrationEffect SUCCESS_VIBRATION_EFFECT = VibrationEffect.get(0);
    private int mRemainingSteps = 0;
    private int mTotalSteps = 0;
    private float mProgress = 0.0f;
    private boolean mComplete = false;
    private float mCheckmarkScale = 0.0f;

    interface OnDrawFinishedListener {
        void onDrawFinished();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public UdfpsEnrollProgressBarDrawable(Context context, AttributeSet attributeSet) {
        this.mContext = context;
        loadResources(context, attributeSet);
        float f = (context.getResources().getDisplayMetrics().densityDpi / 160.0f) * 12.0f;
        this.mStrokeWidthPx = f;
        int i = this.mEnrollProgress;
        this.mProgressColor = i;
        boolean zIsTouchExplorationEnabled = ((AccessibilityManager) context.getSystemService(AccessibilityManager.class)).isTouchExplorationEnabled();
        this.mIsAccessibilityEnabled = zIsTouchExplorationEnabled;
        this.mOnFirstBucketFailedColor = this.mMovingTargetFillError;
        if (!zIsTouchExplorationEnabled) {
            this.mHelpColor = this.mEnrollProgressHelp;
        } else {
            this.mHelpColor = this.mEnrollProgressHelpWithTalkback;
        }
        Drawable drawable = context.getDrawable(R.drawable.udfps_enroll_checkmark);
        this.mCheckmarkDrawable = drawable;
        drawable.mutate();
        this.mCheckmarkInterpolator = new OvershootInterpolator();
        Paint paint = new Paint();
        this.mBackgroundPaint = paint;
        paint.setStrokeWidth(f);
        paint.setColor(this.mMovingTargetFill);
        paint.setAntiAlias(true);
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        Paint.Cap cap = Paint.Cap.ROUND;
        paint.setStrokeCap(cap);
        Paint paint2 = new Paint();
        this.mFillPaint = paint2;
        paint2.setStrokeWidth(f);
        paint2.setColor(i);
        paint2.setAntiAlias(true);
        paint2.setStyle(style);
        paint2.setStrokeCap(cap);
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
        this.mProgressUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$new$0(valueAnimator);
            }
        };
        this.mFillColorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$new$1(valueAnimator);
            }
        };
        this.mCheckmarkUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$new$2(valueAnimator);
            }
        };
        this.mBackgroundColorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$new$3(valueAnimator);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        this.mProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ValueAnimator valueAnimator) {
        this.mFillPaint.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(ValueAnimator valueAnimator) {
        this.mCheckmarkScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(ValueAnimator valueAnimator) {
        this.mBackgroundPaint.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        invalidateSelf();
    }

    void onEnrollmentProgress(int i, int i2) {
        this.mAfterFirstTouch = true;
        performHaptic(i, i2, false);
        updateState(i, i2, false);
    }

    void onEnrollmentHelp(int i, int i2) {
        performHaptic(i, i2, true);
        updateState(i, i2, true);
    }

    void onLastStepAcquired() {
        performHaptic(0, this.mTotalSteps, false);
        updateState(0, this.mTotalSteps, false);
    }

    private void updateState(int i, int i2, boolean z) {
        updateProgress(i, i2);
        updateFillColor(z);
    }

    private void performHaptic(int i, int i2, boolean z) {
        if (this.mRemainingSteps == i && this.mTotalSteps == i2) {
            return;
        }
        Vibrator vibrator = this.mVibrator;
        if (z) {
            if (vibrator == null || !this.mIsAccessibilityEnabled) {
                return;
            }
            vibrator.vibrate(Process.myUid(), this.mContext.getOpPackageName(), VIBRATE_EFFECT_ERROR, getClass().getSimpleName().concat("::onEnrollmentHelp"), FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES);
            return;
        }
        if (vibrator != null) {
            if (i == -1 && this.mIsAccessibilityEnabled) {
                vibrator.vibrate(Process.myUid(), this.mContext.getOpPackageName(), VIBRATE_EFFECT_ERROR, getClass().getSimpleName().concat("::onFirstTouchError"), FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES);
            } else {
                if (i == -1 || this.mIsAccessibilityEnabled) {
                    return;
                }
                vibrator.vibrate(Process.myUid(), this.mContext.getOpPackageName(), SUCCESS_VIBRATION_EFFECT, getClass().getSimpleName().concat("::OnEnrollmentProgress"), HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES);
            }
        }
    }

    private void updateProgress(int i, int i2) {
        if (this.mRemainingSteps == i && this.mTotalSteps == i2) {
            return;
        }
        this.mRemainingSteps = i;
        this.mTotalSteps = i2;
        int iMax = Math.max(0, i2 - i);
        boolean z = this.mAfterFirstTouch;
        if (z) {
            iMax++;
        }
        int i3 = this.mTotalSteps;
        if (z) {
            i3++;
        }
        float fMin = Math.min(1.0f, iMax / i3);
        ValueAnimator valueAnimator = this.mProgressAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mProgressAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mProgress, fMin);
        this.mProgressAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(400L);
        this.mProgressAnimator.addUpdateListener(this.mProgressUpdateListener);
        this.mProgressAnimator.start();
        if (i == 0) {
            startCompletionAnimation();
        } else if (i > 0) {
            rollBackCompletionAnimation();
        }
    }

    private void animateBackgroundColor() {
        ValueAnimator valueAnimator = this.mBackgroundColorAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mBackgroundColorAnimator.end();
        }
        ValueAnimator valueAnimatorOfArgb = ValueAnimator.ofArgb(this.mBackgroundPaint.getColor(), this.mOnFirstBucketFailedColor);
        this.mBackgroundColorAnimator = valueAnimatorOfArgb;
        valueAnimatorOfArgb.setDuration(350L);
        this.mBackgroundColorAnimator.setRepeatCount(1);
        this.mBackgroundColorAnimator.setRepeatMode(2);
        this.mBackgroundColorAnimator.setInterpolator(DEACCEL);
        this.mBackgroundColorAnimator.addUpdateListener(this.mBackgroundColorUpdateListener);
        this.mBackgroundColorAnimator.start();
    }

    private void updateFillColor(boolean z) {
        if (!this.mAfterFirstTouch && z) {
            animateBackgroundColor();
            return;
        }
        ValueAnimator valueAnimator = this.mFillColorAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mFillColorAnimator.end();
        }
        ValueAnimator valueAnimatorOfArgb = ValueAnimator.ofArgb(this.mFillPaint.getColor(), z ? this.mHelpColor : this.mProgressColor);
        this.mFillColorAnimator = valueAnimatorOfArgb;
        valueAnimatorOfArgb.setDuration(350L);
        this.mFillColorAnimator.setRepeatCount(1);
        this.mFillColorAnimator.setRepeatMode(2);
        this.mFillColorAnimator.setInterpolator(DEACCEL);
        this.mFillColorAnimator.addUpdateListener(this.mFillColorUpdateListener);
        this.mFillColorAnimator.start();
    }

    private void startCompletionAnimation() {
        if (this.mComplete) {
            return;
        }
        this.mComplete = true;
        ValueAnimator valueAnimator = this.mCheckmarkAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mCheckmarkAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mCheckmarkScale, 1.0f);
        this.mCheckmarkAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setStartDelay(200L);
        this.mCheckmarkAnimator.setDuration(300L);
        this.mCheckmarkAnimator.setInterpolator(this.mCheckmarkInterpolator);
        this.mCheckmarkAnimator.addUpdateListener(this.mCheckmarkUpdateListener);
        this.mCheckmarkAnimator.start();
    }

    private void rollBackCompletionAnimation() {
        if (this.mComplete) {
            this.mComplete = false;
            ValueAnimator valueAnimator = this.mCheckmarkAnimator;
            long jRound = Math.round((valueAnimator != null ? valueAnimator.getAnimatedFraction() : 0.0f) * 200.0f);
            ValueAnimator valueAnimator2 = this.mCheckmarkAnimator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.mCheckmarkAnimator.cancel();
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mCheckmarkScale, 0.0f);
            this.mCheckmarkAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.setDuration(jRound);
            this.mCheckmarkAnimator.addUpdateListener(this.mCheckmarkUpdateListener);
            this.mCheckmarkAnimator.start();
        }
    }

    private void loadResources(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BiometricsEnrollView, R.attr.biometricsEnrollStyle, R.style.BiometricsEnrollStyle);
        this.mMovingTargetFill = typedArrayObtainStyledAttributes.getColor(R.styleable.BiometricsEnrollView_biometricsMovingTargetFill, 0);
        this.mMovingTargetFillError = typedArrayObtainStyledAttributes.getColor(R.styleable.BiometricsEnrollView_biometricsMovingTargetFillError, 0);
        this.mEnrollProgress = typedArrayObtainStyledAttributes.getColor(R.styleable.BiometricsEnrollView_biometricsEnrollProgress, 0);
        this.mEnrollProgressHelp = typedArrayObtainStyledAttributes.getColor(R.styleable.BiometricsEnrollView_biometricsEnrollProgressHelp, 0);
        this.mEnrollProgressHelpWithTalkback = typedArrayObtainStyledAttributes.getColor(R.styleable.BiometricsEnrollView_biometricsEnrollProgressHelpWithTalkback, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Canvas canvas2;
        canvas.save();
        canvas.rotate(-90.0f, getBounds().centerX(), getBounds().centerY());
        float f = this.mStrokeWidthPx / 2.0f;
        if (this.mProgress < 1.0f) {
            canvas2 = canvas;
            canvas2.drawArc(f, f, getBounds().right - f, getBounds().bottom - f, 0.0f, 360.0f, false, this.mBackgroundPaint);
        } else {
            canvas2 = canvas;
        }
        if (this.mProgress > 0.0f) {
            canvas2.drawArc(f, f, getBounds().right - f, getBounds().bottom - f, 0.0f, this.mProgress * 360.0f, false, this.mFillPaint);
        }
        canvas2.restore();
        if (this.mCheckmarkScale > 0.0f) {
            float fSqrt = ((float) Math.sqrt(2.0d)) / 2.0f;
            float fWidth = ((getBounds().width() - this.mStrokeWidthPx) / 2.0f) * fSqrt;
            float fHeight = ((getBounds().height() - this.mStrokeWidthPx) / 2.0f) * fSqrt;
            float fCenterX = getBounds().centerX() + fWidth;
            float fCenterY = getBounds().centerY() + fHeight;
            float intrinsicWidth = (this.mCheckmarkDrawable.getIntrinsicWidth() / 2.0f) * this.mCheckmarkScale;
            float intrinsicHeight = (this.mCheckmarkDrawable.getIntrinsicHeight() / 2.0f) * this.mCheckmarkScale;
            this.mCheckmarkDrawable.setBounds(Math.round(fCenterX - intrinsicWidth), Math.round(fCenterY - intrinsicHeight), Math.round(fCenterX + intrinsicWidth), Math.round(fCenterY + intrinsicHeight));
            this.mCheckmarkDrawable.draw(canvas2);
        }
        OnDrawFinishedListener onDrawFinishedListener = this.mOnDrawFinishedListener;
        if (onDrawFinishedListener != null) {
            onDrawFinishedListener.onDrawFinished();
        }
    }

    void addOnDrawFinishedListener(OnDrawFinishedListener onDrawFinishedListener) {
        this.mOnDrawFinishedListener = onDrawFinishedListener;
    }

    void deleteOnDrawFinishedListener() {
        this.mOnDrawFinishedListener = null;
    }
}

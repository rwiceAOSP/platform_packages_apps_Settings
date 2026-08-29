package com.google.android.settings.biometrics.udfps.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.SystemProperties;
import android.util.AttributeSet;
import android.util.PathParser;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.android.settings.R;

/* JADX INFO: loaded from: classes4.dex */
public class UdfpsEnrollDrawable extends Drawable {
    private int mAlpha;
    private final Paint mBlueFill;
    float mCurrentX;
    float mCurrentY;
    private UdfpsEnrollHelper mEnrollHelper;
    private int mEnrollIcon;
    private final ShapeDrawable mFingerprintDrawable;
    private int mMovingTargetFill;
    private final Drawable mMovingTargetFpIcon;
    private final Paint mSensorOutlinePaint;
    private RectF mSensorRect;
    private final Animator.AnimatorListener mTargetAnimListener;
    AnimatorSet mTargetAnimatorSet;
    private boolean mSkipDraw = false;
    float mCurrentScale = 1.0f;
    private boolean mShouldShowTipHint = false;
    private boolean mShouldShowEdgeHint = false;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    UdfpsEnrollDrawable(Context context, AttributeSet attributeSet) {
        ShapeDrawable shapeDrawableDefaultFactory = defaultFactory(context);
        this.mFingerprintDrawable = shapeDrawableDefaultFactory;
        loadResources(context, attributeSet);
        Paint paint = new Paint(0);
        this.mSensorOutlinePaint = paint;
        paint.setAntiAlias(true);
        paint.setColor(this.mMovingTargetFill);
        Paint.Style style = Paint.Style.FILL;
        paint.setStyle(style);
        Paint paint2 = new Paint(0);
        this.mBlueFill = paint2;
        paint2.setAntiAlias(true);
        paint2.setColor(this.mMovingTargetFill);
        paint2.setStyle(style);
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_enrollment_fingerprint, null);
        this.mMovingTargetFpIcon = drawable;
        drawable.setTint(this.mEnrollIcon);
        drawable.mutate();
        shapeDrawableDefaultFactory.setTint(this.mEnrollIcon);
        setAlpha(255);
        this.mTargetAnimListener = new Animator.AnimatorListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollDrawable.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                UdfpsEnrollDrawable.this.updateTipHintVisibility();
            }
        };
    }

    void onSensorRectUpdated(RectF rectF) {
        int iHeight = ((int) rectF.height()) / 8;
        updateFingerprintIconBounds(new Rect(((int) rectF.left) + iHeight, ((int) rectF.top) + iHeight, ((int) rectF.right) - iHeight, ((int) rectF.bottom) - iHeight));
        this.mSensorRect = rectF;
    }

    void setEnrollHelper(UdfpsEnrollHelper udfpsEnrollHelper) {
        this.mEnrollHelper = udfpsEnrollHelper;
    }

    void setShouldSkipDraw(boolean z) {
        if (this.mSkipDraw == z) {
            return;
        }
        this.mSkipDraw = z;
        invalidateSelf();
    }

    void updateFingerprintIconBounds(Rect rect) {
        this.mFingerprintDrawable.setBounds(rect);
        invalidateSelf();
        this.mMovingTargetFpIcon.setBounds(rect);
        invalidateSelf();
    }

    void onEnrollmentProgress(int i, int i2) {
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        if (udfpsEnrollHelper == null) {
            return;
        }
        if (!udfpsEnrollHelper.isCenterEnrollmentStage()) {
            AnimatorSet animatorSet = this.mTargetAnimatorSet;
            if (animatorSet != null && animatorSet.isRunning()) {
                this.mTargetAnimatorSet.end();
            }
            PointF nextGuidedEnrollmentPoint = this.mEnrollHelper.getNextGuidedEnrollmentPoint();
            float f = this.mCurrentX;
            float f2 = nextGuidedEnrollmentPoint.x;
            if (f != f2 || this.mCurrentY != nextGuidedEnrollmentPoint.y) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollDrawable$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$onEnrollmentProgress$0(valueAnimator);
                    }
                });
                ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(this.mCurrentY, nextGuidedEnrollmentPoint.y);
                valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollDrawable$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$onEnrollmentProgress$1(valueAnimator);
                    }
                });
                long j = (nextGuidedEnrollmentPoint.x == 0.0f && nextGuidedEnrollmentPoint.y == 0.0f) ? 600L : 800L;
                ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(0.0f, 3.1415927f);
                valueAnimatorOfFloat3.setDuration(j);
                valueAnimatorOfFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollDrawable$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$onEnrollmentProgress$2(valueAnimator);
                    }
                });
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.mTargetAnimatorSet = animatorSet2;
                animatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
                this.mTargetAnimatorSet.setDuration(j);
                this.mTargetAnimatorSet.addListener(this.mTargetAnimListener);
                this.mTargetAnimatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2, valueAnimatorOfFloat3);
                this.mTargetAnimatorSet.start();
            } else {
                updateTipHintVisibility();
            }
        } else {
            updateTipHintVisibility();
        }
        updateEdgeHintVisibility();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollmentProgress$0(ValueAnimator valueAnimator) {
        this.mCurrentX = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollmentProgress$1(ValueAnimator valueAnimator) {
        this.mCurrentY = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollmentProgress$2(ValueAnimator valueAnimator) {
        this.mCurrentScale = (((float) Math.sin(((Float) valueAnimator.getAnimatedValue()).floatValue())) * 0.25f) + 1.0f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        RectF rectF;
        if (this.mSkipDraw) {
            return;
        }
        if (SystemProperties.getBoolean("debug.udfps_show_sensor_bounds_outline", false) && (rectF = this.mSensorRect) != null) {
            visualizeFingerprintSensorOutline(canvas, rectF);
        }
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        if (udfpsEnrollHelper != null && !udfpsEnrollHelper.isCenterEnrollmentStage()) {
            canvas.save();
            canvas.translate(this.mCurrentX, this.mCurrentY);
            RectF rectF2 = this.mSensorRect;
            if (rectF2 != null) {
                float f = this.mCurrentScale;
                canvas.scale(f, f, rectF2.centerX(), this.mSensorRect.centerY());
                canvas.drawOval(this.mSensorRect, this.mBlueFill);
            }
            this.mMovingTargetFpIcon.draw(canvas);
            canvas.restore();
            return;
        }
        RectF rectF3 = this.mSensorRect;
        if (rectF3 != null) {
            canvas.drawOval(rectF3, this.mSensorOutlinePaint);
        }
        this.mFingerprintDrawable.draw(canvas);
        this.mFingerprintDrawable.setAlpha(getAlpha());
        this.mSensorOutlinePaint.setAlpha(getAlpha());
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mAlpha = i;
        this.mFingerprintDrawable.setAlpha(i);
        this.mSensorOutlinePaint.setAlpha(i);
        this.mBlueFill.setAlpha(i);
        this.mMovingTargetFpIcon.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTipHintVisibility() {
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        boolean z = udfpsEnrollHelper != null && udfpsEnrollHelper.isTipEnrollmentStage();
        if (this.mShouldShowTipHint == z) {
            return;
        }
        this.mShouldShowTipHint = z;
    }

    private void updateEdgeHintVisibility() {
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        boolean z = udfpsEnrollHelper != null && udfpsEnrollHelper.isEdgeEnrollmentStage();
        if (this.mShouldShowEdgeHint == z) {
            return;
        }
        this.mShouldShowEdgeHint = z;
    }

    private ShapeDrawable defaultFactory(Context context) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new PathShape(PathParser.createPathFromPathData(context.getResources().getString(R.string.config_udfpsIcon)), 72.0f, 72.0f));
        shapeDrawable.mutate();
        shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
        shapeDrawable.getPaint().setStrokeCap(Paint.Cap.ROUND);
        shapeDrawable.getPaint().setStrokeWidth(3.0f);
        return shapeDrawable;
    }

    private void loadResources(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BiometricsEnrollView, R.attr.biometricsEnrollStyle, R.style.BiometricsEnrollStyle);
        this.mEnrollIcon = typedArrayObtainStyledAttributes.getColor(R.styleable.BiometricsEnrollView_biometricsEnrollIcon, 0);
        this.mMovingTargetFill = typedArrayObtainStyledAttributes.getColor(R.styleable.BiometricsEnrollView_biometricsMovingTargetFill, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void visualizeFingerprintSensorOutline(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        paint.setColor(-16711936);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), Math.min(rectF.width(), rectF.height()) / 2.0f, paint);
    }
}

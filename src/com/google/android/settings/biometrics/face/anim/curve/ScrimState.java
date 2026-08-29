package com.google.android.settings.biometrics.face.anim.curve;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

/* JADX INFO: loaded from: classes4.dex */
public class ScrimState {
    private final int mGoneColor;
    private final Paint mPaint;
    private ValueAnimator mScrimAnimator;
    private ValueAnimator.AnimatorUpdateListener mScrimAnimatorListener;
    private final int mShowingColor;
    private int mState = 0;

    public ScrimState(int i, int i2) {
        this.mGoneColor = i;
        this.mShowingColor = i2;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(i);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        this.mScrimAnimatorListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.settings.biometrics.face.anim.curve.ScrimState$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$new$0(valueAnimator);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        this.mPaint.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    public boolean isShowing() {
        return this.mState != 0;
    }

    public void fadeOut() {
        int i = this.mState;
        if (i == 0 || i == 2) {
            return;
        }
        this.mState = 2;
        ValueAnimator valueAnimatorOfArgb = ValueAnimator.ofArgb(this.mPaint.getColor(), this.mGoneColor);
        this.mScrimAnimator = valueAnimatorOfArgb;
        valueAnimatorOfArgb.addUpdateListener(this.mScrimAnimatorListener);
        this.mScrimAnimator.setDuration(200L);
        this.mScrimAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.settings.biometrics.face.anim.curve.ScrimState.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                ScrimState.this.mState = 0;
            }
        });
        this.mScrimAnimator.start();
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(0.0f, 0.0f, canvas.getWidth() / 2, this.mPaint);
    }
}

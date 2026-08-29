package com.google.android.settings.biometrics.udfps.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.RotationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.settings.R;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;

/* JADX INFO: loaded from: classes4.dex */
public class UdfpsEnrollView extends FrameLayout implements UdfpsEnrollHelper.Listener {
    private int mDefaultProgressBarRadius;
    private final UdfpsEnrollDrawable mFingerprintDrawable;
    private final UdfpsEnrollProgressBarDrawable mFingerprintProgressDrawable;
    private ImageView mFingerprintProgressView;
    private final Handler mHandler;
    private UdfpsOverlayParams mOverlayParams;
    private int mProgressBarRadius;
    private Rect mSensorRect;

    public UdfpsEnrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFingerprintDrawable = new UdfpsEnrollDrawable(((FrameLayout) this).mContext, attributeSet);
        this.mFingerprintProgressDrawable = new UdfpsEnrollProgressBarDrawable(context, attributeSet);
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        ((ImageView) findViewById(R.id.udfps_enroll_animation_fp_view)).setImageDrawable(this.mFingerprintDrawable);
        ImageView imageView = (ImageView) findViewById(R.id.udfps_enroll_animation_fp_progress_view);
        this.mFingerprintProgressView = imageView;
        imageView.setImageDrawable(this.mFingerprintProgressDrawable);
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollHelper.Listener
    public void onEnrollmentProgress(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onEnrollmentProgress$0(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollmentProgress$0(int i, int i2) {
        this.mFingerprintProgressDrawable.onEnrollmentProgress(i, i2);
        this.mFingerprintDrawable.onEnrollmentProgress(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollmentHelp$1(int i, int i2) {
        this.mFingerprintProgressDrawable.onEnrollmentHelp(i, i2);
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollHelper.Listener
    public void onEnrollmentHelp(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onEnrollmentHelp$1(i, i2);
            }
        });
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollHelper.Listener
    public void onAcquired(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onAcquired$2(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAcquired$2(boolean z) {
        onFingerUp();
        if (z) {
            this.mFingerprintProgressDrawable.onLastStepAcquired();
        }
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollHelper.Listener
    public void onPointerDown(int i) {
        onFingerDown();
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollHelper.Listener
    public void onPointerUp(int i) {
        onFingerUp();
    }

    public UdfpsOverlayParams getOverlayParams() {
        return this.mOverlayParams;
    }

    public void setOverlayParams(UdfpsOverlayParams udfpsOverlayParams) {
        this.mOverlayParams = udfpsOverlayParams;
        post(new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setOverlayParams$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOverlayParams$3() {
        int scaleFactor = (int) (this.mOverlayParams.getScaleFactor() * getContext().getResources().getInteger(R.integer.config_udfpsEnrollProgressBar));
        this.mDefaultProgressBarRadius = scaleFactor;
        if (this.mProgressBarRadius == 0) {
            this.mProgressBarRadius = scaleFactor;
        }
        this.mSensorRect = new Rect(this.mOverlayParams.getSensorBounds());
        onSensorRectUpdated();
    }

    public void setEnrollHelper(UdfpsEnrollHelper udfpsEnrollHelper) {
        this.mFingerprintDrawable.setEnrollHelper(udfpsEnrollHelper);
        udfpsEnrollHelper.setListener(this);
    }

    void setDecreasePadding(int i) {
        this.mProgressBarRadius -= i;
        onSensorRectUpdated();
    }

    Drawable getFingerprintProgressDrawable() {
        return this.mFingerprintProgressDrawable;
    }

    private void onSensorRectUpdated() {
        updateDimensions();
        this.mSensorRect.set(getPaddingX(), getPaddingY(), this.mOverlayParams.getSensorBounds().width() + getPaddingX(), this.mOverlayParams.getSensorBounds().height() + getPaddingY());
        this.mFingerprintDrawable.onSensorRectUpdated(new RectF(this.mSensorRect));
    }

    private void updateDimensions() {
        Rect rect = new Rect(this.mOverlayParams.getSensorBounds());
        int rotation = this.mOverlayParams.getRotation();
        if (rotation == 1 || rotation == 3) {
            RotationUtils.rotateBounds(rect, this.mOverlayParams.getNaturalDisplayWidth(), this.mOverlayParams.getNaturalDisplayHeight(), rotation);
        }
        ViewGroup viewGroup = (ViewGroup) getParent();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        if (rotation == 0 || rotation == 2) {
            int[] locationOnScreen = viewGroup.getLocationOnScreen();
            int i = locationOnScreen[0];
            int i2 = locationOnScreen[1];
            int width = i + viewGroup.getWidth();
            layoutParams.gravity = 53;
            int paddingX = (width - rect.right) - getPaddingX();
            int paddingY = (rect.top - i2) - getPaddingY();
            if (marginLayoutParams.rightMargin == paddingX && marginLayoutParams.topMargin == paddingY) {
                return;
            }
            marginLayoutParams.rightMargin = paddingX;
            marginLayoutParams.topMargin = paddingY;
            setLayoutParams(layoutParams);
        } else {
            int[] locationOnScreen2 = viewGroup.getLocationOnScreen();
            int i3 = locationOnScreen2[0];
            int i4 = locationOnScreen2[1];
            int width2 = viewGroup.getWidth() + i3;
            int height = i4 + viewGroup.getHeight();
            if (rotation == 1) {
                layoutParams.gravity = 85;
                marginLayoutParams.rightMargin = (width2 - rect.right) - getPaddingX();
                marginLayoutParams.bottomMargin = (height - rect.bottom) - getPaddingY();
            } else if (rotation == 3) {
                layoutParams.gravity = 83;
                marginLayoutParams.leftMargin = (rect.left - i3) - getPaddingX();
                marginLayoutParams.bottomMargin = (height - rect.bottom) - getPaddingY();
            }
        }
        layoutParams.height = rect.height() + (getPaddingX() * 2);
        layoutParams.width = rect.width() + (getPaddingY() * 2);
        setLayoutParams(layoutParams);
    }

    private void onFingerDown() {
        if (this.mOverlayParams.getSensorType() == 3) {
            this.mFingerprintDrawable.setShouldSkipDraw(true);
        }
        this.mFingerprintDrawable.invalidateSelf();
    }

    private void onFingerUp() {
        this.mFingerprintDrawable.setShouldSkipDraw(false);
        this.mFingerprintDrawable.invalidateSelf();
    }

    private int getPaddingX() {
        return this.mProgressBarRadius;
    }

    private int getPaddingY() {
        return this.mProgressBarRadius;
    }
}

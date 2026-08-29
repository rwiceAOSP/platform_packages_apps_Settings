package com.google.android.settings.biometrics.udfps.ui.widget;

import android.content.Context;
import android.graphics.PointF;
import android.hardware.fingerprint.FingerprintManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.accessibility.AccessibilityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class UdfpsEnrollHelper {
    private final boolean mAccessibilityEnabled;
    private final FingerprintManager mFingerprintManager;
    private final List mGuidedEnrollmentPoints;
    Listener mListener;
    private int mTotalSteps = -1;
    private int mRemainingSteps = -1;
    private int mLocationsEnrolled = 0;
    private int mCenterTouchCount = 0;
    private int mPace = 1;

    interface Listener {
        void onAcquired(boolean z);

        void onEnrollmentHelp(int i, int i2);

        void onEnrollmentProgress(int i, int i2);

        void onPointerDown(int i);

        void onPointerUp(int i);
    }

    public UdfpsEnrollHelper(Context context) {
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(FingerprintManager.class);
        Objects.requireNonNull(fingerprintManager);
        this.mFingerprintManager = fingerprintManager;
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        Objects.requireNonNull(accessibilityManager);
        this.mAccessibilityEnabled = accessibilityManager.isEnabled();
        ArrayList arrayList = new ArrayList();
        this.mGuidedEnrollmentPoints = arrayList;
        float fApplyDimension = TypedValue.applyDimension(5, 1.0f, context.getResources().getDisplayMetrics());
        Log.v("UdfpsEnrollHelper", "Using old coordinates");
        arrayList.add(new PointF(2.0f * fApplyDimension, 0.0f * fApplyDimension));
        arrayList.add(new PointF(0.87f * fApplyDimension, (-2.7f) * fApplyDimension));
        float f = (-1.8f) * fApplyDimension;
        arrayList.add(new PointF(f, (-1.31f) * fApplyDimension));
        arrayList.add(new PointF(f, 1.31f * fApplyDimension));
        arrayList.add(new PointF(0.88f * fApplyDimension, 2.7f * fApplyDimension));
        arrayList.add(new PointF(3.94f * fApplyDimension, (-1.06f) * fApplyDimension));
        arrayList.add(new PointF(2.9f * fApplyDimension, (-4.14f) * fApplyDimension));
        arrayList.add(new PointF((-0.52f) * fApplyDimension, (-5.95f) * fApplyDimension));
        float f2 = (-3.33f) * fApplyDimension;
        arrayList.add(new PointF(f2, f2));
        arrayList.add(new PointF((-3.99f) * fApplyDimension, (-0.35f) * fApplyDimension));
        arrayList.add(new PointF((-3.62f) * fApplyDimension, 2.54f * fApplyDimension));
        arrayList.add(new PointF((-1.49f) * fApplyDimension, 5.57f * fApplyDimension));
        arrayList.add(new PointF(2.29f * fApplyDimension, 4.92f * fApplyDimension));
        arrayList.add(new PointF(3.82f * fApplyDimension, fApplyDimension * 1.78f));
    }

    public void onEnrollmentProgress(int i, int i2) {
        int i3;
        if (this.mTotalSteps == -1) {
            this.mTotalSteps = i;
        }
        if (i2 != this.mRemainingSteps) {
            this.mLocationsEnrolled++;
            if (isCenterEnrollmentStage()) {
                this.mCenterTouchCount++;
            }
        }
        int i4 = this.mRemainingSteps;
        if (i4 > i2) {
            this.mPace = i4 - i2;
        }
        this.mRemainingSteps = i2;
        Listener listener = this.mListener;
        if (listener == null || (i3 = this.mTotalSteps) == -1) {
            return;
        }
        listener.onEnrollmentProgress(i2, i3);
    }

    public void onEnrollmentHelp() {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onEnrollmentHelp(this.mRemainingSteps, this.mTotalSteps);
        }
    }

    public void onAcquired(boolean z) {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onAcquired(z && animateIfLastStep());
        }
    }

    public void onPointerDown(int i) {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onPointerDown(i);
        }
    }

    public void onPointerUp(int i) {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onPointerUp(i);
        }
    }

    void setListener(Listener listener) {
        int i;
        this.mListener = listener;
        if (listener == null || (i = this.mTotalSteps) == -1) {
            return;
        }
        listener.onEnrollmentProgress(this.mRemainingSteps, i);
    }

    boolean isCenterEnrollmentStage() {
        int i;
        int i2 = this.mTotalSteps;
        return i2 == -1 || (i = this.mRemainingSteps) == -1 || i2 - i < getStageThresholdSteps(i2, 0);
    }

    boolean isTipEnrollmentStage() {
        int i;
        int i2;
        int i3 = this.mTotalSteps;
        return i3 != -1 && (i = this.mRemainingSteps) != -1 && (i2 = i3 - i) >= getStageThresholdSteps(i3, 1) && i2 < getStageThresholdSteps(this.mTotalSteps, 2);
    }

    boolean isEdgeEnrollmentStage() {
        int i;
        int i2 = this.mTotalSteps;
        return (i2 == -1 || (i = this.mRemainingSteps) == -1 || i2 - i < getStageThresholdSteps(i2, 2)) ? false : true;
    }

    PointF getNextGuidedEnrollmentPoint() {
        if (this.mAccessibilityEnabled || !isGuidedEnrollmentStage()) {
            return new PointF(0.0f, 0.0f);
        }
        int i = this.mLocationsEnrolled - this.mCenterTouchCount;
        List list = this.mGuidedEnrollmentPoints;
        PointF pointF = (PointF) list.get(i % list.size());
        return new PointF(pointF.x * 0.5f, pointF.y * 0.5f);
    }

    boolean animateIfLastStep() {
        if (this.mListener == null) {
            Log.e("UdfpsEnrollHelper", "animateIfLastStep, null listener");
            return false;
        }
        int i = this.mRemainingSteps;
        return i <= this.mPace && i >= 0;
    }

    private int getStageThresholdSteps(int i, int i2) {
        return Math.round(i * this.mFingerprintManager.getEnrollStageThreshold(i2));
    }

    private boolean isGuidedEnrollmentStage() {
        int i;
        int i2;
        int i3;
        return (this.mAccessibilityEnabled || (i = this.mTotalSteps) == -1 || (i2 = this.mRemainingSteps) == -1 || (i3 = i - i2) < getStageThresholdSteps(i, 0) || i3 >= getStageThresholdSteps(this.mTotalSteps, 1)) ? false : true;
    }
}

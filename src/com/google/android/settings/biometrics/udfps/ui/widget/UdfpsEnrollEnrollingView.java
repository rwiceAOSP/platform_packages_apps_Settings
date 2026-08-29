package com.google.android.settings.biometrics.udfps.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.settings.R;
import com.android.systemui.biometrics.UdfpsUtils;
import com.android.systemui.biometrics.shared.model.FingerprintSensor;
import com.android.systemui.biometrics.shared.model.FingerprintSensorTypeKt;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.google.android.settings.biometrics.R$layout;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.R$id;
import com.google.android.setupdesign.view.BottomScrollView;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class UdfpsEnrollEnrollingView extends GlifLayout {
    private final AccessibilityManager mAccessibilityManager;
    private int mDefaultHeaderScrollViewIndicator;
    private ObjectAnimator mHeaderScrollAnimator;
    private View mHeaderView;
    private boolean mIsLandscape;
    private UdfpsEnrollProgressBarDrawable.OnDrawFinishedListener mOnDrawFinishedListener;
    private ValueAnimator mProgressBarEnterAnimator;
    private int mRotation;
    private boolean mShouldShowLottie;
    private boolean mShouldUseReverseLandscape;
    private UdfpsEnrollHelper mUdfpsEnrollHelper;
    private UdfpsEnrollView mUdfpsEnrollView;
    private FingerprintSensor mUdfpsProps;
    private final UdfpsUtils mUdfpsUtils;
    private WindowManager mWindowManager;

    public UdfpsEnrollEnrollingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDefaultHeaderScrollViewIndicator = 0;
        updateLandscapeInfo();
        this.mUdfpsUtils = new UdfpsUtils();
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService(AccessibilityManager.class);
        this.mWindowManager = (WindowManager) ((FrameLayout) this).mContext.getSystemService(WindowManager.class);
    }

    private void updateLandscapeInfo() {
        int rotation = getContext().getDisplay().getRotation();
        this.mRotation = rotation;
        boolean z = false;
        this.mIsLandscape = rotation == 1 || rotation == 3;
        boolean z2 = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        int i = this.mRotation;
        if ((i == 1 && z2) || (i == 3 && !z2)) {
            z = true;
        }
        this.mShouldUseReverseLandscape = z;
    }

    @Override // com.google.android.setupdesign.GlifLayout, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mHeaderView = findViewById(R$id.sud_landscape_header_area);
        this.mUdfpsEnrollView = (UdfpsEnrollView) findViewById(com.google.android.settings.biometrics.udfps.R$id.udfps_animation_view);
    }

    @Override // com.google.android.setupdesign.GlifLayout, com.google.android.setupcompat.internal.TemplateLayout
    protected View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (layoutInflater.getContext().getResources().getConfiguration().orientation == 1) {
            i = R$layout.biometrics_glif_compact;
        }
        return super.onInflateTemplate(layoutInflater, i);
    }

    void setDecreasePadding(int i) {
        UdfpsEnrollView udfpsEnrollView = this.mUdfpsEnrollView;
        if (udfpsEnrollView != null) {
            udfpsEnrollView.setDecreasePadding(i);
        }
    }

    void onUdfpsSensorRectUpdated() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mProgressBarEnterAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        this.mProgressBarEnterAnimator.setDuration(500L);
        this.mProgressBarEnterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$onUdfpsSensorRectUpdated$0(valueAnimator);
            }
        });
        this.mProgressBarEnterAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                UdfpsEnrollEnrollingView.this.removeAllEnrollProgressAnimatorListener();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                UdfpsEnrollEnrollingView.this.removeAllEnrollProgressAnimatorListener();
            }
        });
        this.mProgressBarEnterAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUdfpsSensorRectUpdated$0(ValueAnimator valueAnimator) {
        setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAllEnrollProgressAnimatorListener() {
        this.mProgressBarEnterAnimator.removeAllUpdateListeners();
        this.mProgressBarEnterAnimator.removeAllListeners();
    }

    private int getScrollableGlifHeaderHeight(boolean z) {
        this.mShouldShowLottie = z;
        TypedValue typedValue = new TypedValue();
        if (isLargeDisplaySizeOrFontSize() && !z) {
            getResources().getValue(R.dimen.biometrics_glif_header_height_ratio_large, typedValue, true);
        } else {
            getResources().getValue(R.dimen.biometrics_glif_header_height_ratio, typedValue, true);
        }
        return (int) (getResources().getDisplayMetrics().heightPixels * typedValue.getFloat());
    }

    public void adjustScrollableHeaderHeight(ScrollView scrollView, boolean z) {
        ViewGroup.LayoutParams layoutParams = scrollView.getLayoutParams();
        layoutParams.height = getScrollableGlifHeaderHeight(z);
        scrollView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLargeDisplaySizeOrFontSize() {
        return getResources().getConfiguration().fontScale > 1.3f || getLargeDisplayScale() >= 2.8f;
    }

    private float getLargeDisplayScale() {
        Display defaultDisplay = this.mWindowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.scaledDensity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustProgressBarView() {
        UdfpsEnrollView udfpsEnrollView = this.mUdfpsEnrollView;
        if (udfpsEnrollView != null) {
            udfpsEnrollView.postDelayed(new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView.2
                @Override // java.lang.Runnable
                public void run() {
                    UdfpsEnrollEnrollingView.this.adjustUdfpsViewWithFooterBar();
                }
            }, 100L);
        }
    }

    public void adjustUdfpsViewWithFooterBar() {
        updateSensorOverlayParams(this.mUdfpsProps, this.mUdfpsEnrollHelper);
        ImageView imageView = (ImageView) findViewById(com.google.android.settings.biometrics.udfps.R$id.udfps_enroll_animation_fp_progress_view);
        final int onScreenPositionTop = ((getOnScreenPositionTop(imageView) + imageView.getDrawable().getBounds().height()) - imageView.getPaddingBottom()) + 2;
        final LinearLayout buttonContainer = ((FooterBarMixin) getMixin(FooterBarMixin.class)).getButtonContainer();
        buttonContainer.requestLayout();
        final Rect rect = new Rect();
        buttonContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView.3
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                buttonContainer.getGlobalVisibleRect(rect);
                int i = rect.top;
                if (onScreenPositionTop > i && (!UdfpsEnrollEnrollingView.this.mIsLandscape || !UdfpsEnrollEnrollingView.this.isLargeDisplaySizeOrFontSize())) {
                    UdfpsEnrollEnrollingView.this.setDecreasePadding(onScreenPositionTop - i);
                }
                buttonContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        onUdfpsSensorRectUpdated();
    }

    private int getOnScreenPositionTop(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return iArr[1];
    }

    public void headerVerticalScrolling(final ScrollView scrollView, final long j) {
        scrollView.post(new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$headerVerticalScrolling$1(scrollView, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$headerVerticalScrolling$1(ScrollView scrollView, long j) {
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getChildAt(0).getMeasuredHeight() - scrollView.getMeasuredHeight());
        this.mHeaderScrollAnimator = objectAnimatorOfInt;
        objectAnimatorOfInt.setDuration(j);
        this.mHeaderScrollAnimator.addListener(new AnonymousClass4(scrollView, j));
        this.mHeaderScrollAnimator.start();
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView$4, reason: invalid class name */
    class AnonymousClass4 implements Animator.AnimatorListener {
        final /* synthetic */ long val$duration;
        final /* synthetic */ ScrollView val$headerScrollView;

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        AnonymousClass4(ScrollView scrollView, long j) {
            this.val$headerScrollView = scrollView;
            this.val$duration = j;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            ScrollView scrollView = this.val$headerScrollView;
            final long j = this.val$duration;
            scrollView.post(new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAnimationEnd$1(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAnimationEnd$1(long j) {
            UdfpsEnrollEnrollingView.this.mHeaderScrollAnimator.removeAllListeners();
            if (UdfpsEnrollEnrollingView.this.mAccessibilityManager.isEnabled()) {
                new Handler().postDelayed(new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView$4$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onAnimationEnd$0();
                    }
                }, j + 200);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAnimationEnd$0() {
            if (UdfpsEnrollEnrollingView.this.mHeaderScrollAnimator.isRunning()) {
                return;
            }
            UdfpsEnrollEnrollingView.this.setFocusOnDescription();
        }
    }

    public void setFocusOnDescription() {
        final ScrollView scrollView = (ScrollView) findViewById(com.google.android.settings.biometrics.R$id.sud_header_scroll_view);
        final TextView descriptionTextView = getDescriptionTextView();
        if (descriptionTextView == null || descriptionTextView.getText().isEmpty()) {
            return;
        }
        descriptionTextView.post(new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                UdfpsEnrollEnrollingView.m8038$r8$lambda$e6HCq59pxeExz7_qftoVs9OpR8(scrollView, descriptionTextView);
            }
        });
    }

    /* JADX INFO: renamed from: $r8$lambda$e6HCq59pxeExz7_qf-toVs9OpR8, reason: not valid java name */
    public static /* synthetic */ void m8038$r8$lambda$e6HCq59pxeExz7_qftoVs9OpR8(ScrollView scrollView, TextView textView) {
        Rect rect = new Rect();
        scrollView.getHitRect(rect);
        if (textView.getLocalVisibleRect(rect)) {
            return;
        }
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);
        textView.requestFocus();
    }

    public void initView(FingerprintSensor fingerprintSensor, UdfpsEnrollHelper udfpsEnrollHelper) {
        setImportantForAccessibility(2);
        this.mUdfpsProps = fingerprintSensor;
        this.mUdfpsEnrollHelper = udfpsEnrollHelper;
        initUdfpsEnrollView(fingerprintSensor, udfpsEnrollHelper);
        FrameLayout frameLayout = (FrameLayout) findViewById(com.google.android.settings.biometrics.udfps.R$id.layout_container);
        frameLayout.setClipChildren(false);
        frameLayout.setClipToPadding(false);
        ViewGroup viewGroup = (ViewGroup) frameLayout.getParent();
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
        if (!this.mIsLandscape) {
            adjustPortraitPaddings();
            showPortraitSubTitle();
        } else if (this.mShouldUseReverseLandscape) {
            relayoutForReversedLandscape();
            showSubTitle();
        } else {
            showSubTitle();
        }
        setOnHoverListener();
        adjustProgressBarViewIfNeeded();
    }

    private void adjustProgressBarViewIfNeeded() {
        UdfpsEnrollView udfpsEnrollView = this.mUdfpsEnrollView;
        if (udfpsEnrollView != null) {
            final UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = (UdfpsEnrollProgressBarDrawable) udfpsEnrollView.getFingerprintProgressDrawable();
            UdfpsEnrollProgressBarDrawable.OnDrawFinishedListener onDrawFinishedListener = new UdfpsEnrollProgressBarDrawable.OnDrawFinishedListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView.5
                @Override // com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollProgressBarDrawable.OnDrawFinishedListener
                public void onDrawFinished() {
                    udfpsEnrollProgressBarDrawable.deleteOnDrawFinishedListener();
                    UdfpsEnrollEnrollingView.this.mUdfpsEnrollView.setVisibility(4);
                    UdfpsEnrollEnrollingView.this.adjustProgressBarView();
                }
            };
            this.mOnDrawFinishedListener = onDrawFinishedListener;
            udfpsEnrollProgressBarDrawable.addOnDrawFinishedListener(onDrawFinishedListener);
        }
    }

    public void relayoutForFingerprintSensor() {
        updateLandscapeInfo();
        if (this.mIsLandscape) {
            if (this.mShouldUseReverseLandscape) {
                relayoutForReversedLandscape();
            } else {
                relayoutForLandscape();
            }
        }
    }

    private void initUdfpsEnrollView(FingerprintSensor fingerprintSensor, UdfpsEnrollHelper udfpsEnrollHelper) {
        updateSensorOverlayParams(fingerprintSensor, udfpsEnrollHelper);
    }

    private void updateSensorOverlayParams(FingerprintSensor fingerprintSensor, UdfpsEnrollHelper udfpsEnrollHelper) {
        DisplayInfo displayInfo = new DisplayInfo();
        getContext().getDisplay().getDisplayInfo(displayInfo);
        float scaleFactor = this.mUdfpsUtils.getScaleFactor(displayInfo);
        Rect rectCopyOrNull = Rect.copyOrNull(fingerprintSensor.getSensorBounds());
        rectCopyOrNull.scale(scaleFactor);
        this.mUdfpsEnrollView.setOverlayParams(new UdfpsOverlayParams(rectCopyOrNull, new Rect(0, displayInfo.getNaturalHeight() / 2, displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight()), displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight(), scaleFactor, displayInfo.rotation, FingerprintSensorTypeKt.toInt(fingerprintSensor.getSensorType())));
        this.mUdfpsEnrollView.setEnrollHelper(udfpsEnrollHelper);
        this.mUdfpsEnrollView.setClipToPadding(false);
        this.mUdfpsEnrollView.setClipChildren(false);
        this.mUdfpsEnrollView.setVisibility(0);
    }

    private void adjustPortraitPaddings() {
        FrameLayout frameLayout = (FrameLayout) findViewById(com.google.android.settings.biometrics.udfps.R$id.layout_container);
        int dimension = (int) getResources().getDimension(R.dimen.udfps_lottie_padding_top);
        frameLayout.setPadding(0, dimension, 0, 0);
        int i = -dimension;
        ((ImageView) this.mUdfpsEnrollView.findViewById(com.google.android.settings.biometrics.udfps.R$id.udfps_enroll_animation_fp_progress_view)).setPadding(0, i, 0, dimension);
        ((ImageView) this.mUdfpsEnrollView.findViewById(com.google.android.settings.biometrics.udfps.R$id.udfps_enroll_animation_fp_view)).setPadding(0, i, 0, dimension);
    }

    private void setOnHoverListener() {
        int i;
        if (this.mAccessibilityManager.isEnabled()) {
            if (this.mIsLandscape) {
                i = R$id.sud_landscape_content_area;
            } else {
                i = R$id.sud_layout_content;
            }
            final ViewGroup viewGroup = (ViewGroup) findManagedViewById(i);
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            viewGroup.setImportantForAccessibility(2);
            viewGroup.setAccessibilityLiveRegion(2);
            viewGroup.setOnHoverListener(new View.OnHoverListener() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    return this.f$0.lambda$setOnHoverListener$4(viewGroup, view, motionEvent);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setOnHoverListener$4(ViewGroup viewGroup, View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 9 || motionEvent.getAction() == 7) {
            onHoverEnterOrMove(viewGroup, motionEvent);
            return false;
        }
        if (motionEvent.getAction() != 10) {
            return false;
        }
        onHoverExit(viewGroup);
        return false;
    }

    private void onHoverEnterOrMove(View view, MotionEvent motionEvent) {
        String strOnTouchOutsideOfSensorArea;
        Point touchInNativeCoordinates = this.mUdfpsUtils.getTouchInNativeCoordinates(motionEvent.getPointerId(0), motionEvent, this.mUdfpsEnrollView.getOverlayParams());
        if (this.mUdfpsUtils.isWithinSensorArea(motionEvent.getPointerId(0), motionEvent, this.mUdfpsEnrollView.getOverlayParams()) || (strOnTouchOutsideOfSensorArea = this.mUdfpsUtils.onTouchOutsideOfSensorArea(this.mAccessibilityManager.isTouchExplorationEnabled(), getContext(), touchInNativeCoordinates.x, touchInNativeCoordinates.y, this.mUdfpsEnrollView.getOverlayParams())) == null) {
            return;
        }
        view.setImportantForAccessibility(2);
        view.setContentDescription(null);
        view.setImportantForAccessibility(1);
        view.setContentDescription(strOnTouchOutsideOfSensorArea);
    }

    private void onHoverExit(final View view) {
        view.postDelayed(new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                view.setImportantForAccessibility(2);
            }
        }, 600L);
    }

    private void relayoutForReversedLandscape() {
        ViewGroup viewGroup = (ViewGroup) this.mHeaderView.getParent();
        View childAt = viewGroup.getChildAt(0);
        View view = this.mHeaderView;
        if (childAt == view) {
            viewGroup.removeView(view);
            viewGroup.addView(this.mHeaderView);
        }
        BottomScrollView bottomScrollView = (BottomScrollView) this.mHeaderView.findViewById(R$id.sud_header_scroll_view);
        this.mDefaultHeaderScrollViewIndicator = bottomScrollView.getScrollIndicators();
        bottomScrollView.setScrollIndicators(0);
    }

    private void relayoutForLandscape() {
        ViewGroup viewGroup = (ViewGroup) this.mHeaderView.getParent();
        View childAt = viewGroup.getChildAt(viewGroup.getChildCount() - 1);
        View view = this.mHeaderView;
        if (childAt == view) {
            viewGroup.removeView(view);
            viewGroup.addView(this.mHeaderView, 0);
        }
        ((BottomScrollView) this.mHeaderView.findViewById(R$id.sud_header_scroll_view)).setScrollIndicators(this.mDefaultHeaderScrollViewIndicator);
    }

    private void showSubTitle() {
        this.mHeaderView.findViewById(R$id.sud_layout_subtitle).setVisibility(0);
    }

    private void showPortraitSubTitle() {
        if (this.mAccessibilityManager.isEnabled() || !this.mShouldShowLottie) {
            findViewById(R$id.sud_layout_subtitle).setVisibility(0);
        }
    }
}

package com.google.android.settings.biometrics.face;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes4.dex */
public class SquareFrameLayout extends FrameLayout {
    private int mOuterRegionChild;
    private int mPaddingDp;

    public SquareFrameLayout(Context context) {
        super(context);
    }

    public SquareFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SquareFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SquareFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setOuterRegion(int i, int i2) {
        this.mOuterRegionChild = i;
        this.mPaddingDp = i2;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        setMeasuredDimension(size, size);
        int iDpToPx = (int) Utils.dpToPx(getContext(), this.mPaddingDp);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
            View childAt = getChildAt(i3);
            childAt.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            if (childAt.getId() != this.mOuterRegionChild) {
                childAt.setPadding(iDpToPx, iDpToPx, iDpToPx, iDpToPx);
            }
        }
    }
}

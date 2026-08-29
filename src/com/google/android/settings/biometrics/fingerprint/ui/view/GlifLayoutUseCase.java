package com.google.android.settings.biometrics.fingerprint.ui.view;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.TextView;
import com.google.android.setupdesign.GlifLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: GlifLayoutUseCase.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class GlifLayoutUseCase {
    private static final Companion Companion = new Companion(null);
    private final GlifLayout glifLayout;

    public GlifLayoutUseCase(GlifLayout glifLayout) {
        glifLayout.getClass();
        this.glifLayout = glifLayout;
    }

    public final void setHeaderText(Activity activity, int i) {
        activity.getClass();
        CharSequence text = activity.getText(i);
        text.getClass();
        setHeaderText(activity, text);
    }

    public final void setHeaderText(Activity activity, CharSequence charSequence) {
        activity.getClass();
        charSequence.getClass();
        TextView headerTextView = this.glifLayout.getHeaderTextView();
        CharSequence text = headerTextView.getText();
        headerTextView.setHyphenationFrequency(0);
        if (text != charSequence) {
            if (!TextUtils.isEmpty(text)) {
                headerTextView.setAccessibilityLiveRegion(1);
            }
            this.glifLayout.setHeaderText(charSequence);
            this.glifLayout.getHeaderTextView().setContentDescription(charSequence);
            activity.setTitle(charSequence);
        }
    }

    public final void setDescriptionText(CharSequence charSequence) {
        if (TextUtils.equals(this.glifLayout.getDescriptionText(), charSequence)) {
            return;
        }
        this.glifLayout.setDescriptionText(charSequence);
    }

    /* JADX INFO: compiled from: GlifLayoutUseCase.kt */
    final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

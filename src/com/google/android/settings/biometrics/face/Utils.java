package com.google.android.settings.biometrics.face;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Utils {
    public static float dpToPx(Context context, int i) {
        return i * (context.getResources().getDisplayMetrics().densityDpi / 160.0f);
    }
}

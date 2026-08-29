package com.google.android.settings.biometrics.face;

import android.R;
import android.content.Context;
import com.android.internal.util.FrameworkStatsLog;

/* JADX INFO: loaded from: classes4.dex */
public abstract class FaceUtils {
    public static final int[] CENTER_BUCKETS = {1108, 1112, 1113, 1114, 1118};

    public static void writeVendorLog(int i, int i2) {
        FrameworkStatsLog.write(87, 4, i, false, 1, 0, 22, i2, false, -1, 0, 0, false, false, 0, 0, 0, -1, 0);
    }

    public static boolean isOneOfCenterBuckets(int i) {
        int i2 = 0;
        while (true) {
            int[] iArr = CENTER_BUCKETS;
            if (i2 >= iArr.length) {
                return false;
            }
            if (i == iArr[i2]) {
                return true;
            }
            i2++;
        }
    }

    public static boolean isFoldable(Context context) {
        return context != null && context.getResources().getIntArray(R.array.config_locationExtraPackageNames).length > 0;
    }
}

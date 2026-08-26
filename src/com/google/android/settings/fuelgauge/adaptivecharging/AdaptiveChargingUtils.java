/*
 * Copyright (C) 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.settings.fuelgauge.adaptivecharging;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.VisibleForTesting;

import com.android.settings.overlay.FeatureFactory;
import com.google.android.systemui.googlebattery.AdaptiveChargingManager;

public abstract class AdaptiveChargingUtils {
    private static final String TAG = "AdaptiveChargingUtils";

    public static boolean isAvailable(AdaptiveChargingManager manager) {
        return manager != null && isSystemUser() && manager.isAvailable();
    }

    public static Bundle getIsAvailableBundle(AdaptiveChargingManager manager) {
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("extra_is_available", isAvailable(manager));
        return bundle;
    }

    public static boolean isSystemUser() {
        return UserHandle.myUserId() == 0;
    }

    public static boolean isChecked(AdaptiveChargingManager manager) {
        return manager != null && manager.isEnabled();
    }

    public static Bundle getIsCheckedBundle(AdaptiveChargingManager manager) {
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("extra_is_checked", isChecked(manager));
        return bundle;
    }

    public static void setChecked(
            Context context, AdaptiveChargingManager manager, Bundle bundle) {
        if (bundle == null) {
            Log.w(TAG, "Bundle is null!");
        } else {
            setChecked(
                    context,
                    manager,
                    bundle.getBoolean("extra_previous_is_checked"),
                    bundle.getBoolean("extra_is_checked"));
        }
    }

    @VisibleForTesting
    static void setChecked(
            Context context, AdaptiveChargingManager manager,
            boolean previousChecked, boolean checked) {
        if (manager == null) {
            Log.w(TAG, "AdaptiveChargingManager is null!");
            return;
        }
        manager.setEnabled(checked);
        if (checked) {
            manager.setDefaultChargingPolicy();
            setChargingOptimizationMode(context, 0);
        }
        if (!checked) {
            manager.setAdaptiveChargingDeadline(-1);
        }
        if (previousChecked != checked) {
            FeatureFactory.getFeatureFactory()
                    .getMetricsFeatureProvider()
                    .action(context, /* SettingsEnums.ACTION_ADAPTIVE_CHARGING_CHANGED */ 1781,
                            checked);
        }    }

    public static boolean isAdaptiveChargingVisible(Context context) {
        return Settings.Secure.getInt(
                context.getContentResolver(), "adaptive_charging_visible", 1) == 1;
    }

    public static boolean isPreinstalledApp(Context context, String packageName) {
        try {
            ApplicationInfo info =
                    context.getPackageManager().getApplicationInfo(packageName, 0);
            return (info.flags & ApplicationInfo.FLAG_SYSTEM) != 0
                    || (info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static void setChargingOptimizationMode(Context context, int mode) {
        Settings.Secure.putInt(context.getContentResolver(), "charge_optimization_mode", mode);
    }
}

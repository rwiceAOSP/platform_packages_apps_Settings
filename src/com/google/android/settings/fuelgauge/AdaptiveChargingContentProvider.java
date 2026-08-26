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
package com.google.android.settings.fuelgauge;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.fuelgauge.adaptivecharging.AdaptiveChargingUtils;
import com.google.android.systemui.googlebattery.AdaptiveChargingManager;

/**
 * Bridge used by Settings Intelligence to read and toggle adaptive charging.
 * Authority: {@code com.android.settings.fuelgauge.provider.adaptive_charging}.
 */
public class AdaptiveChargingContentProvider extends ContentProvider {
    static final String METHOD_GET_ADAPTIVE_CHARGING_AVAILABILITY =
            "get_adaptive_charging_availability";
    static final String METHOD_IS_ADAPTIVE_CHARGING_CHECKED = "is_adaptive_charging_checked";
    static final String METHOD_SET_ADAPTIVE_CHARGING_CHECKED = "set_adaptive_charging_checked";

    AdaptiveChargingManager mAdaptiveChargingManager;

    @Override
    public boolean onCreate() {
        mAdaptiveChargingManager = new AdaptiveChargingManager(getContext().getApplicationContext());
        return true;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (method == null || method.isEmpty()) {
            return Bundle.EMPTY;
        }
        if (!AdaptiveChargingUtils.isSystemUser()) {
            Log.w(
                    "AdaptiveChargingContentProvider",
                    "call: ignore non-system users for ".concat(method));
            return Bundle.EMPTY;
        }
        Log.d("AdaptiveChargingContentProvider", "method: ".concat(method));
        if (!isCalledFromSI()) {
            Log.w(
                    "AdaptiveChargingContentProvider",
                    "caller is invalid from " + getCallingPackageNonFinal());
            return null;
        }
        Context applicationContext = getContext().getApplicationContext();
        if (METHOD_GET_ADAPTIVE_CHARGING_AVAILABILITY.equals(method)) {
            return AdaptiveChargingUtils.getIsAvailableBundle(mAdaptiveChargingManager);
        }
        if (METHOD_IS_ADAPTIVE_CHARGING_CHECKED.equals(method)) {
            return AdaptiveChargingUtils.getIsCheckedBundle(mAdaptiveChargingManager);
        }
        if (METHOD_SET_ADAPTIVE_CHARGING_CHECKED.equals(method)) {
            AdaptiveChargingUtils.setChecked(applicationContext, mAdaptiveChargingManager, extras);
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        Log.w("AdaptiveChargingContentProvider",
                "unsupported query() from " + getCallingPackageNonFinal());
        return null;
    }

    @Override
    public String getType(Uri uri) {
        Log.w("AdaptiveChargingContentProvider",
                "unsupported getType() from " + getCallingPackageNonFinal());
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.w("AdaptiveChargingContentProvider",
                "unsupported insert() from " + getCallingPackageNonFinal());
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.w("AdaptiveChargingContentProvider",
                "unsupported delete() from " + getCallingPackageNonFinal());
        return -1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.w("AdaptiveChargingContentProvider",
                "unsupported update() from " + getCallingPackageNonFinal());
        return -1;
    }

    private boolean isCalledFromSI() {
        String callingPackage = getCallingPackageNonFinal();
        Log.d("AdaptiveChargingContentProvider", "callerPackage: " + callingPackage);
        return getContext()
                .getString(R.string.config_settingsintelligence_package_name)
                .equals(callingPackage)
                && AdaptiveChargingUtils.isPreinstalledApp(getContext(), callingPackage);
    }

    String getCallingPackageNonFinal() {
        return getCallingPackage();
    }
}

package com.google.android.settings.gestures;

import android.app.settings.SettingsEnums;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

public class DoubleTapScreenToSleepSettings extends DashboardFragment {

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.double_tap_screen_to_sleep_settings);

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.SETTINGS_GESTURE_TAP_SCREEN;
    }

    @Override
    protected String getLogTag() {
        return "DoubleTapScreenToSleepSettings";
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.double_tap_screen_to_sleep_settings;
    }
}

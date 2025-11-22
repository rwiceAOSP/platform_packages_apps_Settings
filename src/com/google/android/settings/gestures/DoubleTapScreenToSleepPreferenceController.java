package com.google.android.settings.gestures;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.settings.gestures.GesturePreferenceController;

public class DoubleTapScreenToSleepPreferenceController extends GesturePreferenceController {

    private static final String PREF_KEY_VIDEO = "gesture_double_tap_screen_video";

    @Override
    public boolean isPublicSlice() {
        return true;
    }

    public DoubleTapScreenToSleepPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), "gesture_double_tap_screen_to_sleep");
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        return Settings.Secure.putInt(
                mContext.getContentResolver(), "double_tap_to_sleep", isChecked ? 1 : 0);
    }

    @Override
    protected String getVideoPrefKey() {
        return PREF_KEY_VIDEO;
    }

    @Override
    public boolean isChecked() {
        return Settings.Secure.getInt(mContext.getContentResolver(), "double_tap_to_sleep", 0) == 1;
    }
}

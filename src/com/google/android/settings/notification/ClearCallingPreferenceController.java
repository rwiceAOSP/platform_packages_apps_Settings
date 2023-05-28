package com.google.android.settings.notification;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

public class ClearCallingPreferenceController extends BasePreferenceController {

    public ClearCallingPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        if (mContext.getResources().getBoolean(R.bool.config_clear_calling_enabled)) {
            return AVAILABLE;
        }
        return UNSUPPORTED_ON_DEVICE;
    }
}

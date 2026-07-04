package com.google.android.settings;

import com.android.settingslib.metadata.FixedArrayMap;
import com.android.settingslib.metadata.PreferenceScreenMetadataFactory;

import com.google.android.settings.update.SoftwareUpdateScreen;

public abstract class SettingsGoogleScreenCollector {

    public static FixedArrayMap<String, PreferenceScreenMetadataFactory> get() {
        return new FixedArrayMap<>(1, SettingsGoogleScreenCollector::init);
    }

    private static void init(
            FixedArrayMap.OrderedInitializer<String, PreferenceScreenMetadataFactory> initializer) {
        initializer.put(
                "software_update_settings_v2",
                (PreferenceScreenMetadataFactory) context -> new SoftwareUpdateScreen());
    }
}

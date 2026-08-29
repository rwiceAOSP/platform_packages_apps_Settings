package com.google.android.settings.biometrics.fingerprint.ui.view;

import androidx.activity.ComponentActivity;
import com.android.settings.SetupWizardUtils;
import com.google.android.setupdesign.util.ThemeHelper;

/* JADX INFO: compiled from: ThemeUseCase.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ThemeUseCase {
    private final ComponentActivity activity;

    public ThemeUseCase(ComponentActivity componentActivity) {
        componentActivity.getClass();
        this.activity = componentActivity;
    }

    public final void applyTheme() {
        boolean zShouldApplyGlifExpressiveStyle = ThemeHelper.shouldApplyGlifExpressiveStyle(this.activity);
        ComponentActivity componentActivity = this.activity;
        if (zShouldApplyGlifExpressiveStyle) {
            if (ThemeHelper.trySetSuwTheme(componentActivity)) {
                return;
            }
            ComponentActivity componentActivity2 = this.activity;
            componentActivity2.setTheme(ThemeHelper.getSuwDefaultTheme(componentActivity2));
            ThemeHelper.trySetDynamicColor(this.activity);
            return;
        }
        componentActivity.setTheme(SetupWizardUtils.getTheme(componentActivity, componentActivity.getIntent()));
        ThemeHelper.trySetDynamicColor(this.activity);
    }
}

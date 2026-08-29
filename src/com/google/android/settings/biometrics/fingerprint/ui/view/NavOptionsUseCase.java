package com.google.android.settings.biometrics.fingerprint.ui.view;

import androidx.navigation.NavOptions;
import com.google.android.settings.biometrics.R$id;
import com.google.android.setupdesign.R$anim;

/* JADX INFO: compiled from: NavOptionsUseCase.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class NavOptionsUseCase {
    public static final NavOptionsUseCase INSTANCE = new NavOptionsUseCase();

    private NavOptionsUseCase() {
    }

    public final NavOptions newNavOptions() {
        return getDefaultNavOptionsBuilder().build();
    }

    public final NavOptions newSkipEnrollNavOptions() {
        return NavOptions.Builder.setPopUpTo$default(getDefaultNavOptionsBuilder(), R$id.enroll, true, false, 4, null).build();
    }

    public final NavOptions newPopAllScreensNavOptions() {
        return NavOptions.Builder.setPopUpTo$default(getDefaultNavOptionsBuilder(), R$id.intro, true, false, 4, null).build();
    }

    public final NavOptions newBackToEnrollNavOptions() {
        return NavOptions.Builder.setPopUpTo$default(getDefaultNavOptionsBuilder(), R$id.finish, true, false, 4, null).build();
    }

    private final NavOptions.Builder getDefaultNavOptionsBuilder() {
        return new NavOptions.Builder().setEnterAnim(R$anim.shared_x_axis_activity_open_enter_dynamic_color).setExitAnim(R$anim.shared_x_axis_activity_open_exit).setPopEnterAnim(R$anim.shared_x_axis_activity_close_enter_dynamic_color).setPopExitAnim(R$anim.shared_x_axis_activity_close_exit);
    }
}

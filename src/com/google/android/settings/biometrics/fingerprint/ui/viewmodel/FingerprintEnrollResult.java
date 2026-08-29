package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: SetEnrollResultViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public enum FingerprintEnrollResult {
    GENERATE_CHALLENGE_FAILED,
    ACTIVITY_ON_PAUSE_UNEXPECTED,
    INTRO_FRAGMENT_SKIP_OR_CANCEL_BUTTON,
    INTRO_FRAGMENT_DONE_AND_FINISH_BUTTON,
    INTRO_FRAGMENT_CONTINUE_ENROLL,
    SPLIT_DIALOG_DISMISS,
    FIND_SENSOR_SKIP_BUTTON,
    FIND_SENSOR_ERROR_TIMEOUT,
    FIND_SENSOR_ERROR_FINISH,
    ENROLL_ERROR_DIALOG_OK_BUTTON_TIMEOUT,
    ENROLL_ERROR_DIALOG_OK_BUTTON_FINISH,
    FIND_SENSOR_NEXT_SCREEN,
    ENROLL_SKIP_BUTTON,
    CONFIRMATION_NEXT_BUTTON;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
}

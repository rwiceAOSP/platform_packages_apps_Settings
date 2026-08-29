package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: FingerprintEnrollmentViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public enum FingerprintEnrollmentCredentialAction {
    CREDENTIAL_VALID,
    IS_GENERATING_CHALLENGE,
    FAIL_NEED_TO_CHOOSE_LOCK,
    FAIL_NEED_TO_CONFIRM_LOCK;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
}

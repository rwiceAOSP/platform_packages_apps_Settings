package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

/* JADX INFO: compiled from: LockPatternInteractor.kt */
/* JADX INFO: loaded from: classes4.dex */
public interface LockPatternInteractor {
    byte[] getGkHat(long j, long j2);

    boolean isUnspecifiedPassword();

    void removeGkPwHandle(long j);
}

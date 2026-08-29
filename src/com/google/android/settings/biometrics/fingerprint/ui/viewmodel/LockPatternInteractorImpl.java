package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.biometrics.GatekeeperPasswordProvider;

/* JADX INFO: compiled from: LockPatternInteractor.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class LockPatternInteractorImpl implements LockPatternInteractor {
    private final GatekeeperPasswordProvider gatekeeperPasswordProvider;
    private final boolean isUnspecifiedPassword;
    private final LockPatternUtils lockPatternUtils;
    private final int userId;

    public LockPatternInteractorImpl(int i, LockPatternUtils lockPatternUtils, GatekeeperPasswordProvider gatekeeperPasswordProvider) {
        lockPatternUtils.getClass();
        gatekeeperPasswordProvider.getClass();
        this.userId = i;
        this.lockPatternUtils = lockPatternUtils;
        this.gatekeeperPasswordProvider = gatekeeperPasswordProvider;
        this.isUnspecifiedPassword = lockPatternUtils.getActivePasswordQuality(i) == 0;
    }

    @Override // com.google.android.settings.biometrics.fingerprint.ui.viewmodel.LockPatternInteractor
    public boolean isUnspecifiedPassword() {
        return this.isUnspecifiedPassword;
    }

    @Override // com.google.android.settings.biometrics.fingerprint.ui.viewmodel.LockPatternInteractor
    public byte[] getGkHat(long j, long j2) {
        byte[] bArrRequestGatekeeperHat = this.gatekeeperPasswordProvider.requestGatekeeperHat(j, j2, this.userId);
        bArrRequestGatekeeperHat.getClass();
        return bArrRequestGatekeeperHat;
    }

    @Override // com.google.android.settings.biometrics.fingerprint.ui.viewmodel.LockPatternInteractor
    public void removeGkPwHandle(long j) {
        this.gatekeeperPasswordProvider.removeGatekeeperPasswordHandle(j);
    }
}

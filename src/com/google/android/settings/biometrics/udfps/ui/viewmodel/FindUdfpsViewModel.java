package com.google.android.settings.biometrics.udfps.ui.viewmodel;

import androidx.lifecycle.ViewModel;

/* JADX INFO: compiled from: FindUdfpsViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class FindUdfpsViewModel extends ViewModel {
    private final boolean isSuw;

    public FindUdfpsViewModel(boolean z) {
        this.isSuw = z;
    }

    public final boolean isSuw() {
        return this.isSuw;
    }
}

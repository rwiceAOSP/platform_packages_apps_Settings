package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

/* JADX INFO: compiled from: IntroViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class FingerprintEnrollIntroUiState {
    private final boolean enrollable;
    private final boolean hasScrolledToBottom;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FingerprintEnrollIntroUiState)) {
            return false;
        }
        FingerprintEnrollIntroUiState fingerprintEnrollIntroUiState = (FingerprintEnrollIntroUiState) obj;
        return this.hasScrolledToBottom == fingerprintEnrollIntroUiState.hasScrolledToBottom && this.enrollable == fingerprintEnrollIntroUiState.enrollable;
    }

    public int hashCode() {
        return (Boolean.hashCode(this.hasScrolledToBottom) * 31) + Boolean.hashCode(this.enrollable);
    }

    public FingerprintEnrollIntroUiState(boolean z, boolean z2) {
        this.hasScrolledToBottom = z;
        this.enrollable = z2;
    }

    public final boolean getHasScrolledToBottom() {
        return this.hasScrolledToBottom;
    }

    public final boolean getEnrollable() {
        return this.enrollable;
    }

    public String toString() {
        return FingerprintEnrollIntroUiState.class.getSimpleName() + "@" + Integer.toHexString(hashCode()) + "{hasScrolledToBottom:" + this.hasScrolledToBottom + ", enrollable:" + this.enrollable + "}";
    }
}

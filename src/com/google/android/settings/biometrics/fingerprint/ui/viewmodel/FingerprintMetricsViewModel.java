package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingAction;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingScreen;
import com.android.settings.biometrics.metrics.OnboardingEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FingerprintMetricsViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public class FingerprintMetricsViewModel extends ViewModel {
    public static final Companion Companion = new Companion(null);
    private boolean skipNextCancelAction;

    public void appendAction(BiometricsOnboardingProto$OnboardingAction biometricsOnboardingProto$OnboardingAction) {
        biometricsOnboardingProto$OnboardingAction.getClass();
    }

    public void setScreen(BiometricsOnboardingProto$OnboardingScreen biometricsOnboardingProto$OnboardingScreen) {
        biometricsOnboardingProto$OnboardingScreen.getClass();
    }

    public OnboardingEvent sendMetricsToLogger(int i) {
        return new OnboardingEvent();
    }

    /* JADX INFO: compiled from: FingerprintMetricsViewModel.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public boolean getSkipNextCancelAction() {
        return this.skipNextCancelAction;
    }

    public void setSkipNextCancelAction(boolean z) {
        this.skipNextCancelAction = z;
    }
}

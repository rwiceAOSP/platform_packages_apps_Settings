package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import android.util.Log;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingAction;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingScreen;
import com.android.settings.biometrics.metrics.BiometricsLogger;
import com.android.settings.biometrics.metrics.OnboardingEvent;
import com.android.settings.biometrics.metrics.OnboardingScreenInfoEvent;
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintNumOfEnrolledInteractor;
import com.google.android.settings.biometrics.fingerprint.interactor.ScreenProtectorInteractor;
import com.google.android.settings.biometrics.fingerprint.interactor.Sp001AllowListInteractor;
import com.google.android.settings.biometrics.fingerprint.model.CapybaraMetricsStatus;
import com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequest;
import java.time.Clock;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: FingerprintMetricsViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class FingerprintMetricsViewModelImpl extends FingerprintMetricsViewModel {
    private final BiometricsLogger biometricsLogger;
    private final Clock clock;
    private final FingerprintNumOfEnrolledInteractor numOfEnrolledInteractor;
    private final OnboardingEvent onboardingEvent;
    private ScreenInfoBuilder screenInfoBuilder;
    private final Sp001AllowListInteractor sp001AllowListInteractor;
    private final ScreenProtectorInteractor spInteractor;
    private final long startMillis;

    public FingerprintMetricsViewModelImpl(EnrollmentRequest enrollmentRequest, int i, Clock clock, FingerprintNumOfEnrolledInteractor fingerprintNumOfEnrolledInteractor, ScreenProtectorInteractor screenProtectorInteractor, Sp001AllowListInteractor sp001AllowListInteractor, BiometricsLogger biometricsLogger) {
        enrollmentRequest.getClass();
        clock.getClass();
        fingerprintNumOfEnrolledInteractor.getClass();
        screenProtectorInteractor.getClass();
        sp001AllowListInteractor.getClass();
        this.clock = clock;
        this.numOfEnrolledInteractor = fingerprintNumOfEnrolledInteractor;
        this.spInteractor = screenProtectorInteractor;
        this.sp001AllowListInteractor = sp001AllowListInteractor;
        this.biometricsLogger = biometricsLogger;
        this.startMillis = clock.millis();
        OnboardingEvent onboardingEvent = new OnboardingEvent();
        this.onboardingEvent = onboardingEvent;
        int i2 = 1;
        onboardingEvent.setModality(1);
        if (!enrollmentRequest.isSuw()) {
            if (enrollmentRequest.isFromSafetySource()) {
                i2 = 3;
            } else {
                i2 = enrollmentRequest.isFromFrrNotification() ? 4 : 2;
            }
        }
        onboardingEvent.setFromSource(i2);
        onboardingEvent.setUserId(i);
    }

    @Override // com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel
    public OnboardingEvent sendMetricsToLogger(int i) {
        int value;
        OnboardingEvent onboardingEvent = this.onboardingEvent;
        int i2 = 1;
        if (i != -1) {
            if (i == 0) {
                i2 = 4;
            } else if (i != 1) {
                i2 = 2;
                if (i != 2) {
                    i2 = 3;
                    if (i != 3) {
                        i2 = 0;
                    }
                }
            }
        }
        onboardingEvent.setResultCode(i2);
        this.onboardingEvent.setEnrolledCount(this.numOfEnrolledInteractor.getNumOfEnrolledFingerprints());
        OnboardingEvent onboardingEvent2 = this.onboardingEvent;
        if (this.sp001AllowListInteractor.isEnabled()) {
            value = this.sp001AllowListInteractor.getCapybaraMetricsStatus(this.spInteractor.getScreenProtector()).getValue();
        } else {
            value = CapybaraMetricsStatus.NOT_SUPPORTED.getValue();
        }
        onboardingEvent2.setCapybaraStatus(value);
        this.onboardingEvent.setDuration(this.clock.millis() - this.startMillis);
        ScreenInfoBuilder screenInfoBuilder = this.screenInfoBuilder;
        if (screenInfoBuilder != null) {
            OnboardingScreenInfoEvent onboardingScreenInfoEventBuildEvent = buildEvent(screenInfoBuilder);
            String string = onboardingScreenInfoEventBuildEvent.toString();
            string.getClass();
            Log.d("FingerprintMetricsViewModel", "addScreenInfo(" + StringsKt.trim(string).toString() + ")");
            this.onboardingEvent.addScreenInfo(onboardingScreenInfoEventBuildEvent);
            this.screenInfoBuilder = null;
        }
        BiometricsLogger biometricsLogger = this.biometricsLogger;
        if (biometricsLogger != null) {
            biometricsLogger.logSettingsBiometricsOnboarding(this.onboardingEvent);
        }
        return this.onboardingEvent;
    }

    @Override // com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel
    public void setScreen(BiometricsOnboardingProto$OnboardingScreen biometricsOnboardingProto$OnboardingScreen) {
        biometricsOnboardingProto$OnboardingScreen.getClass();
        ScreenInfoBuilder screenInfoBuilder = this.screenInfoBuilder;
        if (screenInfoBuilder != null && screenInfoBuilder.getEvent().getScreen() != biometricsOnboardingProto$OnboardingScreen.getNumber()) {
            OnboardingScreenInfoEvent onboardingScreenInfoEventBuildEvent = buildEvent(screenInfoBuilder);
            String string = onboardingScreenInfoEventBuildEvent.toString();
            string.getClass();
            Log.d("FingerprintMetricsViewModel", "addScreenInfo(" + StringsKt.trim(string).toString() + "), newScreen:" + biometricsOnboardingProto$OnboardingScreen.getNumber());
            this.onboardingEvent.addScreenInfo(onboardingScreenInfoEventBuildEvent);
            this.screenInfoBuilder = null;
        }
        if (this.screenInfoBuilder == null) {
            this.screenInfoBuilder = new ScreenInfoBuilder(this.clock.millis(), new OnboardingScreenInfoEvent(biometricsOnboardingProto$OnboardingScreen.getNumber(), 0L, new int[0]));
        }
    }

    @Override // com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel
    public void appendAction(BiometricsOnboardingProto$OnboardingAction biometricsOnboardingProto$OnboardingAction) {
        biometricsOnboardingProto$OnboardingAction.getClass();
        if (getSkipNextCancelAction() && biometricsOnboardingProto$OnboardingAction == BiometricsOnboardingProto$OnboardingAction.ACTION_CANCEL) {
            setSkipNextCancelAction(false);
            return;
        }
        ScreenInfoBuilder screenInfoBuilder = this.screenInfoBuilder;
        if (screenInfoBuilder == null) {
            Log.d("FingerprintMetricsViewModel", "Null builder for " + biometricsOnboardingProto$OnboardingAction);
            return;
        }
        this.screenInfoBuilder = newInfoWithAppendedAction(screenInfoBuilder, biometricsOnboardingProto$OnboardingAction);
    }

    private final OnboardingScreenInfoEvent buildEvent(ScreenInfoBuilder screenInfoBuilder) {
        return new OnboardingScreenInfoEvent(screenInfoBuilder.getEvent().getScreen(), this.clock.millis() - screenInfoBuilder.getStartMillis(), screenInfoBuilder.getEvent().getActions());
    }

    private final ScreenInfoBuilder newInfoWithAppendedAction(ScreenInfoBuilder screenInfoBuilder, BiometricsOnboardingProto$OnboardingAction biometricsOnboardingProto$OnboardingAction) {
        long startMillis = screenInfoBuilder.getStartMillis();
        int screen = screenInfoBuilder.getEvent().getScreen();
        int[] actions = screenInfoBuilder.getEvent().getActions();
        actions.getClass();
        return new ScreenInfoBuilder(startMillis, new OnboardingScreenInfoEvent(screen, 0L, ArraysKt.plus(actions, biometricsOnboardingProto$OnboardingAction.getNumber())));
    }

    /* JADX INFO: compiled from: FingerprintMetricsViewModel.kt */
    final class ScreenInfoBuilder {
        private final OnboardingScreenInfoEvent event;
        private final long startMillis;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ScreenInfoBuilder)) {
                return false;
            }
            ScreenInfoBuilder screenInfoBuilder = (ScreenInfoBuilder) obj;
            return this.startMillis == screenInfoBuilder.startMillis && Intrinsics.areEqual(this.event, screenInfoBuilder.event);
        }

        public int hashCode() {
            return (Long.hashCode(this.startMillis) * 31) + this.event.hashCode();
        }

        public String toString() {
            return "ScreenInfoBuilder(startMillis=" + this.startMillis + ", event=" + this.event + ")";
        }

        public ScreenInfoBuilder(long j, OnboardingScreenInfoEvent onboardingScreenInfoEvent) {
            onboardingScreenInfoEvent.getClass();
            this.startMillis = j;
            this.event = onboardingScreenInfoEvent;
        }

        public final long getStartMillis() {
            return this.startMillis;
        }

        public final OnboardingScreenInfoEvent getEvent() {
            return this.event;
        }
    }
}

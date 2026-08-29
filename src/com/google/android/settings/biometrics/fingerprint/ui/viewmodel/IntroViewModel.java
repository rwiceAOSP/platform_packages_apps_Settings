package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.android.settingslib.RestrictedLockUtils;
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintMaxTemplatesInteractor;
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintNumOfEnrolledInteractor;
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintSensorTypeInteractor;
import com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequest;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: IntroViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class IntroViewModel extends ViewModel {
    private final MutableStateFlow hasScrolledToBottomFlow;
    private final boolean isFingerprintUnlockDisabledByAdmin;
    private final boolean isParentalConsentRequired;
    private final FingerprintMaxTemplatesInteractor maxTemplatesInteractor;
    private final FingerprintNumOfEnrolledInteractor numOfEnrolledInteractor;
    private final EnrollmentRequest request;
    private final FingerprintSensorTypeInteractor sensorTypeInteractor;
    private final Flow uiState;

    public final EnrollmentRequest getRequest() {
        return this.request;
    }

    public IntroViewModel(RestrictedLockUtils.EnforcedAdmin enforcedAdmin, RestrictedLockUtils.EnforcedAdmin enforcedAdmin2, FingerprintMaxTemplatesInteractor fingerprintMaxTemplatesInteractor, FingerprintSensorTypeInteractor fingerprintSensorTypeInteractor, FingerprintNumOfEnrolledInteractor fingerprintNumOfEnrolledInteractor, EnrollmentRequest enrollmentRequest) {
        fingerprintMaxTemplatesInteractor.getClass();
        fingerprintSensorTypeInteractor.getClass();
        fingerprintNumOfEnrolledInteractor.getClass();
        enrollmentRequest.getClass();
        this.maxTemplatesInteractor = fingerprintMaxTemplatesInteractor;
        this.sensorTypeInteractor = fingerprintSensorTypeInteractor;
        this.numOfEnrolledInteractor = fingerprintNumOfEnrolledInteractor;
        this.request = enrollmentRequest;
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.hasScrolledToBottomFlow = MutableStateFlow;
        this.uiState = FlowKt.combine(MutableStateFlow, getEnrollableFlow(), new IntroViewModel$uiState$1(null));
        this.isParentalConsentRequired = enforcedAdmin != null;
        this.isFingerprintUnlockDisabledByAdmin = enforcedAdmin2 != null;
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel$getEnrollableFlow$1, reason: invalid class name */
    /* JADX INFO: compiled from: IntroViewModel.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int I$0;
        int I$1;
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = IntroViewModel.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0075, code lost:
        
            if (r0.emit(r4, r7) == r1) goto L19;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Object r0 = r7.L$0
                kotlinx.coroutines.flow.FlowCollector r0 = (kotlinx.coroutines.flow.FlowCollector) r0
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r7.label
                r3 = 2
                r4 = 1
                if (r2 == 0) goto L23
                if (r2 == r4) goto L1d
                if (r2 != r3) goto L16
                kotlin.ResultKt.throwOnFailure(r8)
                goto L78
            L16:
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                okio.Segment$$ExternalSyntheticBUOutline1.m(r7)
                r7 = 0
                return r7
            L1d:
                int r2 = r7.I$0
                kotlin.ResultKt.throwOnFailure(r8)
                goto L57
            L23:
                kotlin.ResultKt.throwOnFailure(r8)
                com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel r8 = com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel.this
                com.google.android.settings.biometrics.fingerprint.interactor.FingerprintNumOfEnrolledInteractor r8 = com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel.access$getNumOfEnrolledInteractor$p(r8)
                int r2 = r8.getNumOfEnrolledFingerprints()
                com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel r8 = com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel.this
                com.google.android.settings.biometrics.fingerprint.interactor.FingerprintMaxTemplatesInteractor r8 = com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel.access$getMaxTemplatesInteractor$p(r8)
                com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel r5 = com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel.this
                com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequest r5 = r5.getRequest()
                boolean r5 = r5.isSuw()
                com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel r6 = com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel.this
                com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequest r6 = r6.getRequest()
                boolean r6 = r6.isAfterSuwOrSuwSuggestedAction()
                r7.L$0 = r0
                r7.I$0 = r2
                r7.label = r4
                java.lang.Object r8 = r8.getMaxTemplates(r5, r6, r7)
                if (r8 != r1) goto L57
                goto L77
            L57:
                java.lang.Number r8 = (java.lang.Number) r8
                int r8 = r8.intValue()
                if (r2 >= r8) goto L60
                goto L61
            L60:
                r4 = 0
            L61:
                java.lang.Boolean r4 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
                java.lang.Object r5 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r0)
                r7.L$0 = r5
                r7.I$0 = r2
                r7.I$1 = r8
                r7.label = r3
                java.lang.Object r7 = r0.emit(r4, r7)
                if (r7 != r1) goto L78
            L77:
                return r1
            L78:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private final Flow getEnrollableFlow() {
        return FlowKt.flow(new AnonymousClass1(null));
    }

    public final Flow getUiState() {
        return this.uiState;
    }

    public final Object getSensorType(Continuation continuation) {
        return this.sensorTypeInteractor.getType(continuation);
    }

    public final Object onScrollToBottom(Continuation continuation) {
        Object objEmit = this.hasScrolledToBottomFlow.emit(Boxing.boxBoolean(true), continuation);
        return objEmit == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEmit : Unit.INSTANCE;
    }

    public final boolean isParentalConsentRequired() {
        return this.isParentalConsentRequired;
    }

    public final boolean isFingerprintUnlockDisabledByAdmin() {
        return this.isFingerprintUnlockDisabledByAdmin;
    }
}

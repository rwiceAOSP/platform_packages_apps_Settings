package com.google.android.settings.biometrics.udfps.ui.viewmodel;

import android.util.Log;
import androidx.lifecycle.ViewModelKt;
import com.android.settings.biometrics.fingerprint2.domain.interactor.AccessibilityInteractor;
import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintSensorInteractor;
import com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractor;
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.CanEnrollFingerprintsInteractor;
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.EnrollFingerprintInteractor;
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.EnrolledFingerprintsInteractor;
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.UserInteractor;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerEnrollState;
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintEnrollStageThresholdInteractor;
import com.google.android.settings.biometrics.fingerprint.interactor.SafetySourceUpdater;
import com.google.android.settings.biometrics.fingerprint.ui.model.CredentialModel;
import com.google.android.settings.biometrics.udfps.ui.model.EnrollStage;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: EnrollUdfpsViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class EnrollUdfpsViewModelImpl extends EnrollUdfpsViewModel {
    public static final Companion Companion = new Companion(null);
    private final MutableSharedFlow _acquiredFlow;
    private final MutableSharedFlow _errorFlow;
    private final MutableSharedFlow _helpFlow;
    private final MutableSharedFlow _pointerDownFlow;
    private final MutableSharedFlow _pointerUpFlow;
    private final MutableStateFlow _progressFlow;
    private final AccessibilityInteractor accessibilityInteractor;
    private final SharedFlow acquiredFlow;
    private final CanEnrollFingerprintsInteractor canEnrollFingerprintsInteractor;
    private final CredentialModel credentialModel;
    private final EnrollFingerprintInteractor enroll2Interactor;
    private final int enrollReason;
    private final FingerprintEnrollStageThresholdInteractor enrollStateThresholdInteractor;
    private final EnrolledFingerprintsInteractor enrolledFingerprintsInteractor;
    private Job enrollingJob;
    private final SharedFlow errorFlow;
    private final SharedFlow helpFlow;
    private final boolean isFastEnroll;
    private final StateFlow isStageHalfCompletedFlow;
    private final boolean isSuw;
    private final SharedFlow pointerDownFlow;
    private final SharedFlow pointerUpFlow;
    private final StateFlow progressFlow;
    private final Flow rotation;
    private final SafetySourceUpdater safetySourceUpdater;
    private final FingerprintSensorInteractor sensorInteractor;
    private final StateFlow stageFlow;

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModelImpl$isEnrollable$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EnrollUdfpsViewModel.kt */
    final class C05761 extends ContinuationImpl {
        int I$0;
        int label;
        /* synthetic */ Object result;

        C05761(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return EnrollUdfpsViewModelImpl.this.isEnrollable(this);
        }
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public boolean isSuw() {
        return this.isSuw;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public boolean isFastEnroll() {
        return this.isFastEnroll;
    }

    public EnrollUdfpsViewModelImpl(SafetySourceUpdater safetySourceUpdater, boolean z, boolean z2, int i, CredentialModel credentialModel, UserInteractor userInteractor, FingerprintSensorInteractor fingerprintSensorInteractor, FingerprintEnrollStageThresholdInteractor fingerprintEnrollStageThresholdInteractor, OrientationInteractor orientationInteractor, AccessibilityInteractor accessibilityInteractor, EnrollFingerprintInteractor enrollFingerprintInteractor, EnrolledFingerprintsInteractor enrolledFingerprintsInteractor, CanEnrollFingerprintsInteractor canEnrollFingerprintsInteractor) {
        safetySourceUpdater.getClass();
        credentialModel.getClass();
        userInteractor.getClass();
        fingerprintSensorInteractor.getClass();
        fingerprintEnrollStageThresholdInteractor.getClass();
        orientationInteractor.getClass();
        accessibilityInteractor.getClass();
        enrollFingerprintInteractor.getClass();
        enrolledFingerprintsInteractor.getClass();
        canEnrollFingerprintsInteractor.getClass();
        this.safetySourceUpdater = safetySourceUpdater;
        this.isSuw = z;
        this.isFastEnroll = z2;
        this.enrollReason = i;
        this.credentialModel = credentialModel;
        this.sensorInteractor = fingerprintSensorInteractor;
        this.enrollStateThresholdInteractor = fingerprintEnrollStageThresholdInteractor;
        this.accessibilityInteractor = accessibilityInteractor;
        this.enroll2Interactor = enrollFingerprintInteractor;
        this.enrolledFingerprintsInteractor = enrolledFingerprintsInteractor;
        this.canEnrollFingerprintsInteractor = canEnrollFingerprintsInteractor;
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._progressFlow = MutableStateFlow;
        this.progressFlow = FlowKt.asStateFlow(MutableStateFlow);
        Flow flow = FlowKt.flow(new EnrollUdfpsViewModelImpl$special$$inlined$transform$1(MutableStateFlow, null, this));
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.stageFlow = FlowKt.stateIn(flow, viewModelScope, companion.getEagerly(), EnrollStage.UNKNOWN);
        this.isStageHalfCompletedFlow = FlowKt.stateIn(FlowKt.flow(new EnrollUdfpsViewModelImpl$special$$inlined$transform$2(MutableStateFlow, null, this)), ViewModelKt.getViewModelScope(this), companion.getEagerly(), Boolean.FALSE);
        MutableSharedFlow mutableSharedFlowMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6, null);
        this._helpFlow = mutableSharedFlowMutableSharedFlow$default;
        this.helpFlow = FlowKt.asSharedFlow(mutableSharedFlowMutableSharedFlow$default);
        MutableSharedFlow mutableSharedFlowMutableSharedFlow$default2 = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6, null);
        this._errorFlow = mutableSharedFlowMutableSharedFlow$default2;
        this.errorFlow = FlowKt.asSharedFlow(mutableSharedFlowMutableSharedFlow$default2);
        MutableSharedFlow mutableSharedFlowMutableSharedFlow$default3 = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6, null);
        this._acquiredFlow = mutableSharedFlowMutableSharedFlow$default3;
        this.acquiredFlow = FlowKt.asSharedFlow(mutableSharedFlowMutableSharedFlow$default3);
        MutableSharedFlow mutableSharedFlowMutableSharedFlow$default4 = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6, null);
        this._pointerDownFlow = mutableSharedFlowMutableSharedFlow$default4;
        this.pointerDownFlow = FlowKt.asSharedFlow(mutableSharedFlowMutableSharedFlow$default4);
        MutableSharedFlow mutableSharedFlowMutableSharedFlow$default5 = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6, null);
        this._pointerUpFlow = mutableSharedFlowMutableSharedFlow$default5;
        this.pointerUpFlow = FlowKt.asSharedFlow(mutableSharedFlowMutableSharedFlow$default5);
        userInteractor.updateUser(credentialModel.getUserId());
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
        this.rotation = FlowKt.distinctUntilChanged(orientationInteractor.getRotation());
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public StateFlow getProgressFlow() {
        return this.progressFlow;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public StateFlow getStageFlow() {
        return this.stageFlow;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public StateFlow isStageHalfCompletedFlow() {
        return this.isStageHalfCompletedFlow;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public SharedFlow getHelpFlow() {
        return this.helpFlow;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public SharedFlow getErrorFlow() {
        return this.errorFlow;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public SharedFlow getAcquiredFlow() {
        return this.acquiredFlow;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public SharedFlow getPointerDownFlow() {
        return this.pointerDownFlow;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public SharedFlow getPointerUpFlow() {
        return this.pointerUpFlow;
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModelImpl$1, reason: invalid class name */
    /* JADX INFO: compiled from: EnrollUdfpsViewModel.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EnrollUdfpsViewModelImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableSharedFlow mutableSharedFlow = EnrollUdfpsViewModelImpl.this._helpFlow;
                this.label = 1;
                if (mutableSharedFlow.emit(null, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public boolean startEnroll() {
        byte[] token = this.credentialModel.getToken();
        if (token == null) {
            Log.e("EnrollUdfpsViewModel", "Null hardware auth token for enroll");
            return false;
        }
        Job job = this.enrollingJob;
        if (job != null) {
            job.cancel(new CancellationException("RestartEnroll"));
        }
        this.enrollingJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C05771(token, null), 3, null);
        return true;
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModelImpl$startEnroll$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EnrollUdfpsViewModel.kt */
    final class C05771 extends SuspendLambda implements Function2 {
        final /* synthetic */ byte[] $token;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C05771(byte[] bArr, Continuation continuation) {
            super(2, continuation);
            this.$token = bArr;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EnrollUdfpsViewModelImpl.this.new C05771(this.$token, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C05771) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:19:0x005b  */
        /* JADX WARN: Code duplicated, block: B:22:0x006b  */
        /* JADX WARN: Code duplicated, block: B:25:0x007b  */
        /* JADX WARN: Code duplicated, block: B:28:0x008b  */
        /* JADX WARN: Code duplicated, block: B:31:0x00b5 A[PHI: r6
          0x00b5: PHI (r6v20 java.lang.Object) = (r6v19 java.lang.Object), (r6v0 java.lang.Object) binds: [B:29:0x00b2, B:7:0x0016] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x00c5, code lost:
        
            if (((kotlinx.coroutines.flow.Flow) r6).collect(r1, r5) == r0) goto L33;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                Method dump skipped, instruction units count: 224
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModelImpl.C05771.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModelImpl$startEnroll$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: EnrollUdfpsViewModel.kt */
        final class C01811 implements FlowCollector {
            final /* synthetic */ EnrollUdfpsViewModelImpl this$0;

            C01811(EnrollUdfpsViewModelImpl enrollUdfpsViewModelImpl) {
                this.this$0 = enrollUdfpsViewModelImpl;
            }

            /* JADX WARN: Code duplicated, block: B:7:0x0013  */
            /* JADX WARN: Code restructure failed: missing block: B:25:0x00bb, code lost:
            
                if (r9.emit(r8, r0) == r1) goto L62;
             */
            /* JADX WARN: Code restructure failed: missing block: B:33:0x00e9, code lost:
            
                if (r7.emit(r8, r0) == r1) goto L62;
             */
            /* JADX WARN: Code restructure failed: missing block: B:40:0x0107, code lost:
            
                if (r7.emit(r8, r0) == r1) goto L62;
             */
            /* JADX WARN: Code restructure failed: missing block: B:47:0x0124, code lost:
            
                if (r7.emit(r8, r0) == r1) goto L62;
             */
            /* JADX WARN: Code restructure failed: missing block: B:54:0x0141, code lost:
            
                if (r7.emit(r8, r0) == r1) goto L62;
             */
            /* JADX WARN: Code restructure failed: missing block: B:61:0x015e, code lost:
            
                if (r7.emit(r8, r0) == r1) goto L62;
             */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Object emit(com.android.settings.biometrics.fingerprint2.lib.model.FingerEnrollState r8, kotlin.coroutines.Continuation r9) {
                /*
                    Method dump skipped, instruction units count: 400
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModelImpl.C05771.C01811.emit(com.android.settings.biometrics.fingerprint2.lib.model.FingerEnrollState, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getThresholdSteps(FingerEnrollState.EnrollProgress enrollProgress, EnrollStage enrollStage) {
        return MathKt.roundToInt(enrollProgress.getTotalStepsRequired() * this.enrollStateThresholdInteractor.getThreshold(enrollStage.getValue()));
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public void cancelEnroll() {
        Job job = this.enrollingJob;
        if (job != null) {
            job.cancel(new CancellationException("CancelEnroll"));
        }
        this.enrollingJob = null;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public boolean isEnrolling() {
        return this.enrollingJob != null;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public Object getSensorProp(Continuation continuation) {
        return FlowKt.first(this.sensorInteractor.getFingerprintSensor(), continuation);
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public Flow getRotation() {
        return this.rotation;
    }

    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public boolean isAccessibilityEnabled() {
        return this.accessibilityInteractor.isEnabled();
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0072  */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Override // com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel
    public Object isEnrollable(Continuation continuation) {
        C05761 c05761;
        int i;
        if (continuation instanceof C05761) {
            c05761 = (C05761) continuation;
            int i2 = c05761.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c05761.label = i2 - Integer.MIN_VALUE;
            } else {
                c05761 = new C05761(continuation);
            }
        } else {
            c05761 = new C05761(continuation);
        }
        Object objFirstOrNull = c05761.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i3 = c05761.label;
        if (i3 != 0) {
            if (i3 == 1) {
                ResultKt.throwOnFailure(objFirstOrNull);
            } else {
                if (i3 != 2) {
                    Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                i = c05761.I$0;
                ResultKt.throwOnFailure(objFirstOrNull);
            }
            return Boxing.boxBoolean(i < ((Number) objFirstOrNull).intValue());
        }
        ResultKt.throwOnFailure(objFirstOrNull);
        Flow enrolledFingerprints = this.enrolledFingerprintsInteractor.getEnrolledFingerprints();
        c05761.label = 1;
        objFirstOrNull = FlowKt.firstOrNull(enrolledFingerprints, c05761);
        if (objFirstOrNull != coroutine_suspended) {
        }
        return coroutine_suspended;
        List list = (List) objFirstOrNull;
        int size = list != null ? list.size() : 0;
        Flow maxFingerprintsEnrollable = this.canEnrollFingerprintsInteractor.getMaxFingerprintsEnrollable();
        c05761.I$0 = size;
        c05761.label = 2;
        Object objFirst = FlowKt.first(maxFingerprintsEnrollable, c05761);
        if (objFirst != coroutine_suspended) {
            int i4 = size;
            objFirstOrNull = objFirst;
            i = i4;
            return Boxing.boxBoolean(i < ((Number) objFirstOrNull).intValue());
        }
        return coroutine_suspended;
    }

    /* JADX INFO: compiled from: EnrollUdfpsViewModel.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

package com.google.android.settings.biometrics.udfps.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.CanEnrollFingerprintsInteractor;
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.EnrolledFingerprintsInteractor;
import com.android.settings.biometrics.fingerprint2.lib.domain.interactor.UserInteractor;
import java.util.List;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: ConfirmUdfpsViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ConfirmUdfpsViewModel extends ViewModel {
    private final CanEnrollFingerprintsInteractor canEnrollFingerprintsInteractor;
    private final EnrolledFingerprintsInteractor enrolledFingerprintsInteractor;
    private final boolean isSuw;

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.viewmodel.ConfirmUdfpsViewModel$getEnrolledFingerprints$1, reason: invalid class name */
    /* JADX INFO: compiled from: ConfirmUdfpsViewModel.kt */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ConfirmUdfpsViewModel.this.getEnrolledFingerprints(this);
        }
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.viewmodel.ConfirmUdfpsViewModel$isEnrollable$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ConfirmUdfpsViewModel.kt */
    final class C05751 extends ContinuationImpl {
        int I$0;
        int label;
        /* synthetic */ Object result;

        C05751(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ConfirmUdfpsViewModel.this.isEnrollable(this);
        }
    }

    public final boolean isSuw() {
        return this.isSuw;
    }

    public ConfirmUdfpsViewModel(boolean z, int i, UserInteractor userInteractor, EnrolledFingerprintsInteractor enrolledFingerprintsInteractor, CanEnrollFingerprintsInteractor canEnrollFingerprintsInteractor) {
        userInteractor.getClass();
        enrolledFingerprintsInteractor.getClass();
        canEnrollFingerprintsInteractor.getClass();
        this.isSuw = z;
        this.enrolledFingerprintsInteractor = enrolledFingerprintsInteractor;
        this.canEnrollFingerprintsInteractor = canEnrollFingerprintsInteractor;
        userInteractor.updateUser(i);
        canEnrollFingerprintsInteractor.setShouldUseSettingsMaxFingerprints(false);
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0072  */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final Object isEnrollable(Continuation continuation) {
        C05751 c05751;
        int i;
        if (continuation instanceof C05751) {
            c05751 = (C05751) continuation;
            int i2 = c05751.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c05751.label = i2 - Integer.MIN_VALUE;
            } else {
                c05751 = new C05751(continuation);
            }
        } else {
            c05751 = new C05751(continuation);
        }
        Object objFirstOrNull = c05751.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i3 = c05751.label;
        if (i3 != 0) {
            if (i3 == 1) {
                ResultKt.throwOnFailure(objFirstOrNull);
            } else {
                if (i3 != 2) {
                    Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                i = c05751.I$0;
                ResultKt.throwOnFailure(objFirstOrNull);
            }
            return Boxing.boxBoolean(i < ((Number) objFirstOrNull).intValue());
        }
        ResultKt.throwOnFailure(objFirstOrNull);
        Flow enrolledFingerprints = this.enrolledFingerprintsInteractor.getEnrolledFingerprints();
        c05751.label = 1;
        objFirstOrNull = FlowKt.firstOrNull(enrolledFingerprints, c05751);
        if (objFirstOrNull != coroutine_suspended) {
        }
        return coroutine_suspended;
        List list = (List) objFirstOrNull;
        int size = list != null ? list.size() : 0;
        Flow maxFingerprintsEnrollable = this.canEnrollFingerprintsInteractor.getMaxFingerprintsEnrollable();
        c05751.I$0 = size;
        c05751.label = 2;
        Object objFirst = FlowKt.first(maxFingerprintsEnrollable, c05751);
        if (objFirst != coroutine_suspended) {
            int i4 = size;
            objFirstOrNull = objFirst;
            i = i4;
            return Boxing.boxBoolean(i < ((Number) objFirstOrNull).intValue());
        }
        return coroutine_suspended;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final Object getEnrolledFingerprints(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object objFirst = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFirst);
            Flow enrolledFingerprints = this.enrolledFingerprintsInteractor.getEnrolledFingerprints();
            anonymousClass1.label = 1;
            objFirst = FlowKt.first(enrolledFingerprints, anonymousClass1);
            if (objFirst == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
            ResultKt.throwOnFailure(objFirst);
        }
        List list = (List) objFirst;
        return Boxing.boxInt(list != null ? list.size() : 1);
    }
}

package com.google.android.settings.biometrics.udfps.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: Emitters.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class EnrollUdfpsViewModelImpl$special$$inlined$transform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ EnrollUdfpsViewModelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EnrollUdfpsViewModelImpl$special$$inlined$transform$1(Flow flow, Continuation continuation, EnrollUdfpsViewModelImpl enrollUdfpsViewModelImpl) {
        super(2, continuation);
        this.$this_transform = flow;
        this.this$0 = enrollUdfpsViewModelImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        EnrollUdfpsViewModelImpl$special$$inlined$transform$1 enrollUdfpsViewModelImpl$special$$inlined$transform$1 = new EnrollUdfpsViewModelImpl$special$$inlined$transform$1(this.$this_transform, continuation, this.this$0);
        enrollUdfpsViewModelImpl$special$$inlined$transform$1.L$0 = obj;
        return enrollUdfpsViewModelImpl$special$$inlined$transform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
        return ((EnrollUdfpsViewModelImpl$special$$inlined$transform$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModelImpl$special$$inlined$transform$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: Emitters.kt */
    public final class AnonymousClass1 implements FlowCollector {
        final /* synthetic */ FlowCollector $$this$flow;
        final /* synthetic */ EnrollUdfpsViewModelImpl this$0;

        /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModelImpl$special$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C01791 extends ContinuationImpl {
            int I$0;
            int I$1;
            int I$2;
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            int label;
            /* synthetic */ Object result;

            public C01791(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass1.this.emit(null, this);
            }
        }

        public AnonymousClass1(FlowCollector flowCollector, EnrollUdfpsViewModelImpl enrollUdfpsViewModelImpl) {
            this.this$0 = enrollUdfpsViewModelImpl;
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0017  */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x0097, code lost:
        
            if (r1.emit(r0, r2) == r3) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0121, code lost:
        
            if (r1.emit(r8, r2) == r3) goto L37;
         */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object emit(java.lang.Object r17, kotlin.coroutines.Continuation r18) {
            /*
                Method dump skipped, instruction units count: 295
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModelImpl$special$$inlined$transform$1.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector = (FlowCollector) this.L$0;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = this.$this_transform;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, this.this$0);
            this.L$0 = SpillingKt.nullOutSpilledVariable(flowCollector);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutine_suspended) {
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

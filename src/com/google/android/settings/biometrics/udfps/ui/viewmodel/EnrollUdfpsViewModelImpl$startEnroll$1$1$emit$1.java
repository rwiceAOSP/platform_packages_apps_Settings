package com.google.android.settings.biometrics.udfps.ui.viewmodel;

import com.android.settings.biometrics.fingerprint2.lib.model.FingerEnrollState;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* JADX INFO: compiled from: EnrollUdfpsViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
final class EnrollUdfpsViewModelImpl$startEnroll$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ EnrollUdfpsViewModelImpl.C05771.C01811 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    EnrollUdfpsViewModelImpl$startEnroll$1$1$emit$1(EnrollUdfpsViewModelImpl.C05771.C01811 c01811, Continuation continuation) {
        super(continuation);
        this.this$0 = c01811;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((FingerEnrollState) null, (Continuation) this);
    }
}

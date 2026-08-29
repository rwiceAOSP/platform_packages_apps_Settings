package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: IntroViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
final class IntroViewModel$uiState$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    IntroViewModel$uiState$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue(), (Continuation) obj3);
    }

    public final Object invoke(boolean z, boolean z2, Continuation continuation) {
        IntroViewModel$uiState$1 introViewModel$uiState$1 = new IntroViewModel$uiState$1(continuation);
        introViewModel$uiState$1.Z$0 = z;
        introViewModel$uiState$1.Z$1 = z2;
        return introViewModel$uiState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
            return null;
        }
        ResultKt.throwOnFailure(obj);
        return new FingerprintEnrollIntroUiState(z, z2);
    }
}

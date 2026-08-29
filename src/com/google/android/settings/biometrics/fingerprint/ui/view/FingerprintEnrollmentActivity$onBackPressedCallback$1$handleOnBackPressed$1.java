package com.google.android.settings.biometrics.fingerprint.ui.view;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
final class FingerprintEnrollmentActivity$onBackPressedCallback$1$handleOnBackPressed$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FingerprintEnrollmentActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FingerprintEnrollmentActivity$onBackPressedCallback$1$handleOnBackPressed$1(FingerprintEnrollmentActivity fingerprintEnrollmentActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintEnrollmentActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintEnrollmentActivity$onBackPressedCallback$1$handleOnBackPressed$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((FingerprintEnrollmentActivity$onBackPressedCallback$1$handleOnBackPressed$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            this.this$0.addOnBackPressedCallback();
            return Unit.INSTANCE;
        }
        Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
        return null;
    }
}

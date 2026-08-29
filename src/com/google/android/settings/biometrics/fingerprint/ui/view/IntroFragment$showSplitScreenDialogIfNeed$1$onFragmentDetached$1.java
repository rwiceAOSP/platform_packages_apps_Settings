package com.google.android.settings.biometrics.fingerprint.ui.view;

import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollResult;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: IntroFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
final class IntroFragment$showSplitScreenDialogIfNeed$1$onFragmentDetached$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ IntroFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    IntroFragment$showSplitScreenDialogIfNeed$1$onFragmentDetached$1(IntroFragment introFragment, Continuation continuation) {
        super(2, continuation);
        this.this$0 = introFragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new IntroFragment$showSplitScreenDialogIfNeed$1$onFragmentDetached$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((IntroFragment$showSplitScreenDialogIfNeed$1$onFragmentDetached$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SetEnrollResultViewModel setEnrollResultViewModel = this.this$0.getSetEnrollResultViewModel();
            FingerprintEnrollResult fingerprintEnrollResult = FingerprintEnrollResult.SPLIT_DIALOG_DISMISS;
            this.label = 1;
            if (setEnrollResultViewModel.emit(fingerprintEnrollResult, this) == coroutine_suspended) {
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

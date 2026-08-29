package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;

/* JADX INFO: compiled from: SetEnrollResultViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class SetEnrollResultViewModel extends ViewModel {
    private final MutableSharedFlow _resultFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7, null);

    public final SharedFlow getResultFlow() {
        return FlowKt.asSharedFlow(this._resultFlow);
    }

    public final Object emit(FingerprintEnrollResult fingerprintEnrollResult, Continuation continuation) {
        Object objEmit = this._resultFlow.emit(fingerprintEnrollResult, continuation);
        return objEmit == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEmit : Unit.INSTANCE;
    }
}

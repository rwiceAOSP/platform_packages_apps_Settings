package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.google.android.settings.biometrics.fingerprint.interactor.CheckAccessibilityStatusInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: AccessibilityViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class AccessibilityViewModel extends ViewModel {
    private final MutableStateFlow _isAnyAccessibilityServiceEnabled;
    private final CheckAccessibilityStatusInteractor checkAccessibilityStatusInteractor;
    private final StateFlow isAnyAccessibilityServiceEnabled;

    public AccessibilityViewModel(CheckAccessibilityStatusInteractor checkAccessibilityStatusInteractor) {
        checkAccessibilityStatusInteractor.getClass();
        this.checkAccessibilityStatusInteractor = checkAccessibilityStatusInteractor;
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isAnyAccessibilityServiceEnabled = MutableStateFlow;
        this.isAnyAccessibilityServiceEnabled = FlowKt.asStateFlow(MutableStateFlow);
        observeAnyAccessibilityServiceStatus();
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.viewmodel.AccessibilityViewModel$observeAnyAccessibilityServiceStatus$1, reason: invalid class name */
    /* JADX INFO: compiled from: AccessibilityViewModel.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = AccessibilityViewModel.this.new AnonymousClass1(continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
        }

        public final Object invoke(boolean z, Continuation continuation) {
            return ((AnonymousClass1) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            boolean z = this.Z$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                AccessibilityViewModel.this._isAnyAccessibilityServiceEnabled.setValue(Boxing.boxBoolean(z));
                return Unit.INSTANCE;
            }
            Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
            return null;
        }
    }

    private final void observeAnyAccessibilityServiceStatus() {
        FlowKt.launchIn(FlowKt.onEach(this.checkAccessibilityStatusInteractor.observeAnyAccessibilityServiceEnabled(), new AnonymousClass1(null)), ViewModelKt.getViewModelScope(this));
    }
}

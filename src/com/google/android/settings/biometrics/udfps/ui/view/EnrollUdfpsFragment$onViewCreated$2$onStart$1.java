package com.google.android.settings.biometrics.udfps.ui.view;

import kotlin.Function;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowCollector;

/* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
final /* synthetic */ class EnrollUdfpsFragment$onViewCreated$2$onStart$1 implements FlowCollector, FunctionAdapter {
    final /* synthetic */ EnrollUdfpsFragment $tmp0;

    EnrollUdfpsFragment$onViewCreated$2$onStart$1(EnrollUdfpsFragment enrollUdfpsFragment) {
        this.$tmp0 = enrollUdfpsFragment;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof FlowCollector) && (obj instanceof FunctionAdapter)) {
            return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new AdaptedFunctionReference(2, this.$tmp0, EnrollUdfpsFragment.class, "onRotationChanged", "onRotationChanged(I)V", 4);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    public final Object emit(int i, Continuation continuation) {
        Object objOnStart$onRotationChanged = EnrollUdfpsFragment.AnonymousClass2.onStart$onRotationChanged(this.$tmp0, i, continuation);
        return objOnStart$onRotationChanged == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objOnStart$onRotationChanged : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
        return emit(((Number) obj).intValue(), continuation);
    }
}

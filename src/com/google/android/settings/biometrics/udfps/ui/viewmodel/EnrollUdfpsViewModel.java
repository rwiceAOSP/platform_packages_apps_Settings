package com.google.android.settings.biometrics.udfps.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: EnrollUdfpsViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public abstract class EnrollUdfpsViewModel extends ViewModel {
    public abstract void cancelEnroll();

    public abstract SharedFlow getAcquiredFlow();

    public abstract SharedFlow getErrorFlow();

    public abstract SharedFlow getHelpFlow();

    public abstract SharedFlow getPointerDownFlow();

    public abstract SharedFlow getPointerUpFlow();

    public abstract StateFlow getProgressFlow();

    public abstract Flow getRotation();

    public abstract Object getSensorProp(Continuation continuation);

    public abstract StateFlow getStageFlow();

    public abstract boolean isAccessibilityEnabled();

    public abstract Object isEnrollable(Continuation continuation);

    public abstract boolean isEnrolling();

    public abstract boolean isFastEnroll();

    public abstract StateFlow isStageHalfCompletedFlow();

    public abstract boolean isSuw();

    public abstract boolean startEnroll();
}

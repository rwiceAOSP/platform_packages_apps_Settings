package com.google.android.settings.biometrics.fingerprint.ui.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.settings.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollResult;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel;
import com.google.android.setupdesign.util.ThemeHelper;
import kotlin.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: SkipFindFpsDialog.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class SkipFindFpsDialog extends DialogFragment {
    public static final Companion Companion = new Companion(null);
    private final Lazy metricsViewModel$delegate;
    private final Lazy setEnrollResultViewModel$delegate;

    public SkipFindFpsDialog() {
        final Function0 function0 = null;
        this.setEnrollResultViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(SetEnrollResultViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog$special$$inlined$activityViewModels$default$1
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog$special$$inlined$activityViewModels$default$2
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog$special$$inlined$activityViewModels$default$3
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        this.metricsViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(FingerprintMetricsViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog$special$$inlined$activityViewModels$default$4
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog$special$$inlined$activityViewModels$default$5
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog$special$$inlined$activityViewModels$default$6
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SetEnrollResultViewModel getSetEnrollResultViewModel() {
        return (SetEnrollResultViewModel) this.setEnrollResultViewModel$delegate.getValue();
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        if (ThemeHelper.shouldApplyGlifExpressiveStyle(requireContext())) {
            Object objCreateDialogBuilder = createDialogBuilder();
            objCreateDialogBuilder.getClass();
            AlertDialog alertDialogCreate = ((MaterialAlertDialogBuilder) objCreateDialogBuilder).create();
            alertDialogCreate.getClass();
            return alertDialogCreate;
        }
        Object objCreateDialogBuilder2 = createDialogBuilder();
        objCreateDialogBuilder2.getClass();
        android.app.AlertDialog alertDialogCreate2 = ((android.app.AlertDialog.Builder) objCreateDialogBuilder2).create();
        alertDialogCreate2.getClass();
        return alertDialogCreate2;
    }

    public final Object createDialogBuilder() {
        if (ThemeHelper.shouldApplyGlifExpressiveStyle(requireContext())) {
            return new MaterialAlertDialogBuilder(requireActivity()).setTitle(R.string.setup_fingerprint_enroll_skip_title).setPositiveButton(R.string.skip_anyway_button_label, new DialogInterface.OnClickListener() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog.createDialogBuilder.1

                /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog$createDialogBuilder$1$1, reason: invalid class name and collision with other inner class name */
                /* JADX INFO: compiled from: SkipFindFpsDialog.kt */
                final class C01751 extends SuspendLambda implements Function2 {
                    int label;
                    final /* synthetic */ SkipFindFpsDialog this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    C01751(SkipFindFpsDialog skipFindFpsDialog, Continuation continuation) {
                        super(2, continuation);
                        this.this$0 = skipFindFpsDialog;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C01751(this.this$0, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                        return ((C01751) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            SetEnrollResultViewModel setEnrollResultViewModel = this.this$0.getSetEnrollResultViewModel();
                            FingerprintEnrollResult fingerprintEnrollResult = FingerprintEnrollResult.FIND_SENSOR_SKIP_BUTTON;
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

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(SkipFindFpsDialog.this), null, null, new C01751(SkipFindFpsDialog.this, null), 3, null);
                }
            }).setNegativeButton(R.string.go_back_button_label, (DialogInterface.OnClickListener) null).setMessage(R.string.setup_fingerprint_enroll_skip_after_adding_lock_text);
        }
        return new android.app.AlertDialog.Builder(requireActivity()).setTitle(R.string.setup_fingerprint_enroll_skip_title).setPositiveButton(R.string.skip_anyway_button_label, new DialogInterface.OnClickListener() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog.createDialogBuilder.2

            /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog$createDialogBuilder$2$1, reason: invalid class name */
            /* JADX INFO: compiled from: SkipFindFpsDialog.kt */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ SkipFindFpsDialog this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(SkipFindFpsDialog skipFindFpsDialog, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = skipFindFpsDialog;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass1(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        SetEnrollResultViewModel setEnrollResultViewModel = this.this$0.getSetEnrollResultViewModel();
                        FingerprintEnrollResult fingerprintEnrollResult = FingerprintEnrollResult.FIND_SENSOR_SKIP_BUTTON;
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

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(SkipFindFpsDialog.this), null, null, new AnonymousClass1(SkipFindFpsDialog.this, null), 3, null);
            }
        }).setNegativeButton(R.string.go_back_button_label, (DialogInterface.OnClickListener) null).setMessage(R.string.setup_fingerprint_enroll_skip_after_adding_lock_text);
    }

    /* JADX INFO: compiled from: SkipFindFpsDialog.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void showDialog(FragmentManager fragmentManager) {
            fragmentManager.getClass();
            new SkipFindFpsDialog().show(fragmentManager, SkipFindFpsDialog.class.getName());
        }
    }
}

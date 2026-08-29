package com.google.android.settings.biometrics.udfps.ui.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.navigation.FloatingWindow;
import androidx.navigation.fragment.FragmentKt;
import com.android.settings.R;
import com.android.settings.biometrics.fingerprint.FingerprintErrorDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollResult;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel;
import com.google.android.setupdesign.util.ThemeHelper;
import kotlin.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: EnrollUdfpsErrorDialog.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class EnrollUdfpsErrorDialog extends DialogFragment implements FloatingWindow {
    public static final Companion Companion = new Companion(null);
    private boolean onDialogBtnClicked;
    private final Lazy setEnrollResultViewModel$delegate;

    public EnrollUdfpsErrorDialog() {
        final Function0 function0 = null;
        this.setEnrollResultViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(SetEnrollResultViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsErrorDialog$special$$inlined$activityViewModels$default$1
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsErrorDialog$special$$inlined$activityViewModels$default$2
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsErrorDialog$special$$inlined$activityViewModels$default$3
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
        int errorMessage;
        AlertDialog.Builder builder;
        final int i = requireArguments().getInt("fingerprint_message_id");
        boolean z = requireArguments().getBoolean("is_suw");
        int errorTitle = FingerprintErrorDialog.getErrorTitle(i);
        if (z) {
            errorMessage = FingerprintErrorDialog.getSetupErrorMessage(i);
        } else {
            errorMessage = FingerprintErrorDialog.getErrorMessage(i);
        }
        if (ThemeHelper.shouldApplyGlifExpressiveStyle(requireContext())) {
            builder = new MaterialAlertDialogBuilder(requireActivity());
        } else {
            builder = new AlertDialog.Builder(requireActivity());
        }
        AlertDialog.Builder message = builder.setTitle(errorTitle).setMessage(errorMessage);
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsErrorDialog$onCreateDialog$okClickListener$1

            /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsErrorDialog$onCreateDialog$okClickListener$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: EnrollUdfpsErrorDialog.kt */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                final /* synthetic */ int $errorId;
                Object L$0;
                int label;
                final /* synthetic */ EnrollUdfpsErrorDialog this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(int i, EnrollUdfpsErrorDialog enrollUdfpsErrorDialog, Continuation continuation) {
                    super(2, continuation);
                    this.$errorId = i;
                    this.this$0 = enrollUdfpsErrorDialog;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass1(this.$errorId, this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    FingerprintEnrollResult fingerprintEnrollResult;
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        if (this.$errorId == 3) {
                            fingerprintEnrollResult = FingerprintEnrollResult.ENROLL_ERROR_DIALOG_OK_BUTTON_TIMEOUT;
                        } else {
                            fingerprintEnrollResult = FingerprintEnrollResult.ENROLL_ERROR_DIALOG_OK_BUTTON_FINISH;
                        }
                        SetEnrollResultViewModel setEnrollResultViewModel = this.this$0.getSetEnrollResultViewModel();
                        this.L$0 = SpillingKt.nullOutSpilledVariable(fingerprintEnrollResult);
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
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.this$0.onDialogBtnClicked = true;
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), null, null, new AnonymousClass1(i, this.this$0, null), 3, null);
            }
        };
        if (i == 2) {
            message.setPositiveButton(R.string.security_settings_fingerprint_enroll_dialog_try_again, new DialogInterface.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsErrorDialog$onCreateDialog$tryAgainClickListener$1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    this.this$0.onDialogBtnClicked = true;
                    this.this$0.dismiss();
                }
            });
            message.setNegativeButton(R.string.security_settings_fingerprint_enroll_dialog_ok, onClickListener);
        } else {
            message.setPositiveButton(R.string.security_settings_fingerprint_enroll_dialog_ok, onClickListener);
        }
        AlertDialog alertDialogCreate = message.create();
        alertDialogCreate.getClass();
        alertDialogCreate.setCancelable(false);
        alertDialogCreate.setCanceledOnTouchOutside(false);
        return alertDialogCreate;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        dialogInterface.getClass();
        Log.d("EnrollUdfpsErrorDialog", "Dialog was canceled, onDialogBtnClicked:" + this.onDialogBtnClicked);
        if (!this.onDialogBtnClicked) {
            FragmentKt.findNavController(this).popBackStack();
        }
        super.onCancel(dialogInterface);
    }

    /* JADX INFO: compiled from: EnrollUdfpsErrorDialog.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final EnrollUdfpsErrorDialog newInstance(int i, boolean z) {
            Bundle bundle = new Bundle();
            bundle.putInt("fingerprint_message_id", i);
            bundle.putBoolean("is_suw", z);
            EnrollUdfpsErrorDialog enrollUdfpsErrorDialog = new EnrollUdfpsErrorDialog();
            enrollUdfpsErrorDialog.setArguments(bundle);
            return enrollUdfpsErrorDialog;
        }
    }
}

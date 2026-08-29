package com.google.android.settings.biometrics.fingerprint.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.android.settings.R;
import com.android.settings.biometrics.BiometricUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SplitScreenDialog.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class SplitScreenDialog extends DialogFragment {
    public static final Companion Companion = new Companion(null);

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(requireContext()).setTitle(R.string.biometric_settings_add_fingerprint_in_split_mode_title).setMessage(R.string.biometric_settings_add_fingerprint_in_split_mode_message).setCancelable(false).setPositiveButton(R.string.biometric_settings_add_biometrics_in_split_mode_ok, new DialogInterface.OnClickListener() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.SplitScreenDialog$onCreateDialog$dialog$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.this$0.dismiss();
            }
        }).create();
        alertDialogCreate.getClass();
        alertDialogCreate.setCancelable(false);
        alertDialogCreate.setCanceledOnTouchOutside(false);
        return alertDialogCreate;
    }

    /* JADX INFO: compiled from: SplitScreenDialog.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean shouldShowDialog(Activity activity) {
            activity.getClass();
            return BiometricUtils.isSplitScreenEnrollmentDisabled(activity);
        }

        public final void dismissExistingDialog(FragmentManager fragmentManager) {
            fragmentManager.getClass();
            SplitScreenDialog splitScreenDialog = (SplitScreenDialog) fragmentManager.findFragmentByTag(SplitScreenDialog.class.getName());
            if (splitScreenDialog != null) {
                splitScreenDialog.dismiss();
            }
        }

        public final void showDialog(FragmentManager fragmentManager) {
            fragmentManager.getClass();
            new SplitScreenDialog().show(fragmentManager, SplitScreenDialog.class.getName());
        }
    }
}

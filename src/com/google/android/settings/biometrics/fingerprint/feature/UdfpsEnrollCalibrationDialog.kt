package com.google.android.settings.biometrics.fingerprint.feature

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.android.settings.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.setupdesign.util.ThemeHelper

class UdfpsEnrollCalibrationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (ThemeHelper.shouldApplyGlifExpressiveStyle(requireContext())) {
            val alertDialog = MaterialAlertDialogBuilder(requireActivity())
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(positiveBtnTextId) { dialogInterface, _ ->
                    dialogInterface?.dismiss()
                }
                .create()
            isCancelable = false
            return alertDialog
        }
        val alertDialog = AlertDialog.Builder(requireActivity(), R.style.Theme_AlertDialog)
            .setTitle(titleId)
            .setMessage(messageId)
            .setPositiveButton(positiveBtnTextId) { dialogInterface, _ ->
                dialogInterface?.dismiss()
            }
            .create()
        isCancelable = false
        return alertDialog
    }

    companion object {
        val titleId: Int = R.string.fingerprint_udfps_pre_enroll_runner_dialog_title
        val messageId: Int = R.string.fingerprint_udfps_pre_enroll_runner_dialog_message
        val positiveBtnTextId: Int = R.string.done
    }
}
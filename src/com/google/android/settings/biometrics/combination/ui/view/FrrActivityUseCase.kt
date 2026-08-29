package com.google.android.settings.biometrics.combination.ui.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.android.settings.R
import com.android.settings.biometrics.BiometricUtils
import com.android.settings.password.ChooseLockSettingsHelper

class FrrActivityUseCase {

    fun getConfirmLockLauncher(
        userId: Int,
        activity: Activity,
        requestCode: Int
    ): ChooseLockSettingsHelper {
        return ChooseLockSettingsHelper.Builder(activity)
            .setRequestCode(requestCode)
            .setTitle(activity.getString(R.string.security_settings_fingerprint_preference_title))
            .setRequestGatekeeperPasswordHandle(true)
            .setForegroundOnly(true)
            .setReturnCredentials(true)
            .setUserId(userId)
            .build()
    }

    fun getFingerprintIntent(ok: GkResult.Ok?, context: Context): Intent {
        val intent = BiometricUtils.getFingerprintIntroIntent(context, Intent())
        if (ok != null) {
            intent.putExtra("challenge", ok.challenge)
            intent.putExtra("hw_auth_token", ok.token)
        }
        intent.putExtra("isFromFrr", true)
        return intent
    }

    fun updateExpressViews(linearLayout: LinearLayout) {
        val visibleChildren = (0 until linearLayout.childCount)
            .map { linearLayout.getChildAt(it) }
            .filter { it.visibility == android.view.View.VISIBLE }

        val context = linearLayout.context
        val count = visibleChildren.size

        visibleChildren.forEachIndexed { index, view ->
            val backgroundRes = when {
                index == 0 && count == 1 -> R.drawable.frr_entry_expressive_single_bg
                index == 0 -> R.drawable.frr_entry_expressive_top_bg
                index == count - 1 -> R.drawable.frr_entry_expressive_bottom_bg
                else -> R.drawable.frr_entry_expressive_inner_bg
            }
            view.background = ContextCompat.getDrawable(context, backgroundRes)

            val layoutParams = view.layoutParams as LinearLayout.LayoutParams
            layoutParams.bottomMargin = if (index < count - 1) {
                context.resources.getDimensionPixelSize(R.dimen.frr_divider_height_expressive)
            } else {
                0
            }
            view.layoutParams = layoutParams
        }
    }
}

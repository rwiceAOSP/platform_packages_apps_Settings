package com.google.android.settings.biometrics.fingerprint.ui.view

import android.app.Activity
import android.text.TextUtils
import android.view.View
import com.google.android.setupdesign.GlifLayout

class GlifLayoutUseCase(private val glifLayout: GlifLayout) {

    fun setHeaderText(activity: Activity, headerResId: Int) {
        setHeaderText(activity, activity.getText(headerResId))
    }

    fun setHeaderText(activity: Activity, headerText: CharSequence) {
        val headerTextView = glifLayout.getHeaderTextView()
        val text = headerTextView.text
        headerTextView.hyphenationFrequency = 0
        if (text != headerText) {
            if (!TextUtils.isEmpty(text)) {
                headerTextView.accessibilityLiveRegion = View.ACCESSIBILITY_LIVE_REGION_POLITE
            }
            glifLayout.setHeaderText(headerText)
            glifLayout.getHeaderTextView().contentDescription = headerText
            activity.setTitle(headerText)
        }
    }

    fun setDescriptionText(descriptionText: CharSequence) {
        if (TextUtils.equals(glifLayout.getDescriptionText(), descriptionText)) {
            return
        }
        glifLayout.descriptionText = descriptionText
    }
}

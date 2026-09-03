package com.google.android.settings.biometrics.fingerprint.ui.model

import com.google.android.settings.biometrics.fingerprint.model.SpProductInfo

sealed class SpUiData {

    object None : SpUiData()

    object ThirdParty : SpUiData()

    object Unset : SpUiData()

    data class FirstParty(val productInfo: SpProductInfo) : SpUiData()
}

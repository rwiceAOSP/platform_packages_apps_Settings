package com.google.android.settings.biometrics.fingerprint.interactor

import com.google.android.settings.biometrics.fingerprint.data.repository.Sp001AllowListRepository
import com.google.android.settings.biometrics.fingerprint.model.CapybaraMetricsStatus
import com.google.android.settings.biometrics.fingerprint.model.SpData
import com.google.android.settings.biometrics.fingerprint.model.SpHal
import com.google.android.settings.biometrics.fingerprint.model.SpProductInfo
import com.google.android.settings.biometrics.fingerprint.ui.model.SpUiData

interface Sp001AllowListInteractor {
    fun isEnabled(): Boolean

    fun getSpUiData(spHal: SpHal?): SpUiData

    fun getCapybaraMetricsStatus(spHal: SpHal?): CapybaraMetricsStatus
}

class Sp001AllowListInteractorImpl(private val sp001AllowListRepository: Sp001AllowListRepository) :
    Sp001AllowListInteractor {

    val spNone: SpHal by lazy { sp001AllowListRepository.spNone }

    val spThirdParty: SpHal by lazy { sp001AllowListRepository.spThirdParty }

    private val spFirstPartyList: List<SpData> by lazy { sp001AllowListRepository.allowList }

    override fun isEnabled(): Boolean {
        return spFirstPartyList.isNotEmpty()
    }

    fun getFirstPartySpProductInfo(spHal: SpHal?): SpProductInfo? {
        if (spHal == null) return null
        return spFirstPartyList.firstOrNull { it.hal == spHal }?.detail
    }

    override fun getSpUiData(spHal: SpHal?): SpUiData {
        val firstPartySpProductInfo = getFirstPartySpProductInfo(spHal)
        if (firstPartySpProductInfo != null) {
            return SpUiData.FirstParty(firstPartySpProductInfo)
        }
        return if (spHal == spNone) {
            SpUiData.None
        } else if (spHal == spThirdParty) {
            SpUiData.ThirdParty
        } else {
            SpUiData.Unset
        }
    }

    override fun getCapybaraMetricsStatus(spHal: SpHal?): CapybaraMetricsStatus {
        if (!isEnabled()) {
            return CapybaraMetricsStatus.NOT_SUPPORTED
        }
        return when (getSpUiData(spHal)) {
            is SpUiData.Unset -> CapybaraMetricsStatus.UNSET
            is SpUiData.None -> CapybaraMetricsStatus.NO_SCREEN_PROTECTOR
            else -> CapybaraMetricsStatus.HAS_SCREEN_PROTECTOR
        }
    }
}

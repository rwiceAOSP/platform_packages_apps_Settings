package com.google.android.settings.biometrics.fingerprint.data.repository

import android.content.res.Resources
import android.util.Log
import com.android.settings.R
import com.google.android.settings.biometrics.fingerprint.model.SpData
import com.google.android.settings.biometrics.fingerprint.model.SpHal
import com.google.android.settings.biometrics.fingerprint.model.SpProductInfo

interface Sp001AllowListRepository {
    val allowList: List<SpData>

    val spNone: SpHal

    val spThirdParty: SpHal
}

class Sp001AllowListRepositoryImpl private constructor(private val res: Resources) :
    Sp001AllowListRepository {

    override val allowList: List<SpData> by lazy {
        val firstPartyHals = res.getStringArray(R.array.sp_config_001_first_party_hals)
        val uiModelNames = res.getStringArray(R.array.sp_config_001_ui_model_names)

        if (firstPartyHals.isEmpty()) {
            Log.d(TAG, "No allow list found in resources!!")
            return@lazy emptyList()
        }
        if (firstPartyHals.size != uiModelNames.size) {
            Log.d(TAG, "Allow list resource sizes do not match!!")
            return@lazy emptyList()
        }

        val list = ArrayList<SpData>(firstPartyHals.size)
        for (i in firstPartyHals.indices) {
            val hal = firstPartyHals[i]
            val uiModelName = uiModelNames[i]
            list.add(SpData(SpProductInfo(uiModelName), SpHal.Companion.getInstance(hal)))
        }
        Log.d(TAG, "Allow list loaded with ${list.size} items")
        list
    }

    override val spNone: SpHal by lazy {
        SpHal.getInstance(res.getString(R.string.sp_config_001_hal_none))
    }

    override val spThirdParty: SpHal by lazy {
        SpHal.getInstance(res.getString(R.string.sp_config_001_hal_third_party))
    }

    companion object {
        private const val TAG = "Sp001AllowListRepository"

        @Volatile private var instance: Sp001AllowListRepository? = null

        @JvmStatic
        @Synchronized
        fun getInstance(resources: Resources): Sp001AllowListRepository {
            return instance ?: Sp001AllowListRepositoryImpl(resources).also { instance = it }
        }
    }
}
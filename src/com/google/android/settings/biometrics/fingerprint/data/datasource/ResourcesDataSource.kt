package com.google.android.settings.biometrics.fingerprint.data.datasource

import android.content.res.Resources
import com.android.settings.R

interface ResourcesDataSource {
    val suwMaxFingerprintsEnrollable: Int
}

class ResourcesDataSourceImpl private constructor(resources: Resources) : ResourcesDataSource {

    override val suwMaxFingerprintsEnrollable: Int =
        resources.getInteger(R.integer.suw_max_fingerprints_enrollable)

    companion object {
        @Volatile
        private lateinit var instance: ResourcesDataSource

        @Synchronized
        @JvmStatic
        fun getInstance(resources: Resources): ResourcesDataSource {
            return instance ?: ResourcesDataSourceImpl(resources).also { instance = it }
        }
    }
}
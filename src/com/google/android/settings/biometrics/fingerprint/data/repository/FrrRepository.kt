package com.google.android.settings.biometrics.fingerprint.data.repository

import android.os.SystemProperties
import android.util.Log
import com.google.android.settings.biometrics.fingerprint.model.SpHal
import com.google.hardware.biometrics.fingerprint.IFingerprintExt
import java.util.function.Supplier

interface FrrRepository {
    val screenProtector: SpHal?
}

class FrrRepositoryImpl
private constructor(private val fingerprintExtSupplier: Supplier<IFingerprintExt?>) :
    FrrRepository {

    override val screenProtector: SpHal?
        get() {
            if (fingerprintExtSupplier.get() == null) {
                Log.d(TAG, "get(), fingerprintExt is null")
            } else {
                try {
                    val str = SystemProperties.get(KEY_SP)
                    if (str.length > 0) {
                        return SpHal(str.toUInt(16))
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Got exception during get", e)
                }
            }
            return null
        }

    companion object {
        private const val TAG = "FrrRepository"
        private const val KEY_SP = "persist.fingerprint.screenprotector.config"

        @Volatile private var instance: FrrRepository? = null

        @JvmStatic
        @Synchronized
        fun getInstance(fingerprintExtSupplier: Supplier<IFingerprintExt?>): FrrRepository {
            return instance ?: FrrRepositoryImpl(fingerprintExtSupplier).also { instance = it }
        }
    }
}
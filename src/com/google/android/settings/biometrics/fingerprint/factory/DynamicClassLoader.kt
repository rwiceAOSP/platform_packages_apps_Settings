package com.google.android.settings.biometrics.fingerprint.factory

import android.content.Context
import android.util.Log
import com.android.settings.biometrics.fingerprint.feature.ChallengeGeneratedInvoker
import com.android.settings.biometrics.fingerprint.feature.FingerprintExtPreferencesProvider

object DynamicClassLoader {
    private const val TAG = "DynamicClassLoader"

    fun newFingerprintExtPreferencesProvider(
        className: String,
        context: Context,
    ): FingerprintExtPreferencesProvider? {
        return try {
            val clazz = Class.forName(className)
            clazz.getConstructor(Context::class.java).newInstance(context)
                as? FingerprintExtPreferencesProvider
        } catch (e: ClassNotFoundException) {
            Log.d(TAG, "Fail to find class $className")
            null
        } catch (e: Exception) {
            Log.e(TAG, "Fail to init class $className", e)
            null
        }
    }

    fun newChallengeGeneratedInvoker(className: String): ChallengeGeneratedInvoker? {
        return try {
            val clazz = Class.forName(className)
            clazz.getConstructor().newInstance() as? ChallengeGeneratedInvoker
        } catch (e: ClassNotFoundException) {
            Log.d(TAG, "Fail to find class $className")
            null
        } catch (e: Exception) {
            Log.e(TAG, "Fail to init class $className", e)
            null
        }
    }
}

package com.google.android.settings.biometrics.combination.data.repository

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface VibratorRepository {
    fun getVibratorStatus(): Flow<Boolean>
    fun isVibratorEnabled(): Boolean
}

class VibratorRepositoryImpl(context: Context) : VibratorRepository {

    private val contentResolver = context.contentResolver

    override fun isVibratorEnabled(): Boolean =
        Settings.System.getInt(contentResolver, "vibrate_on", 1) == 1

    override fun getVibratorStatus(): Flow<Boolean> = callbackFlow {
        fun sendCurrentValue() {
            trySend(isVibratorEnabled())
        }

        val observer =
            object : ContentObserver(Handler(Looper.getMainLooper())) {
                override fun onChange(selfChange: Boolean, uri: android.net.Uri?) {
                    sendCurrentValue()
                }
            }

        val uri = Settings.System.getUriFor("vibrate_on")
        contentResolver.registerContentObserver(uri, false, observer)
        sendCurrentValue()

        awaitClose { contentResolver.unregisterContentObserver(observer) }
    }
}

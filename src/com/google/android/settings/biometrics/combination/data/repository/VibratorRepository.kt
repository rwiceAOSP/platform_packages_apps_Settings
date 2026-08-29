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
}

class VibratorRepositoryImpl(context: Context) : VibratorRepository {

    private val contentResolver: ContentResolver = context.contentResolver

    override fun getVibratorStatus(): Flow<Boolean> = callbackFlow {
        val handler = Handler(Looper.getMainLooper())
        val observer = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                trySend(Settings.System.getInt(contentResolver, "vibrate_on", 1) == 1)
            }
        }

        val uri = Settings.System.getUriFor("vibrate_on")
        contentResolver.registerContentObserver(uri, false, observer)
        trySend(Settings.System.getInt(contentResolver, "vibrate_on", 1) == 1)

        awaitClose {
            contentResolver.unregisterContentObserver(observer)
        }
    }
}

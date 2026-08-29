package com.google.android.settings.biometrics.fingerprint.data.repository

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

interface AccessibilityRepository {
    fun observeAnyAccessibilityServiceEnabled(): Flow<Boolean>
}

class AccessibilityRepositoryImpl(private val context: Context) : AccessibilityRepository {

    private val ACCESSIBILITY_ENABLED_URI: Uri =
        Settings.Secure.getUriFor("accessibility_enabled")

    override fun observeAnyAccessibilityServiceEnabled(): Flow<Boolean> =
        callbackFlow {
                fun sendCurrentState() {
                    val enabled =
                        try {
                            Settings.Secure.getInt(context.contentResolver, "accessibility_enabled")
                        } catch (e: Settings.SettingNotFoundException) {
                            Log.e(
                                "AccessibilityRepository",
                                "Error finding setting, accessibility was not found: ${e.message}",
                            )
                            0
                        }
                    trySend(enabled == 1)
                }

                val handler = Handler(Looper.getMainLooper())
                val contentObserver = object : ContentObserver(handler) {
                    override fun onChange(selfChange: Boolean) {
                        super.onChange(selfChange)
                        sendCurrentState()
                    }

                    override fun onChange(selfChange: Boolean, uri: Uri?) {
                       super.onChange(selfChange, uri)
                        sendCurrentState()
                    }
                }

                context.contentResolver.registerContentObserver(
                    ACCESSIBILITY_ENABLED_URI,
                    false,
                    observer,
                )
                sendCurrentState()

                awaitClose { context.contentResolver.unregisterContentObserver(observer) }
            }
            .distinctUntilChanged()
}
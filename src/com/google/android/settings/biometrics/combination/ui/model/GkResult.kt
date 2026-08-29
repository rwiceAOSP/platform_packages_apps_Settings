package com.google.android.settings.biometrics.combination.ui.model

import com.android.settings.biometrics.BiometricUtils

sealed class GkResult private constructor() {

    data class Failed(
        val e: BiometricUtils.GatekeeperCredentialNotMatchException
    ) : GkResult()

    class Ok(
        val challenge: Long,
        val token: ByteArray
    ) : GkResult() {

        override fun toString(): String =
            "Ok(challenge=$challenge, token=${token.contentToString()})"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this.javaClass != other.javaClass) return false
            other as Ok
            return challenge == other.challenge && token.contentEquals(other.token)
        }

        override fun hashCode(): Int {
            return challenge.hashCode() * 31 + token.contentHashCode()
        }
    }
}

package com.google.android.settings.biometrics.fingerprint.model

class SpHal private constructor(private val hex: UInt) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is SpHal && hex == other.hex
    }

    override fun hashCode(): Int {
        return hex.hashCode()
    }

    override fun toString(): String {
        return hex.toString()
    }

    companion object {
        @JvmStatic
        fun getInstance(s: String): SpHal {
            checkNotNull(s)
            return SpHal(s.toUInt(16))
        }
    }
}

package com.google.android.settings.biometrics.face.data.repository

import android.hardware.face.FaceManager

interface FaceManagerRepository {
    val hasEnrolled: Boolean

    fun isSupport(): Boolean
}

class FaceManagerRepositoryImpl
private constructor(private val userId: Int, private val faceManager: FaceManager?) :
    FaceManagerRepository {

    override fun isSupport(): Boolean = faceManager != null

    override val hasEnrolled: Boolean
        get() = faceManager?.hasEnrolledTemplates(userId) ?: false

    companion object {
        private val instances: MutableMap<Int, FaceManagerRepository> = LinkedHashMap()

        @Synchronized
        fun getInstance(userId: Int, faceManager: FaceManager?): FaceManagerRepository {
            return instances[userId] ?: FaceManagerRepositoryImpl(userId, faceManager).also {
                instances[userId] = it
            }
        }
    }
}

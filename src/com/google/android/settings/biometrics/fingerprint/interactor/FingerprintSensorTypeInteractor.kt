package com.google.android.settings.biometrics.fingerprint.interactor

import com.google.android.settings.biometrics.fingerprint.data.repository.FingerprintsRepository
import kotlinx.coroutines.flow.first

interface FingerprintSensorTypeInteractor {
    suspend fun getType(): Int
}

class FingerprintSensorTypeInteractorImpl(
    private val fingerprintsRepository: FingerprintsRepository
) : FingerprintSensorTypeInteractor {

    override suspend fun getType(): Int {
        return fingerprintsRepository.getFingerprintSensor().first().sensorType
    }
}
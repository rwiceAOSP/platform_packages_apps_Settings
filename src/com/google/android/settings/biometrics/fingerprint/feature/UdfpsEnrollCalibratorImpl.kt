package com.google.android.settings.biometrics.fingerprint.feature

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.RemoteException
import android.os.ServiceManager
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.android.settings.biometrics.fingerprint.UdfpsEnrollCalibrator
import com.google.hardware.biometrics.fingerprint.ICalibrationCallback
import com.google.hardware.biometrics.fingerprint.IFingerprintExt
import java.util.UUID
import java.util.function.Supplier

class UdfpsEnrollCalibratorImpl(
    private val handler: Handler,
    private val fingerprintExt: IFingerprintExt,
    val uuid: UUID,
) : UdfpsEnrollCalibrator {

    enum class Result {
        NEED_CALIBRATION,
        NO_NEED_CALIBRATION,
    }

    enum class Status {
        PROCESSING,
        GOT_RESULT,
        FINISHED,
    }

    private var enableEnrollingRunnable: Runnable? = null
    private var fragmentManager: FragmentManager? = null
    private var isWaitingPage = false
    private var result: Result? = null

    private val statusLiveData = MutableLiveData(Status.PROCESSING)

    private val lifecycleObserver: DefaultLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
        }

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
        }

        override fun onStart(owner: LifecycleOwner) {
            Log.d(TAG, "Waiting page onStart")
            isWaitingPage = true
            if (statusLiveData.value == Status.GOT_RESULT) {
                onCalibrationDone()
            }
            super.onStart(owner)
        }

        override fun onStop(owner: LifecycleOwner) {
            Log.d(TAG, "Waiting page onStop")
            isWaitingPage = false
            super.onStop(owner)
        }
    }

    init {
        handler.post { preEnroll() }
        handler.postDelayed(
            {
                if (statusLiveData.value == Status.PROCESSING) {
                    Log.e(TAG, "$this timeout, enable enroll")
                    result = Result.NO_NEED_CALIBRATION
                    statusLiveData.value = Status.GOT_RESULT
                    onCalibrationDone()
                }
            },
            TIMEOUT_MILLIS,
        )
    }

    override fun getExtrasForNextIntent(): Bundle {
        val extras = Bundle()
        extras.putSerializable(KEY_UUID, uuid)
        return extras
    }

    private fun onCalibrationDone() {
        if (statusLiveData.value == Status.GOT_RESULT && isWaitingPage) {
            statusLiveData.value = Status.FINISHED
            if (result == Result.NEED_CALIBRATION) {
                val fragmentManager = fragmentManager
                if (fragmentManager != null) {
                    UdfpsEnrollCalibrationDialog().show(fragmentManager, CALIBRATION_DIALOG_TAG)
                } else {
                    Log.e(TAG, "$this.onCalibrationDone(), fragmentManager null")
                }
            }
            fragmentManager = null
            val runnable = enableEnrollingRunnable
            if (runnable != null) {
                handler.post(runnable)
            } else {
                Log.e(TAG, "$this.onCalibrationDone(), runner null")
            }
            enableEnrollingRunnable = null
        }
    }

    private fun preEnroll() {
        try {
            Log.d(TAG, "$this.preEnroll() start")
            fingerprintExt.onPreEnroll(
                object : ICalibrationCallback.Stub() {
                    override fun getInterfaceVersion(): Int = INTERFACE_VERSION

                    override fun onCalibrationStarted(sequence: Int) {
                        Log.d(TAG, "${this@UdfpsEnrollCalibratorImpl} started $sequence")
                    }

                    override fun onCalibrationError(error: Int) {
                        Log.d(TAG, "${this@UdfpsEnrollCalibratorImpl} error $error")
                        handler.post {
                            if (statusLiveData.value == Status.PROCESSING) {
                                result =
                                    if (error == ERROR_CALIBRATION_NEEDED) {
                                        Result.NEED_CALIBRATION
                                    } else {
                                        Result.NO_NEED_CALIBRATION
                                    }
                                statusLiveData.value = Status.GOT_RESULT
                                onCalibrationDone()
                            }
                        }
                    }

                    override fun onCalibrationFinished(sequence: Int) {
                        Log.d(TAG, "${this@UdfpsEnrollCalibratorImpl} finished $sequence")
                        handler.post {
                            if (statusLiveData.value == Status.PROCESSING) {
                                result = Result.NO_NEED_CALIBRATION
                                statusLiveData.value = Status.GOT_RESULT
                                onCalibrationDone()
                            }
                        }
                    }

                    override fun getInterfaceHash(): String = INTERFACE_HASH
                }
            )
        } catch (e: RemoteException) {
            Log.e(TAG, "$this.preEnroll() IFingerprintExt.Stub.asInterface exception", e)
        }
    }

    override fun toString(): String {
        return "UdfpsEnrollCalibrator@" +
            Integer.toHexString(hashCode()) +
            "{uuid:$uuid status:${statusLiveData.value}, result:$result}"
    }

    companion object {
        private const val TAG = "UdfpsEnrollCalibratorImpl"
        private const val CALIBRATION_DIALOG_TAG = "calibration-dialog"
        private const val KEY_UUID = "calibrator_uuid"
        private const val TIMEOUT_MILLIS = 5000L
        private const val ERROR_CALIBRATION_NEEDED = 4
        private const val INTERFACE_VERSION = 3
        private const val INTERFACE_HASH = "fb35cef863421d4bb91eb447fcaf7717460c5e6c"
        private const val SERVICE_NAME =
            "android.hardware.biometrics.fingerprint.IFingerprint/default"

        private val calibrators = ArrayList<UdfpsEnrollCalibratorImpl>(3)

        @JvmStatic
        fun getInstance(
            handler: Handler,
            bundle: Bundle?,
            intent: Intent?,
        ): UdfpsEnrollCalibratorImpl? {
            return try {
                val fingerprintExt = getFingerprintExtSupplier().get()
                if (fingerprintExt != null) {
                    getInstance(handler, fingerprintExt, bundle, intent)
                } else {
                    Log.e(TAG, "fingerprintExt is null")
                    null
                }
            } catch (e: RemoteException) {
                Log.e(TAG, "fail to get fingerprint ext", e)
                null
            }
        }

        @JvmStatic
        fun getInstance(
            handler: Handler,
            fingerprintExt: IFingerprintExt,
            bundle: Bundle?,
            intent: Intent?,
        ): UdfpsEnrollCalibratorImpl? {
            var uuid: UUID? = bundle?.getSerializable(KEY_UUID, UUID::class.java)
            if (uuid == null) {
                uuid = intent?.getSerializableExtra(KEY_UUID, UUID::class.java)
            }
            return getInstance(handler, fingerprintExt, uuid)
        }

        @JvmStatic
        fun getInstance(handler: Handler, uuid: UUID?): UdfpsEnrollCalibratorImpl? {
            return try {
                val fingerprintExt = getFingerprintExtSupplier().get()
                if (fingerprintExt != null) {
                    getInstance(handler, fingerprintExt, uuid)
                } else {
                    Log.e(TAG, "fingerprintExt is null")
                    null
                }
            } catch (e: RemoteException) {
                Log.e(TAG, "fail to get fingerprint ext", e)
                null
            }
        }

        @JvmStatic
        fun getInstance(
            handler: Handler,
            fingerprintExt: IFingerprintExt,
            uuid: UUID?,
        ): UdfpsEnrollCalibratorImpl? {
            if (uuid != null) {
                val existing = calibrators.firstOrNull { it.uuid == uuid }
                if (existing != null) {
                    return existing
                }
            }
            if (
                uuid == null &&
                    calibrators.size > 0 &&
                    calibrators[0].statusLiveData.value == Status.PROCESSING
            ) {
                return calibrators[0]
            }
            val effectiveUuid = uuid ?: UUID.randomUUID()
            return UdfpsEnrollCalibratorImpl(handler, fingerprintExt, effectiveUuid).also {
                calibrators.add(0, it)
                if (calibrators.size > 2) {
                    calibrators.removeAt(calibrators.lastIndex)
                }
            }
        }

        private fun getFingerprintExtSupplier(): Supplier<IFingerprintExt?> = Supplier {
            val binder = ServiceManager.waitForDeclaredService(SERVICE_NAME)
            if (binder == null) {
                Log.e(TAG, "Unable to get fingerprint service")
                return@Supplier null
            }
            try {
                IFingerprintExt.Stub.asInterface(binder.extension)
            } catch (e: RemoteException) {
                Log.e(TAG, "IFingerprintExt.Stub.asInterface RemoteException", e)
                null
            }
        }
    }
}

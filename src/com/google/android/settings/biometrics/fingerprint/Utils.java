package com.google.android.settings.biometrics.fingerprint;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.google.hardware.biometrics.sidefps.IFingerprintExt;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Utils {
    public static void resumeEnroll() {
        IFingerprintExt iFingerprintExt = (IFingerprintExt) getFingerprintExtSupplier().get();
        if (iFingerprintExt == null) {
            Log.e("BiometricUtil", "Failed to connect to the fingerprint extension");
            return;
        }
        try {
            iFingerprintExt.resumeEnroll();
        } catch (RemoteException e) {
            Log.e("BiometricUtil", "RemoteException", e);
        }
    }

    private static Supplier getFingerprintExtSupplier() {
        return new Supplier() { // from class: com.google.android.settings.biometrics.fingerprint.Utils$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return Utils.$r8$lambda$FmnHsaMqwjFzUe812ILzjWSHR_k();
            }
        };
    }

    public static /* synthetic */ IFingerprintExt $r8$lambda$FmnHsaMqwjFzUe812ILzjWSHR_k() {
        IBinder iBinderWaitForDeclaredService = ServiceManager.waitForDeclaredService("android.hardware.biometrics.fingerprint.IFingerprint/default");
        if (iBinderWaitForDeclaredService == null) {
            Log.e("BiometricUtil", "Unable to get fingerprint service");
            return null;
        }
        try {
            return IFingerprintExt.Stub.asInterface(iBinderWaitForDeclaredService.getExtension());
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.google.hardware.biometrics.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFingerprintExt extends IInterface {
    public static final String DESCRIPTOR =
            "com$google$hardware$biometrics$fingerprint$IFingerprintExt".replace('$', '.');

    void onPreEnroll(ICalibrationCallback iCalibrationCallback) throws RemoteException;

    public abstract class Stub extends Binder implements IFingerprintExt {
        public static IFingerprintExt asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface =
                    iBinder.queryLocalInterface(IFingerprintExt.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null
                    && (iInterfaceQueryLocalInterface instanceof IFingerprintExt)) {
                return (IFingerprintExt) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        private static class Proxy implements IFingerprintExt {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.google.hardware.biometrics.fingerprint.IFingerprintExt
            public void onPreEnroll(ICalibrationCallback iCalibrationCallback)
                    throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(mRemote);
                try {
                    parcelObtain.writeInterfaceToken(IFingerprintExt.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCalibrationCallback);
                    if (!this.mRemote.transact(7, parcelObtain, null, 1)) {
                        throw new RemoteException("Unimplemented");
                    }
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain.recycle();
                    throw th;
                }
            }
        }
    }
}

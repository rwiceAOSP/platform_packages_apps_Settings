package com.google.hardware.biometrics.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICalibrationCallback extends IInterface {
    String DESCRIPTOR =
            "com$google$hardware$biometrics$fingerprint$ICalibrationCallback".replace('$', '.');

    String getInterfaceHash();

    int getInterfaceVersion();

    void onCalibrationError(int error) throws RemoteException;

    void onCalibrationStarted(int sensorId) throws RemoteException;

    void onCalibrationFinished(int sensorId) throws RemoteException;

    abstract class Stub extends Binder implements ICalibrationCallback {
        public static final int TRANSACTION_onCalibrationError = 1;
        public static final int TRANSACTION_onCalibrationStarted = 2;
        public static final int TRANSACTION_onCalibrationFinished = 3;
        public static final int TRANSACTION_getInterfaceVersion = 16777215;
        public static final int TRANSACTION_getInterfaceHash = 16777214;

        @Override
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case TRANSACTION_onCalibrationError:
                    return "onCalibrationError";
                case TRANSACTION_onCalibrationStarted:
                    return "onCalibrationStarted";
                case TRANSACTION_onCalibrationFinished:
                    return "onCalibrationFinished";
                case TRANSACTION_getInterfaceVersion:
                    return "getInterfaceVersion";
                case TRANSACTION_getInterfaceHash:
                    return "getInterfaceHash";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == TRANSACTION_getInterfaceVersion) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == TRANSACTION_getInterfaceHash) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            if (code == TRANSACTION_onCalibrationError) {
                data.enforceNoDataAvail();
                int error = data.readInt();
                onCalibrationError(error);
                reply.writeNoException();
            } else if (code == TRANSACTION_onCalibrationStarted) {
                data.enforceNoDataAvail();
                int sensorId = data.readInt();
                onCalibrationStarted(sensorId);
                reply.writeNoException();
            } else if (code == TRANSACTION_onCalibrationFinished) {
                data.enforceNoDataAvail();
                int sensorId = data.readInt();
                onCalibrationFinished(sensorId);
                reply.writeNoException();
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements ICalibrationCallback {
            private final IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            @Override
            public void onCalibrationError(int error) throws RemoteException {
                Parcel data = Parcel.obtain(asBinder());
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    data.writeInt(error);
                    if (!mRemote.transact(TRANSACTION_onCalibrationError, data, null, 1)) {
                        throw new RemoteException("Unimplemented");
                    }
                } finally {
                    data.recycle();
                }
            }

            @Override
            public void onCalibrationStarted(int sensorId) throws RemoteException {
                Parcel data = Parcel.obtain(asBinder());
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    data.writeInt(sensorId);
                    if (!mRemote.transact(TRANSACTION_onCalibrationStarted, data, null, 1)) {
                        throw new RemoteException("Unimplemented");
                    }
                } finally {
                    data.recycle();
                }
            }

            @Override
            public void onCalibrationFinished(int sensorId) throws RemoteException {
                Parcel data = Parcel.obtain(asBinder());
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    data.writeInt(sensorId);
                    if (!mRemote.transact(TRANSACTION_onCalibrationFinished, data, null, 1)) {
                        throw new RemoteException("Unimplemented");
                    }
                } finally {
                    data.recycle();
                }
            }

            @Override
            public int getInterfaceVersion() {
                if (mCachedVersion == -1) {
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        if (!mRemote.transact(TRANSACTION_getInterfaceVersion, data, reply, 0)) {
                            throw new RemoteException(
                                    "Method getInterfaceVersion is unimplemented.");
                        }
                        reply.readException();
                        mCachedVersion = reply.readInt();
                    } finally {
                        data.recycle();
                        reply.recycle();
                    }
                }
                return mCachedVersion;
            }

            @Override
            public synchronized String getInterfaceHash() {
                if ("-1".equals(mCachedHash)) {
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        if (!mRemote.transact(TRANSACTION_getInterfaceHash, data, reply, 0)) {
                            throw new RemoteException("Method getInterfaceHash is unimplemented.");
                        }
                        reply.readException();
                        mCachedHash = reply.readString();
                    } finally {
                        data.recycle();
                        reply.recycle();
                    }
                }
                return mCachedHash;
            }
        }
    }
}

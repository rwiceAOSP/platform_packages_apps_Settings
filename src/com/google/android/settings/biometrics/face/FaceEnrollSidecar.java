package com.google.android.settings.biometrics.face;

import android.app.Activity;
import android.content.Intent;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Surface;
import androidx.fragment.app.Fragment;
import com.android.settings.biometrics.face.FaceUpdater;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public class FaceEnrollSidecar extends Fragment {
    private boolean mDebugConsent;
    private int[] mDisabledFeatures;
    private boolean mDone;
    private boolean mEnrolling;
    private CancellationSignal mEnrollmentCancel;
    private FaceUpdater mFaceUpdater;
    private final Intent mIntent;
    private Listener mListener;
    private PreviewSurfaceProvider mPreviewSurfaceProvider;
    private boolean mShouldManagePreview;
    private boolean mSingleFromMulti;
    private boolean mTalkbackEnabled;
    private byte[] mToken;
    private int mUserId;
    private int mEnrollmentSteps = -1;
    private int mEnrollmentRemaining = 0;
    private Handler mHandler = new Handler();
    private int mEnrollmentTypeVendorCode = 0;
    private final Runnable mTimeoutRunnable = new Runnable() { // from class: com.google.android.settings.biometrics.face.FaceEnrollSidecar.1
        @Override // java.lang.Runnable
        public void run() {
            FaceEnrollSidecar.this.cancelEnrollment();
        }
    };
    private final Runnable mStartEnrollRunnable = new Runnable() { // from class: com.google.android.settings.biometrics.face.FaceEnrollSidecar.2
        @Override // java.lang.Runnable
        public void run() {
            if (FaceEnrollSidecar.this.mEnrolling) {
                return;
            }
            FaceEnrollSidecar.this.startEnrollment();
        }
    };
    private final FaceManager.EnrollmentCallback mEnrollmentCallback = new FaceManager.EnrollmentCallback() { // from class: com.google.android.settings.biometrics.face.FaceEnrollSidecar.3
        public void onEnrollmentProgress(int i) {
            FaceEnrollSidecar.this.onEnrollmentProgress(i);
        }

        public void onEnrollmentHelp(int i, CharSequence charSequence) {
            FaceEnrollSidecar.this.onEnrollmentHelp(i, charSequence);
        }

        public void onEnrollmentError(int i, CharSequence charSequence) {
            FaceEnrollSidecar.this.onEnrollmentError(i, charSequence);
        }
    };
    private ArrayList mQueuedEvents = new ArrayList();

    public interface Listener {
        void onEnrollmentError(int i, CharSequence charSequence);

        void onEnrollmentHelp(int i, CharSequence charSequence);

        void onEnrollmentProgressChange(int i, int i2);
    }

    interface PreviewSurfaceProvider {
        Surface getPreviewSurface();
    }

    abstract class QueuedEvent {
        public abstract void send(Listener listener);

        private QueuedEvent(FaceEnrollSidecar faceEnrollSidecar) {
        }
    }

    class QueuedEnrollmentProgress extends QueuedEvent {
        int enrollmentSteps;
        int remaining;

        public QueuedEnrollmentProgress(FaceEnrollSidecar faceEnrollSidecar, int i, int i2) {
            super();
            this.enrollmentSteps = i;
            this.remaining = i2;
        }

        @Override // com.google.android.settings.biometrics.face.FaceEnrollSidecar.QueuedEvent
        public void send(Listener listener) {
            listener.onEnrollmentProgressChange(this.enrollmentSteps, this.remaining);
        }
    }

    class QueuedEnrollmentHelp extends QueuedEvent {
        int helpMsgId;
        CharSequence helpString;

        public QueuedEnrollmentHelp(FaceEnrollSidecar faceEnrollSidecar, int i, CharSequence charSequence) {
            super();
            this.helpMsgId = i;
            this.helpString = charSequence;
        }

        @Override // com.google.android.settings.biometrics.face.FaceEnrollSidecar.QueuedEvent
        public void send(Listener listener) {
            listener.onEnrollmentHelp(this.helpMsgId, this.helpString);
        }
    }

    class QueuedEnrollmentError extends QueuedEvent {
        int errMsgId;
        CharSequence errString;

        public QueuedEnrollmentError(FaceEnrollSidecar faceEnrollSidecar, int i, CharSequence charSequence) {
            super();
            this.errMsgId = i;
            this.errString = charSequence;
        }

        @Override // com.google.android.settings.biometrics.face.FaceEnrollSidecar.QueuedEvent
        public void send(Listener listener) {
            listener.onEnrollmentError(this.errMsgId, this.errString);
        }
    }

    public FaceEnrollSidecar(Intent intent) {
        this.mIntent = intent;
    }

    void init(int[] iArr, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mDisabledFeatures = Arrays.copyOf(iArr, iArr.length);
        this.mSingleFromMulti = z;
        this.mTalkbackEnabled = z2;
        this.mShouldManagePreview = z3;
        this.mDebugConsent = z4;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mToken = activity.getIntent().getByteArrayExtra("hw_auth_token");
        this.mUserId = activity.getIntent().getIntExtra("android.intent.extra.USER_ID", -10000);
        this.mFaceUpdater = new FaceUpdater(activity);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.mHandler.postDelayed(this.mStartEnrollRunnable, 750L);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (getActivity().isChangingConfigurations()) {
            return;
        }
        this.mHandler.removeCallbacks(this.mStartEnrollRunnable);
        cancelEnrollment();
    }

    protected void startEnrollment() {
        Surface previewSurface;
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        this.mEnrollmentSteps = -1;
        this.mEnrollmentCancel = new CancellationSignal();
        this.mEnrolling = true;
        boolean z = true;
        int i = 0;
        while (true) {
            int[] iArr = this.mDisabledFeatures;
            if (i >= iArr.length) {
                break;
            }
            if (iArr[i] == 2) {
                z = false;
            }
            i++;
        }
        if (this.mSingleFromMulti) {
            this.mEnrollmentTypeVendorCode = 2;
        } else if (z) {
            this.mEnrollmentTypeVendorCode = 0;
        } else {
            this.mEnrollmentTypeVendorCode = 1;
        }
        updateSettingsCache();
        if (this.mShouldManagePreview) {
            previewSurface = null;
        } else {
            PreviewSurfaceProvider previewSurfaceProvider = this.mPreviewSurfaceProvider;
            if (previewSurfaceProvider == null) {
                Log.e("FaceEnrollSidecar", "Preview surface provider is null");
                previewSurface = null;
            } else {
                previewSurface = previewSurfaceProvider.getPreviewSurface();
                if (previewSurface == null) {
                    Log.e("FaceEnrollSidecar", "Preview surface is null");
                }
            }
        }
        this.mFaceUpdater.enroll(this.mUserId, this.mToken, this.mEnrollmentCancel, this.mEnrollmentCallback, this.mDisabledFeatures, previewSurface, this.mDebugConsent, this.mIntent);
    }

    private void updateSettingsCache() {
        int i = 1;
        int i2 = 1;
        int i3 = 0;
        while (true) {
            int[] iArr = this.mDisabledFeatures;
            if (i3 < iArr.length) {
                int i4 = iArr[i3];
                if (i4 == 1) {
                    i = 0;
                } else if (i4 == 2) {
                    i2 = 0;
                }
                i3++;
            } else {
                Settings.Secure.putIntForUser(getActivity().getContentResolver(), "face_unlock_attention_required", i, this.mUserId);
                Settings.Secure.putIntForUser(getActivity().getContentResolver(), "face_unlock_diversity_required", i2, this.mUserId);
                return;
            }
        }
    }

    public boolean cancelEnrollment() {
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        if (!this.mEnrolling) {
            return false;
        }
        this.mEnrollmentCancel.cancel();
        this.mEnrolling = false;
        this.mEnrollmentSteps = -1;
        return true;
    }

    protected void onEnrollmentProgress(int i) {
        if (this.mEnrollmentSteps == -1) {
            this.mEnrollmentSteps = i;
        }
        this.mEnrollmentRemaining = i;
        this.mDone = i == 0;
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onEnrollmentProgressChange(this.mEnrollmentSteps, i);
        } else {
            this.mQueuedEvents.add(new QueuedEnrollmentProgress(this, this.mEnrollmentSteps, i));
        }
    }

    protected void onEnrollmentHelp(int i, CharSequence charSequence) {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onEnrollmentHelp(i, charSequence);
        } else {
            this.mQueuedEvents.add(new QueuedEnrollmentHelp(this, i, charSequence));
        }
    }

    protected void onEnrollmentError(int i, CharSequence charSequence) {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onEnrollmentError(i, charSequence);
        } else {
            this.mQueuedEvents.add(new QueuedEnrollmentError(this, i, charSequence));
        }
        this.mEnrolling = false;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
        if (listener == null) {
            return;
        }
        int i = 0;
        while (true) {
            int size = this.mQueuedEvents.size();
            ArrayList arrayList = this.mQueuedEvents;
            if (i < size) {
                ((QueuedEvent) arrayList.get(i)).send(this.mListener);
                i++;
            } else {
                arrayList.clear();
                return;
            }
        }
    }

    void setPreviewSurfaceProvider(PreviewSurfaceProvider previewSurfaceProvider) {
        this.mPreviewSurfaceProvider = previewSurfaceProvider;
    }

    public boolean isEnrolling() {
        return this.mEnrolling;
    }

    public void logEnrollmentEnded(int i, boolean z) {
        if (i == 0) {
            logEnrollmentTimeout();
        } else if (i == 1) {
            logEnrollmentSuccess(z);
        } else {
            if (i != 2) {
                return;
            }
            logEnrollmentError();
        }
    }

    private void logEnrollmentTimeout() {
        int i = this.mEnrollmentTypeVendorCode;
        if (i == 0) {
            boolean z = this.mTalkbackEnabled;
            int i2 = this.mUserId;
            if (z) {
                FaceUtils.writeVendorLog(i2, 1149);
                return;
            } else {
                FaceUtils.writeVendorLog(i2, 1142);
                return;
            }
        }
        if (i == 1) {
            boolean z2 = this.mTalkbackEnabled;
            int i3 = this.mUserId;
            if (z2) {
                FaceUtils.writeVendorLog(i3, 1153);
                return;
            } else {
                FaceUtils.writeVendorLog(i3, 1146);
                return;
            }
        }
        if (i != 2) {
            return;
        }
        boolean z3 = this.mTalkbackEnabled;
        int i4 = this.mUserId;
        if (z3) {
            FaceUtils.writeVendorLog(i4, 1159);
        } else {
            FaceUtils.writeVendorLog(i4, 1156);
        }
    }

    private void logEnrollmentSuccess(boolean z) {
        int i = this.mEnrollmentTypeVendorCode;
        if (i == 0) {
            boolean z2 = this.mTalkbackEnabled;
            if (z) {
                int i2 = this.mUserId;
                if (z2) {
                    FaceUtils.writeVendorLog(i2, 1150);
                    return;
                } else {
                    FaceUtils.writeVendorLog(i2, 1143);
                    return;
                }
            }
            int i3 = this.mUserId;
            if (z2) {
                FaceUtils.writeVendorLog(i3, 1151);
                return;
            } else {
                FaceUtils.writeVendorLog(i3, 1144);
                return;
            }
        }
        if (i == 1) {
            boolean z3 = this.mTalkbackEnabled;
            int i4 = this.mUserId;
            if (z3) {
                FaceUtils.writeVendorLog(i4, 1154);
                return;
            } else {
                FaceUtils.writeVendorLog(i4, 1147);
                return;
            }
        }
        if (i != 2) {
            return;
        }
        boolean z4 = this.mTalkbackEnabled;
        int i5 = this.mUserId;
        if (z4) {
            FaceUtils.writeVendorLog(i5, 1160);
        } else {
            FaceUtils.writeVendorLog(i5, 1157);
        }
    }

    private void logEnrollmentError() {
        int i = this.mEnrollmentTypeVendorCode;
        if (i == 0) {
            boolean z = this.mTalkbackEnabled;
            int i2 = this.mUserId;
            if (z) {
                FaceUtils.writeVendorLog(i2, 1152);
                return;
            } else {
                FaceUtils.writeVendorLog(i2, 1145);
                return;
            }
        }
        if (i == 1) {
            boolean z2 = this.mTalkbackEnabled;
            int i3 = this.mUserId;
            if (z2) {
                FaceUtils.writeVendorLog(i3, 1155);
                return;
            } else {
                FaceUtils.writeVendorLog(i3, 1148);
                return;
            }
        }
        if (i != 2) {
            return;
        }
        boolean z3 = this.mTalkbackEnabled;
        int i4 = this.mUserId;
        if (z3) {
            FaceUtils.writeVendorLog(i4, 1161);
        } else {
            FaceUtils.writeVendorLog(i4, 1158);
        }
    }
}

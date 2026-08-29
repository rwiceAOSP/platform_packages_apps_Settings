package com.google.android.settings.biometrics.fingerprint.ui.viewmodel;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.lifecycle.ViewModel;
import com.android.settings.biometrics.BiometricUtils;
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintChallengeGenerator;
import com.google.android.settings.biometrics.fingerprint.interactor.FingerprintSensorTypeInteractor;
import com.google.android.settings.biometrics.fingerprint.interactor.Sp001AllowListInteractor;
import com.google.android.settings.biometrics.fingerprint.ui.model.CredentialModel;
import com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequest;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: FingerprintEnrollmentViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class FingerprintEnrollmentViewModel extends ViewModel {
    public static final Companion Companion = new Companion(null);
    private final MutableSharedFlow _generateChallengeFailedFlow;
    private final CredentialModel credentialModel;
    private final FingerprintChallengeGenerator fingerprintChallengeGenerator;
    private final FingerprintSensorTypeInteractor fingerprintSensorTypeInteractor;
    private boolean isGeneratingChallengeDuringCheckingCredential;
    private final AtomicBoolean isWaitingActivityResult;
    private final LockPatternInteractor lockPatternInteractor;
    private final EnrollmentRequest request;
    private final Lazy shouldUseSpEnroll$delegate;
    private final Sp001AllowListInteractor sp001AllowListInteractor;

    public final EnrollmentRequest getRequest() {
        return this.request;
    }

    public FingerprintEnrollmentViewModel(EnrollmentRequest enrollmentRequest, LockPatternInteractor lockPatternInteractor, FingerprintChallengeGenerator fingerprintChallengeGenerator, FingerprintSensorTypeInteractor fingerprintSensorTypeInteractor, CredentialModel credentialModel, Sp001AllowListInteractor sp001AllowListInteractor) {
        enrollmentRequest.getClass();
        lockPatternInteractor.getClass();
        fingerprintChallengeGenerator.getClass();
        fingerprintSensorTypeInteractor.getClass();
        credentialModel.getClass();
        sp001AllowListInteractor.getClass();
        this.request = enrollmentRequest;
        this.lockPatternInteractor = lockPatternInteractor;
        this.fingerprintChallengeGenerator = fingerprintChallengeGenerator;
        this.fingerprintSensorTypeInteractor = fingerprintSensorTypeInteractor;
        this.credentialModel = credentialModel;
        this.sp001AllowListInteractor = sp001AllowListInteractor;
        this.isWaitingActivityResult = AtomicFU.atomic(false);
        this._generateChallengeFailedFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7, null);
        this.shouldUseSpEnroll$delegate = LazyKt.lazy(new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollmentViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(this.f$0.sp001AllowListInteractor.isEnabled());
            }
        });
    }

    public final AtomicBoolean isWaitingActivityResult() {
        return this.isWaitingActivityResult;
    }

    public final Object getSensorType(Continuation continuation) {
        return this.fingerprintSensorTypeInteractor.getType(continuation);
    }

    public final boolean shallFinishActivityDuringOnPause(boolean z) {
        return (z || this.request.isSuw() || this.isWaitingActivityResult.getValue()) ? false : true;
    }

    public final SharedFlow getGenerateChallengeFailedFlow() {
        return FlowKt.asSharedFlow(this._generateChallengeFailedFlow);
    }

    public final Bundle createGeneratingChallengeExtras() {
        if (!this.isGeneratingChallengeDuringCheckingCredential || !this.credentialModel.isValidToken() || !this.credentialModel.isValidChallenge()) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putByteArray("hw_auth_token", this.credentialModel.getToken());
        bundle.putLong("challenge", this.credentialModel.getChallenge());
        return bundle;
    }

    public final FingerprintEnrollmentCredentialAction checkCredential(CoroutineScope coroutineScope) {
        coroutineScope.getClass();
        if (isValidCredential()) {
            return FingerprintEnrollmentCredentialAction.CREDENTIAL_VALID;
        }
        if (isUnspecifiedPassword()) {
            return FingerprintEnrollmentCredentialAction.FAIL_NEED_TO_CHOOSE_LOCK;
        }
        if (this.credentialModel.isValidGkPwHandle()) {
            long gkPwHandle = this.credentialModel.getGkPwHandle();
            this.credentialModel.clearGkPwHandle();
            generateChallenge(gkPwHandle, false, coroutineScope);
            this.isGeneratingChallengeDuringCheckingCredential = true;
            return FingerprintEnrollmentCredentialAction.IS_GENERATING_CHALLENGE;
        }
        return FingerprintEnrollmentCredentialAction.FAIL_NEED_TO_CONFIRM_LOCK;
    }

    private final void generateChallenge(final long j, final boolean z, final CoroutineScope coroutineScope) {
        this.fingerprintChallengeGenerator.generateChallenge(new FingerprintManager.GenerateChallengeCallback() { // from class: com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollmentViewModel$generateChallenge$callback$1
            public final void onChallengeGenerated(int i, int i2, long j2) {
                CoroutineScope coroutineScope2;
                AnonymousClass1 anonymousClass1;
                try {
                    try {
                        byte[] gkHat = this.this$0.lockPatternInteractor.getGkHat(j, j2);
                        this.this$0.credentialModel.setChallenge(j2);
                        this.this$0.credentialModel.setToken(gkHat);
                        if (z) {
                            this.this$0.lockPatternInteractor.removeGkPwHandle(j);
                        }
                        Log.d("FingerprintEnrollmentViewModel", "generateChallenge(), model:" + this.this$0.credentialModel + ", revokeGkPwHandle:" + z);
                        if (this.this$0.isValidCredential()) {
                            return;
                        }
                        Log.w("FingerprintEnrollmentViewModel", "generateChallenge, invalid Credential or IllegalStateException");
                        coroutineScope2 = coroutineScope;
                        anonymousClass1 = new AnonymousClass1(this.this$0, null);
                        BuildersKt__Builders_commonKt.launch$default(coroutineScope2, null, null, anonymousClass1, 3, null);
                    } catch (BiometricUtils.GatekeeperCredentialNotMatchException e) {
                        Log.e("FingerprintEnrollmentViewModel", "generateChallenge, GatekeeperCredentialNotMatchException", e);
                        if (z) {
                            this.this$0.lockPatternInteractor.removeGkPwHandle(j);
                        }
                        Log.d("FingerprintEnrollmentViewModel", "generateChallenge(), model:" + this.this$0.credentialModel + ", revokeGkPwHandle:" + z);
                        this.this$0.isValidCredential();
                        Log.w("FingerprintEnrollmentViewModel", "generateChallenge, invalid Credential or IllegalStateException");
                        coroutineScope2 = coroutineScope;
                        anonymousClass1 = new AnonymousClass1(this.this$0, null);
                    }
                } catch (Throwable th) {
                    if (z) {
                        this.this$0.lockPatternInteractor.removeGkPwHandle(j);
                    }
                    Log.d("FingerprintEnrollmentViewModel", "generateChallenge(), model:" + this.this$0.credentialModel + ", revokeGkPwHandle:" + z);
                    if (this.this$0.isValidCredential()) {
                        throw th;
                    }
                    Log.w("FingerprintEnrollmentViewModel", "generateChallenge, invalid Credential or IllegalStateException");
                    BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 3, null);
                    throw th;
                }
            }

            /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollmentViewModel$generateChallenge$callback$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: FingerprintEnrollmentViewModel.kt */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ FingerprintEnrollmentViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(FingerprintEnrollmentViewModel fingerprintEnrollmentViewModel, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = fingerprintEnrollmentViewModel;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass1(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        MutableSharedFlow mutableSharedFlow = this.this$0._generateChallengeFailedFlow;
                        Boolean boolBoxBoolean = Boxing.boxBoolean(true);
                        this.label = 1;
                        if (mutableSharedFlow.emit(boolBoxBoolean, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else {
                        if (i != 1) {
                            Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                            return null;
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isValidCredential() {
        return !isUnspecifiedPassword() && this.credentialModel.isValidToken();
    }

    private final boolean isUnspecifiedPassword() {
        return this.lockPatternInteractor.isUnspecifiedPassword();
    }

    public final boolean generateChallengeAsCredentialActivityResult(boolean z, ActivityResult activityResult, CoroutineScope coroutineScope) {
        Intent data;
        activityResult.getClass();
        coroutineScope.getClass();
        if ((!(z && activityResult.getResultCode() == 1) && (z || activityResult.getResultCode() != -1)) || (data = activityResult.getData()) == null) {
            return false;
        }
        generateChallenge(data.getLongExtra("gk_pw_handle", 0L), true, coroutineScope);
        return true;
    }

    public final void revokeChallengeIfSuw() {
        if (this.request.isSuw()) {
            this.fingerprintChallengeGenerator.revokeChallenge(this.credentialModel.getChallenge());
            this.credentialModel.setChallenge(-1L);
        }
    }

    public final Integer getValidUserId() {
        if (this.credentialModel.isValidUserId()) {
            return Integer.valueOf(this.credentialModel.getUserId());
        }
        return null;
    }

    public final Bundle getCredentialIntentExtrasForNextActivity() {
        Bundle bundle = new Bundle();
        if (this.credentialModel.isValidGkPwHandle()) {
            bundle.putLong("gk_pw_handle", this.credentialModel.getGkPwHandle());
        }
        if (this.credentialModel.isValidToken()) {
            bundle.putByteArray("hw_auth_token", this.credentialModel.getToken());
        }
        if (this.credentialModel.isValidUserId()) {
            bundle.putInt("android.intent.extra.USER_ID", this.credentialModel.getUserId());
        }
        bundle.putLong("challenge", this.credentialModel.getChallenge());
        return bundle;
    }

    public final boolean getShouldUseSpEnroll() {
        return ((Boolean) this.shouldUseSpEnroll$delegate.getValue()).booleanValue();
    }

    /* JADX INFO: compiled from: FingerprintEnrollmentViewModel.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

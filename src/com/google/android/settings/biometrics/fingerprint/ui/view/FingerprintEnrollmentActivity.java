package com.google.android.settings.biometrics.fingerprint.ui.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingAction;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor;
import com.android.settings.biometrics.fingerprint.SetupFingerprintEnrollEnrolling;
import com.android.settings.biometrics.fingerprint.SetupFingerprintEnrollFindSensor;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.google.android.settings.R$id;
import com.google.android.settings.R$layout;
import com.google.android.settings.R$string;
import com.google.android.settings.R$style;
import com.google.android.settings.biometrics.R$navigation;
import com.google.android.settings.biometrics.fingerprint.factory.FingerprintViewModelFactory;
import com.google.android.settings.biometrics.fingerprint.ui.model.CredentialModelImpl;
import com.google.android.settings.biometrics.fingerprint.ui.model.EnrollmentRequestImpl;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollResult;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollmentCredentialAction;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollmentViewModel;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel;
import java.time.Clock;
import java.util.List;
import kotlin.Function;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.UStringsKt$$ExternalSyntheticBUOutline0;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlow;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
public class FingerprintEnrollmentActivity extends FragmentActivity {
    private final ActivityResultLauncher chooseLockLauncher;
    private final ActivityResultCallback chooseLockResultCallback;
    private final FingerprintViewModelFactory defaultViewModelProviderFactory;
    private final Lazy metricsViewModel$delegate;
    private final ActivityResultLauncher nextActivityLauncher;
    private final ActivityResultCallback nextActivityResultCallback;
    private final FingerprintEnrollmentActivity$onBackPressedCallback$1 onBackPressedCallback;
    private final Lazy setEnrollResultViewModel$delegate;
    private final Lazy viewModel$delegate;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private final Lazy navController$delegate = LazyKt.lazy(new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FingerprintEnrollmentActivity.$r8$lambda$euUU1Ysh2TF_x7_4SAb1lzRFbLc(this.f$0);
        }
    });
    private final Lazy enrollRequest$delegate = LazyKt.lazy(new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FingerprintEnrollmentActivity.m8029$r8$lambda$x1_11kBtg3nMhKPkS3s0whdf5U(this.f$0);
        }
    });
    private final Lazy credentialModel$delegate = LazyKt.lazy(new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FingerprintEnrollmentActivity.$r8$lambda$4_Rff5cuSK6TthP3uYMpYiPsxfQ(this.f$0);
        }
    });

    /* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
    public final class AddAnother extends FingerprintEnrollmentActivity {
    }

    /* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
    public final class InternalActivity extends FingerprintEnrollmentActivity {
    }

    /* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
    public final class SetupActivity extends FingerprintEnrollmentActivity {
    }

    /* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[FingerprintEnrollResult.values().length];
            try {
                iArr[FingerprintEnrollResult.INTRO_FRAGMENT_SKIP_OR_CANCEL_BUTTON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FingerprintEnrollResult.SPLIT_DIALOG_DISMISS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FingerprintEnrollResult.FIND_SENSOR_SKIP_BUTTON.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FingerprintEnrollResult.ENROLL_SKIP_BUTTON.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[FingerprintEnrollResult.INTRO_FRAGMENT_DONE_AND_FINISH_BUTTON.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[FingerprintEnrollResult.FIND_SENSOR_ERROR_FINISH.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[FingerprintEnrollResult.ENROLL_ERROR_DIALOG_OK_BUTTON_FINISH.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[FingerprintEnrollResult.FIND_SENSOR_ERROR_TIMEOUT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[FingerprintEnrollResult.ACTIVITY_ON_PAUSE_UNEXPECTED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[FingerprintEnrollResult.ENROLL_ERROR_DIALOG_OK_BUTTON_TIMEOUT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[FingerprintEnrollResult.GENERATE_CHALLENGE_FAILED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[FingerprintEnrollResult.CONFIRMATION_NEXT_BUTTON.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[FingerprintEnrollResult.INTRO_FRAGMENT_CONTINUE_ENROLL.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[FingerprintEnrollResult.FIND_SENSOR_NEXT_SCREEN.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[FingerprintEnrollmentCredentialAction.values().length];
            try {
                iArr2[FingerprintEnrollmentCredentialAction.FAIL_NEED_TO_CHOOSE_LOCK.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr2[FingerprintEnrollmentCredentialAction.FAIL_NEED_TO_CONFIRM_LOCK.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr2[FingerprintEnrollmentCredentialAction.CREDENTIAL_VALID.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr2[FingerprintEnrollmentCredentialAction.IS_GENERATING_CHALLENGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$onBackPressedCallback$1] */
    public FingerprintEnrollmentActivity() {
        final Function0 function0 = null;
        this.viewModel$delegate = new ViewModelLazy(Reflection.getOrCreateKotlinClass(FingerprintEnrollmentViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$special$$inlined$viewModels$default$2
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$special$$inlined$viewModels$default$1
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.getDefaultViewModelProviderFactory();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$special$$inlined$viewModels$default$3
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.getDefaultViewModelCreationExtras() : creationExtras;
            }
        });
        this.setEnrollResultViewModel$delegate = new ViewModelLazy(Reflection.getOrCreateKotlinClass(SetEnrollResultViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$special$$inlined$viewModels$default$5
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$special$$inlined$viewModels$default$4
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.getDefaultViewModelProviderFactory();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$special$$inlined$viewModels$default$6
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.getDefaultViewModelCreationExtras() : creationExtras;
            }
        });
        this.metricsViewModel$delegate = new ViewModelLazy(Reflection.getOrCreateKotlinClass(FingerprintMetricsViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$special$$inlined$viewModels$default$8
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$special$$inlined$viewModels$default$7
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.getDefaultViewModelProviderFactory();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$special$$inlined$viewModels$default$9
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.getDefaultViewModelCreationExtras() : creationExtras;
            }
        });
        ActivityResultCallback activityResultCallback = new ActivityResultCallback() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$nextActivityResultCallback$1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(ActivityResult activityResult) {
                activityResult.getClass();
                if (!this.this$0.getViewModel().isWaitingActivityResult().compareAndSet(true, false)) {
                    Log.w("FingerprintEnrollmentActivity", "fail to reset isWaiting flag for enrollment");
                }
                Intent data = activityResult.getData();
                boolean booleanExtra = data != null ? data.getBooleanExtra("finished_enrolling_fingerprint", false) : false;
                Log.d("FingerprintEnrollmentActivity", "get result " + activityResult + ", isSuw: " + this.this$0.getViewModel().getRequest().isSuw() + ", hasEnrolledFingerprint: " + booleanExtra);
                if (this.this$0.getViewModel().getRequest().isSuw() && activityResult.getResultCode() == 0 && booleanExtra) {
                    this.this$0.setResult(0, activityResult.getData());
                    this.this$0.finish();
                }
                if (ArraysKt.contains(new Integer[]{1, 2, 11, 3}, Integer.valueOf(activityResult.getResultCode()))) {
                    this.this$0.setResult(activityResult.getResultCode(), activityResult.getData());
                    this.this$0.finish();
                }
            }
        };
        this.nextActivityResultCallback = activityResultCallback;
        ActivityResultCallback activityResultCallback2 = new ActivityResultCallback() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$chooseLockResultCallback$1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(ActivityResult activityResult) {
                activityResult.getClass();
                this.this$0.onChooseOrConfirmLockResult(true, activityResult);
            }
        };
        this.chooseLockResultCallback = activityResultCallback2;
        this.chooseLockLauncher = registerForActivityResult(new ActivityResultContracts$StartActivityForResult(), activityResultCallback2);
        this.nextActivityLauncher = registerForActivityResult(new ActivityResultContracts$StartActivityForResult(), activityResultCallback);
        this.onBackPressedCallback = new OnBackPressedCallback() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$onBackPressedCallback$1
            {
                super(true);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() throws Exception {
                remove();
                this.this$0.getMetricsViewModel().appendAction(BiometricsOnboardingProto$OnboardingAction.ACTION_CANCEL);
                if (((List) this.this$0.getNavController().getCurrentBackStack().getValue()).size() == 2) {
                    Log.d("FingerprintEnrollmentActivity", "finalize result when backStack size is 2 and back event triggered");
                    Intent intent = new Intent();
                    intent.putExtra("biometrics_onboarding_event", this.this$0.getMetricsViewModel().sendMetricsToLogger(0));
                    this.this$0.setResult(0, intent);
                }
                this.this$0.getOnBackPressedDispatcher().onBackPressed();
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), null, null, new FingerprintEnrollmentActivity$onBackPressedCallback$1$handleOnBackPressed$1(this.this$0, null), 3, null);
            }
        };
        this.defaultViewModelProviderFactory = new FingerprintViewModelFactory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FingerprintEnrollmentViewModel getViewModel() {
        return (FingerprintEnrollmentViewModel) this.viewModel$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SetEnrollResultViewModel getSetEnrollResultViewModel() {
        return (SetEnrollResultViewModel) this.setEnrollResultViewModel$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FingerprintMetricsViewModel getMetricsViewModel() {
        return (FingerprintMetricsViewModel) this.metricsViewModel$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NavController getNavController() {
        return (NavController) this.navController$delegate.getValue();
    }

    public static NavController $r8$lambda$euUU1Ysh2TF_x7_4SAb1lzRFbLc(FingerprintEnrollmentActivity fingerprintEnrollmentActivity) {
        Fragment fragmentFindFragmentById = fingerprintEnrollmentActivity.getSupportFragmentManager().findFragmentById(R$id.nav_host_fragment);
        fragmentFindFragmentById.getClass();
        return ((NavHostFragment) fragmentFindFragmentById).getNavController();
    }

    private final EnrollmentRequestImpl getEnrollRequest() {
        return (EnrollmentRequestImpl) this.enrollRequest$delegate.getValue();
    }

    /* JADX INFO: renamed from: $r8$lambda$x1_11kBtg3nMhK-PkS3s0whdf5U, reason: not valid java name */
    public static EnrollmentRequestImpl m8029$r8$lambda$x1_11kBtg3nMhKPkS3s0whdf5U(FingerprintEnrollmentActivity fingerprintEnrollmentActivity) {
        Intent intent = fingerprintEnrollmentActivity.getIntent();
        intent.getClass();
        EnrollmentRequestImpl enrollmentRequestImpl = new EnrollmentRequestImpl(intent, fingerprintEnrollmentActivity instanceof SetupActivity, fingerprintEnrollmentActivity instanceof AddAnother);
        Log.d("FingerprintEnrollmentActivity", "Request: " + enrollmentRequestImpl);
        return enrollmentRequestImpl;
    }

    private final CredentialModelImpl getCredentialModel() {
        return (CredentialModelImpl) this.credentialModel$delegate.getValue();
    }

    public static CredentialModelImpl $r8$lambda$4_Rff5cuSK6TthP3uYMpYiPsxfQ(FingerprintEnrollmentActivity fingerprintEnrollmentActivity) {
        Bundle extras = fingerprintEnrollmentActivity.getIntent().getExtras();
        Clock clockElapsedRealtimeClock = SystemClock.elapsedRealtimeClock();
        clockElapsedRealtimeClock.getClass();
        return new CredentialModelImpl(extras, clockElapsedRealtimeClock);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addOnBackPressedCallback() {
        getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new ThemeUseCase(this).applyTheme();
        setContentView(R$layout.fingerprint_enrollment_activity);
        Log.d("FingerprintEnrollmentActivity", "onCreate() savedInstance:" + (bundle != null));
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1(null), 3, null);
        if (bundle != null) {
            if (bundle.containsKey("challenge")) {
                getCredentialModel().setChallenge(bundle.getLong("challenge"));
            }
            if (bundle.containsKey("hw_auth_token")) {
                getCredentialModel().setToken(bundle.getByteArray("hw_auth_token"));
            }
        }
        addOnBackPressedCallback();
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass2(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass3(null), 3, null);
        getLifecycle().addObserver(new DefaultLifecycleObserver() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity.onCreate.4
            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public void onStart(LifecycleOwner lifecycleOwner) {
                lifecycleOwner.getClass();
                Intent intent = new Intent("com.google.android.biometric.fingerprint.enroll.start");
                intent.setPackage("com.android.systemui");
                FingerprintEnrollmentActivity.this.sendBroadcast(intent, "android.permission.USE_BIOMETRIC_INTERNAL");
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public void onPause(LifecycleOwner lifecycleOwner) {
                lifecycleOwner.getClass();
                if (FingerprintEnrollmentActivity.this.isFinishing() || !FingerprintEnrollmentActivity.this.getViewModel().shallFinishActivityDuringOnPause(FingerprintEnrollmentActivity.this.isChangingConfigurations())) {
                    return;
                }
                FingerprintEnrollmentActivity.this.onEnrollResult(FingerprintEnrollResult.ACTIVITY_ON_PAUSE_UNEXPECTED);
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public void onStop(LifecycleOwner lifecycleOwner) {
                lifecycleOwner.getClass();
                if (FingerprintEnrollmentActivity.this.isFinishing()) {
                    Intent intent = new Intent("com.google.android.biometric.fingerprint.enroll.stop");
                    intent.setPackage("com.android.systemui");
                    FingerprintEnrollmentActivity.this.sendBroadcast(intent, "android.permission.USE_BIOMETRIC_INTERNAL");
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$onCreate$1, reason: invalid class name */
    /* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return FingerprintEnrollmentActivity.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            int ultrasonicGraphId;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FingerprintEnrollmentActivity.this.checkCredential();
                FingerprintEnrollmentViewModel viewModel = FingerprintEnrollmentActivity.this.getViewModel();
                this.label = 1;
                obj = viewModel.getSensorType(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
            }
            int iIntValue = ((Number) obj).intValue();
            if (iIntValue == 2) {
                ultrasonicGraphId = FingerprintEnrollmentActivity.this.getUltrasonicGraphId();
            } else if (iIntValue == 3) {
                ultrasonicGraphId = R$navigation.udfps_enroll;
            } else if (iIntValue == 4) {
                ultrasonicGraphId = R$navigation.sfps_enroll;
            } else {
                ultrasonicGraphId = R$navigation.intro_page;
            }
            FingerprintEnrollmentActivity.this.initNavigation(ultrasonicGraphId);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$onCreate$2, reason: invalid class name */
    /* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return FingerprintEnrollmentActivity.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SharedFlow generateChallengeFailedFlow = FingerprintEnrollmentActivity.this.getViewModel().getGenerateChallengeFailedFlow();
                final FingerprintEnrollmentActivity fingerprintEnrollmentActivity = FingerprintEnrollmentActivity.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity.onCreate.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Boolean) obj2).booleanValue(), continuation);
                    }

                    public final Object emit(boolean z, Continuation continuation) {
                        fingerprintEnrollmentActivity.onEnrollResult(FingerprintEnrollResult.GENERATE_CHALLENGE_FAILED);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (generateChallengeFailedFlow.collect(flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
            }
            UStringsKt$$ExternalSyntheticBUOutline0.m();
            return null;
        }
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$onCreate$3, reason: invalid class name */
    /* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return FingerprintEnrollmentActivity.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.FingerprintEnrollmentActivity$onCreate$3$1, reason: invalid class name */
        /* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
        final /* synthetic */ class AnonymousClass1 implements FlowCollector, FunctionAdapter {
            final /* synthetic */ FingerprintEnrollmentActivity $tmp0;

            AnonymousClass1(FingerprintEnrollmentActivity fingerprintEnrollmentActivity) {
                this.$tmp0 = fingerprintEnrollmentActivity;
            }

            public final boolean equals(Object obj) {
                if ((obj instanceof FlowCollector) && (obj instanceof FunctionAdapter)) {
                    return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
                }
                return false;
            }

            @Override // kotlin.jvm.internal.FunctionAdapter
            public final Function getFunctionDelegate() {
                return new AdaptedFunctionReference(2, this.$tmp0, FingerprintEnrollmentActivity.class, "onEnrollResult", "onEnrollResult(Lcom/google/android/settings/biometrics/fingerprint/ui/viewmodel/FingerprintEnrollResult;)V", 4);
            }

            public final int hashCode() {
                return getFunctionDelegate().hashCode();
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(FingerprintEnrollResult fingerprintEnrollResult, Continuation continuation) {
                Object objInvokeSuspend$onEnrollResult = AnonymousClass3.invokeSuspend$onEnrollResult(this.$tmp0, fingerprintEnrollResult, continuation);
                return objInvokeSuspend$onEnrollResult == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objInvokeSuspend$onEnrollResult : Unit.INSTANCE;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object invokeSuspend$onEnrollResult(FingerprintEnrollmentActivity fingerprintEnrollmentActivity, FingerprintEnrollResult fingerprintEnrollResult, Continuation continuation) {
            fingerprintEnrollmentActivity.onEnrollResult(fingerprintEnrollResult);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SharedFlow resultFlow = FingerprintEnrollmentActivity.this.getSetEnrollResultViewModel().getResultFlow();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(FingerprintEnrollmentActivity.this);
                this.label = 1;
                if (resultFlow.collect(anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
            }
            UStringsKt$$ExternalSyntheticBUOutline0.m();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getUltrasonicGraphId() {
        if (getViewModel().getShouldUseSpEnroll()) {
            Log.d("FingerprintEnrollmentActivity", "sp enroll");
            return R$navigation.usudfps_sp_enroll;
        }
        Log.d("FingerprintEnrollmentActivity", "normal enroll");
        return R$navigation.usudfps_enroll;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initNavigation(int i) {
        NavGraph navGraphInflate = getNavController().getNavInflater().inflate(i);
        if (this instanceof AddAnother) {
            Log.d("FingerprintEnrollmentActivity", "Fast enrollment launched");
            if (getCredentialModel().isValidGkPwHandle() || getCredentialModel().isValidToken()) {
                navGraphInflate.setStartDestination(com.google.android.settings.biometrics.R$id.enroll);
            }
        } else if (getViewModel().getRequest().isSkipIntro()) {
            Log.d("FingerprintEnrollmentActivity", "Skip intro launched");
            navGraphInflate.setStartDestination(com.google.android.settings.biometrics.R$id.find_sensor);
        }
        getNavController().setGraph(navGraphInflate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onEnrollResult(FingerprintEnrollResult fingerprintEnrollResult) {
        Class cls;
        Class cls2;
        boolean zIsSuw = getViewModel().getRequest().isSuw();
        switch (WhenMappings.$EnumSwitchMapping$0[fingerprintEnrollResult.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                getMetricsViewModel().appendAction(BiometricsOnboardingProto$OnboardingAction.ACTION_SKIP);
                Log.d("FingerprintEnrollmentActivity", "onEnrollResult(" + fingerprintEnrollResult + "), set result 2");
                setActivityResultAndFinish(new ActivityResult(2, null));
                break;
            case 5:
                getMetricsViewModel().appendAction(BiometricsOnboardingProto$OnboardingAction.ACTION_NEXT);
                Log.d("FingerprintEnrollmentActivity", "onEnrollResult(" + fingerprintEnrollResult + "), set result 1");
                setActivityResultAndFinish(new ActivityResult(1, null));
                break;
            case 6:
            case 7:
                Log.d("FingerprintEnrollmentActivity", "onEnrollResult(" + fingerprintEnrollResult + "), set result 1");
                setActivityResultAndFinish(new ActivityResult(1, null));
                break;
            case 8:
            case 9:
            case 10:
                Log.d("FingerprintEnrollmentActivity", "onEnrollResult(" + fingerprintEnrollResult + "), set result 3");
                setActivityResultAndFinish(new ActivityResult(3, null));
                break;
            case 11:
                Log.d("FingerprintEnrollmentActivity", "onEnrollResult(" + fingerprintEnrollResult + "), set result 0");
                setActivityResultAndFinish(new ActivityResult(0, null));
                break;
            case 12:
                getMetricsViewModel().appendAction(BiometricsOnboardingProto$OnboardingAction.ACTION_NEXT);
                getViewModel().revokeChallengeIfSuw();
                Log.d("FingerprintEnrollmentActivity", "onEnrollResult(" + fingerprintEnrollResult + "), set result 1");
                setActivityResultAndFinish(new ActivityResult(1, null));
                break;
            case 13:
                if (!getViewModel().isWaitingActivityResult().compareAndSet(false, true)) {
                    Log.w("FingerprintEnrollmentActivity", "startNext, isSuw:" + getViewModel().getRequest().isSuw() + ", fail to set isWaiting flag");
                }
                if (zIsSuw) {
                    cls = SetupFingerprintEnrollFindSensor.class;
                } else {
                    cls = FingerprintEnrollFindSensor.class;
                }
                Intent intent = new Intent(this, (Class<?>) cls);
                intent.putExtras(getViewModel().getCredentialIntentExtrasForNextActivity());
                intent.putExtras(getViewModel().getRequest().getNextIntentExtra());
                this.nextActivityLauncher.launch(intent);
                break;
            case 14:
                if (!getViewModel().isWaitingActivityResult().compareAndSet(false, true)) {
                    Log.w("FingerprintEnrollmentActivity", "FIND_SENSOR_NEXT_SCREEN startNext, isSuw:" + getViewModel().getRequest().isSuw() + ", fail to set isWaiting flag");
                }
                if (zIsSuw) {
                    cls2 = SetupFingerprintEnrollEnrolling.class;
                } else {
                    cls2 = FingerprintEnrollEnrolling.class;
                }
                Intent intent2 = new Intent(this, (Class<?>) cls2);
                intent2.putExtras(getViewModel().getCredentialIntentExtrasForNextActivity());
                intent2.putExtras(getViewModel().getRequest().getNextIntentExtra());
                this.nextActivityLauncher.launch(intent2);
                break;
            default:
                LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m();
                break;
        }
    }

    private final void setActivityResultAndFinish(ActivityResult activityResult) {
        Intent data = activityResult.getData();
        Bundle bundleCreateGeneratingChallengeExtras = getViewModel().createGeneratingChallengeExtras();
        if (data == null) {
            data = new Intent();
        }
        data.putExtra("biometrics_onboarding_event", getMetricsViewModel().sendMetricsToLogger(activityResult.getResultCode()));
        if (activityResult.getResultCode() == 1 && bundleCreateGeneratingChallengeExtras != null) {
            data.putExtras(bundleCreateGeneratingChallengeExtras);
        }
        Log.d("FingerprintEnrollmentActivity", "setActivityResultAndFinish(" + activityResult + "), override:" + data + ", challengeExtrasLen:" + (bundleCreateGeneratingChallengeExtras != null ? bundleCreateGeneratingChallengeExtras.size() : 0));
        setResult(activityResult.getResultCode(), data);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkCredential() {
        int i = WhenMappings.$EnumSwitchMapping$1[getViewModel().checkCredential(LifecycleOwnerKt.getLifecycleScope(this)).ordinal()];
        if (i == 1) {
            if (!getViewModel().isWaitingActivityResult().compareAndSet(false, true)) {
                Log.w("FingerprintEnrollmentActivity", "chooseLock, fail to set isWaiting flag to true");
            }
            this.chooseLockLauncher.launch(getChooseLockIntent());
        } else {
            if (i != 2) {
                if (i == 3 || i == 4) {
                    return;
                }
                LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m();
                return;
            }
            if (!getConfirmLockLauncher().launch()) {
                Log.e("FingerprintEnrollmentActivity", "confirmLock, launched is true");
                finish();
            } else {
                if (getViewModel().isWaitingActivityResult().compareAndSet(false, true)) {
                    return;
                }
                Log.w("FingerprintEnrollmentActivity", "confirmLock, fail to set isWaiting flag to true");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onChooseOrConfirmLockResult(boolean z, ActivityResult activityResult) {
        if (!getViewModel().isWaitingActivityResult().compareAndSet(true, false)) {
            Log.e("FingerprintEnrollmentActivity", "isChooseLock:" + z + ", fail to unset waiting flag");
        }
        if (getViewModel().generateChallengeAsCredentialActivityResult(z, activityResult, LifecycleOwnerKt.getLifecycleScope(this))) {
            return;
        }
        setActivityResultAndFinish(activityResult);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    protected void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.getClass();
        theme.applyStyle(R$style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, i, z);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            onChooseOrConfirmLockResult(false, new ActivityResult(i2, intent));
        } else {
            super.onActivityResult(i, i2, intent);
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        bundle.getClass();
        super.onSaveInstanceState(bundle);
        bundle.putLong("challenge", getCredentialModel().getChallenge());
        if (getCredentialModel().isValidToken()) {
            bundle.putByteArray("hw_auth_token", getCredentialModel().getToken());
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.lifecycle.HasDefaultViewModelProviderFactory
    public CreationExtras getDefaultViewModelCreationExtras() {
        MutableCreationExtras mutableCreationExtras = new MutableCreationExtras(super.getDefaultViewModelCreationExtras());
        FingerprintViewModelFactory.Companion companion = FingerprintViewModelFactory.Companion;
        mutableCreationExtras.set(companion.getENROLLMENT_REQUEST_KEY(), getEnrollRequest());
        mutableCreationExtras.set(companion.getCREDENTIAL_MODEL_KEY(), getCredentialModel());
        return mutableCreationExtras;
    }

    @Override // androidx.activity.ComponentActivity, androidx.lifecycle.HasDefaultViewModelProviderFactory
    public FingerprintViewModelFactory getDefaultViewModelProviderFactory() {
        return this.defaultViewModelProviderFactory;
    }

    private final Intent getChooseLockIntent() {
        Intent chooseLockIntent = BiometricUtils.getChooseLockIntent(this, getViewModel().getRequest().isSuw(), getViewModel().getRequest().getSuwExtras());
        chooseLockIntent.putExtra("hide_insecure_options", true);
        chooseLockIntent.putExtra("request_gk_pw_handle", true);
        chooseLockIntent.putExtra("for_fingerprint", true);
        Integer validUserId = getViewModel().getValidUserId();
        if (validUserId != null) {
            chooseLockIntent.putExtra("android.intent.extra.USER_ID", validUserId.intValue());
        }
        return chooseLockIntent;
    }

    private final ChooseLockSettingsHelper getConfirmLockLauncher() {
        ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(this);
        builder.setRequestCode(1).setTitle(getString(R$string.security_settings_fingerprint_preference_title)).setRequestGatekeeperPasswordHandle(true).setForegroundOnly(true).setReturnCredentials(true);
        Integer validUserId = getViewModel().getValidUserId();
        if (validUserId != null) {
            builder.setUserId(validUserId.intValue());
        }
        ChooseLockSettingsHelper chooseLockSettingsHelperBuild = builder.build();
        chooseLockSettingsHelperBuild.getClass();
        return chooseLockSettingsHelperBuild;
    }

    /* JADX INFO: compiled from: FingerprintEnrollmentActivity.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

package com.google.android.settings.biometrics.udfps.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.FragmentKt;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.android.settings.R;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingAction;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingScreen;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerEnrollState;
import com.android.settingslib.display.DisplayDensityUtils;
import com.android.systemui.biometrics.shared.model.FingerprintSensor;
import com.google.android.settings.biometrics.R$array;
import com.google.android.settings.biometrics.R$id;
import com.google.android.settings.biometrics.R$raw;
import com.google.android.settings.biometrics.R$string;
import com.google.android.settings.biometrics.combination.ui.viewmodel.VibratorViewModel;
import com.google.android.settings.biometrics.fingerprint.ui.view.NavOptionsUseCase;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollResult;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel;
import com.google.android.settings.biometrics.udfps.R$layout;
import com.google.android.settings.biometrics.udfps.factory.UdfpsViewModelFactory;
import com.google.android.settings.biometrics.udfps.ui.model.EnrollStage;
import com.google.android.settings.biometrics.udfps.ui.viewmodel.EnrollUdfpsViewModel;
import com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollEnrollingView;
import com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollHelper;
import com.google.android.settings.biometrics.udfps.ui.widget.UdfpsEnrollView;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$style;
import com.google.android.setupdesign.util.LottieAnimationHelper;
import com.google.android.setupdesign.util.ThemeHelper;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0;
import kotlin.LazyThreadSafetyMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.text.UStringsKt$$ExternalSyntheticBUOutline0;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class EnrollUdfpsFragment extends Fragment {
    public static final Companion Companion = new Companion(null);
    private final FlowCollector acquiredCollector;
    private Job acquiredJob;
    private final Runnable delayedFinishRunnable;
    private final EnrollUdfpsFragment$dialogDetachedCallback$1 dialogDetachedCallback;
    private final Lazy enrollHelper$delegate;
    private final FlowCollector errorMsgCollector;
    private Job errorMsgJob;
    private AtomicBoolean haveShownCenterLottie;
    private AtomicBoolean haveShownGuideLottie;
    private AtomicBoolean haveShownLeftEdgeLottie;
    private AtomicBoolean haveShownRightEdgeLottie;
    private final AtomicBoolean haveShownTipLottie;
    private final FlowCollector helpMsgCollector;
    private Job helpMsgJob;
    private final Lazy mVibratorViewModel$delegate;
    private final Lazy metricsViewModel$delegate;
    private final View.OnClickListener onSkipClickListener;
    private final FlowCollector pointerDownCollector;
    private Job pointerDownJob;
    private final FlowCollector pointerUpCollector;
    private Job pointerUpJob;
    private final FlowCollector progressCollector;
    private Job progressJob;
    private Integer rotation;
    private Job rotationJob;
    private final Lazy setEnrollResultViewModel$delegate;
    private final Lazy useExpressStyle$delegate;
    private final Lazy viewModel$delegate;

    /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EnrollStage.values().length];
            try {
                iArr[EnrollStage.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EnrollStage.GUIDED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[EnrollStage.FINGERTIP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[EnrollStage.LEFT_EDGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[EnrollStage.RIGHT_EDGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[EnrollStage.UNKNOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r0v26, types: [com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$dialogDetachedCallback$1] */
    public EnrollUdfpsFragment() {
        super(R$layout.enroll_udfps_fragment);
        final Function0 function0 = null;
        this.setEnrollResultViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(SetEnrollResultViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$activityViewModels$default$1
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$activityViewModels$default$2
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$activityViewModels$default$3
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        this.metricsViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(FingerprintMetricsViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$activityViewModels$default$4
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$activityViewModels$default$5
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$activityViewModels$default$6
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        this.mVibratorViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(VibratorViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$activityViewModels$default$7
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$activityViewModels$default$8
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$activityViewModels$default$9
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        final Function0 function1 = new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.requireActivity().getDefaultViewModelCreationExtras();
            }
        };
        Function0 function2 = new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.getDefaultViewModelProviderFactory();
            }
        };
        final Function0 function3 = new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$viewModels$default$1
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                return this;
            }
        };
        final Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$viewModels$default$2
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStoreOwner invoke() {
                return (ViewModelStoreOwner) function3.invoke();
            }
        });
        this.viewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(EnrollUdfpsViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$viewModels$default$3
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return FragmentViewModelLazyKt.m3756viewModels$lambda1(lazy).getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$special$$inlined$viewModels$default$4
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function4 = function1;
                if (function4 != null && (creationExtras = (CreationExtras) function4.invoke()) != null) {
                    return creationExtras;
                }
                ViewModelStoreOwner viewModelStoreOwnerM3756viewModels$lambda1 = FragmentViewModelLazyKt.m3756viewModels$lambda1(lazy);
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwnerM3756viewModels$lambda1 instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwnerM3756viewModels$lambda1 : null;
                return hasDefaultViewModelProviderFactory != null ? hasDefaultViewModelProviderFactory.getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
            }
        }, function2);
        this.useExpressStyle$delegate = LazyKt.lazy(new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(ThemeHelper.shouldApplyGlifExpressiveStyle(this.f$0.requireContext()));
            }
        });
        this.haveShownTipLottie = AtomicFU.atomic(false);
        this.haveShownLeftEdgeLottie = AtomicFU.atomic(false);
        this.haveShownRightEdgeLottie = AtomicFU.atomic(false);
        this.haveShownCenterLottie = AtomicFU.atomic(false);
        this.haveShownGuideLottie = AtomicFU.atomic(false);
        this.onSkipClickListener = new View.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$onSkipClickListener$1

            /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$onSkipClickListener$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ EnrollUdfpsFragment this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(EnrollUdfpsFragment enrollUdfpsFragment, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = enrollUdfpsFragment;
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
                        SetEnrollResultViewModel setEnrollResultViewModel = this.this$0.getSetEnrollResultViewModel();
                        FingerprintEnrollResult fingerprintEnrollResult = FingerprintEnrollResult.ENROLL_SKIP_BUTTON;
                        this.label = 1;
                        if (setEnrollResultViewModel.emit(fingerprintEnrollResult, this) == coroutine_suspended) {
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

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.this$0.cancelEnroll();
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), null, null, new AnonymousClass1(this.this$0, null), 3, null);
            }
        };
        this.progressCollector = new FlowCollector() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$progressCollector$1
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(FingerEnrollState.EnrollProgress enrollProgress, Continuation continuation) {
                if (enrollProgress != null && enrollProgress.getTotalStepsRequired() >= 0) {
                    this.this$0.updateProgress(true, enrollProgress);
                    this.this$0.updateTitleAndDescription();
                    if (this.this$0.getViewModel().isAccessibilityEnabled()) {
                        int totalStepsRequired = enrollProgress.getTotalStepsRequired();
                        int remainingSteps = (int) (((totalStepsRequired - enrollProgress.getRemainingSteps()) / totalStepsRequired) * 100.0f);
                        FragmentActivity activity = this.this$0.getActivity();
                        activity.getClass();
                        String string = activity.getString(R.string.security_settings_udfps_enroll_progress_a11y_message, new Object[]{Boxing.boxInt(remainingSteps)});
                        string.getClass();
                        EnrollUdfpsFragment.Companion companion = EnrollUdfpsFragment.Companion;
                        View view = this.this$0.getView();
                        view.getClass();
                        companion.getUdfpsEnrollView(view).setContentDescription(string);
                    }
                }
                return Unit.INSTANCE;
            }
        };
        this.helpMsgCollector = new FlowCollector() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$helpMsgCollector$1
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(FingerEnrollState.EnrollHelp enrollHelp, Continuation continuation) {
                if (enrollHelp != null) {
                    Log.d("EnrollUdfpsFragment", "helpMsgCollector(" + enrollHelp.getHelpMsgId() + ", " + enrollHelp.getHelpString() + ")");
                    if (enrollHelp.getHelpMsgId() == 3) {
                        EnrollUdfpsFragment enrollUdfpsFragment = this.this$0;
                        String string = enrollUdfpsFragment.getResources().getString(R.string.fingerprint_acquired_imager_dirty_udfps);
                        string.getClass();
                        enrollUdfpsFragment.showError(string);
                        this.this$0.getEnrollHelper().onEnrollmentHelp();
                    } else if (enrollHelp.getHelpString().length() > 0) {
                        this.this$0.showError(enrollHelp.getHelpString());
                        this.this$0.getEnrollHelper().onEnrollmentHelp();
                    }
                    EnrollUdfpsFragment enrollUdfpsFragment2 = this.this$0;
                    View view = enrollUdfpsFragment2.getView();
                    view.getClass();
                    enrollUdfpsFragment2.adjustScrollableHeaderIfNeeded((UdfpsEnrollEnrollingView) view);
                }
                return Unit.INSTANCE;
            }
        };
        this.errorMsgCollector = new FlowCollector() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$errorMsgCollector$1
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(FingerEnrollState.EnrollError enrollError, Continuation continuation) {
                if (enrollError != null) {
                    Log.d("EnrollUdfpsFragment", "errorMsgCollector(" + enrollError.getErrorId() + ")");
                    this.this$0.cancelEnroll();
                    EnrollUdfpsErrorDialog.Companion.newInstance(enrollError.getErrorId(), this.this$0.getViewModel().isSuw()).show(this.this$0.getParentFragmentManager(), EnrollUdfpsErrorDialog.class.getName());
                }
                return Unit.INSTANCE;
            }
        };
        this.acquiredCollector = new FlowCollector() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$acquiredCollector$1
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(FingerEnrollState.Acquired acquired, Continuation continuation) {
                if (acquired != null) {
                    this.this$0.getEnrollHelper().onAcquired(acquired.getAcquiredGood());
                    Log.d("EnrollUdfpsFragment", "onAcquired(), acquiredGood:" + acquired.getAcquiredGood());
                }
                return Unit.INSTANCE;
            }
        };
        this.pointerDownCollector = new FlowCollector() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$pointerDownCollector$1
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(FingerEnrollState.PointerDown pointerDown, Continuation continuation) {
                if (pointerDown != null) {
                    this.this$0.getEnrollHelper().onPointerDown(pointerDown.getFingerId());
                }
                return Unit.INSTANCE;
            }
        };
        this.pointerUpCollector = new FlowCollector() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$pointerUpCollector$1
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(FingerEnrollState.PointerUp pointerUp, Continuation continuation) {
                if (pointerUp != null) {
                    this.this$0.getEnrollHelper().onPointerUp(pointerUp.getFingerId());
                }
                return Unit.INSTANCE;
            }
        };
        this.enrollHelper$delegate = LazyKt.lazy(new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return EnrollUdfpsFragment.m8033$r8$lambda$iLLZzYAUx2XaRyCC9INqXt8LiI(this.f$0);
            }
        });
        this.delayedFinishRunnable = new Runnable() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$delayedFinishRunnable$1

            /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$delayedFinishRunnable$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                int I$0;
                Object L$0;
                int label;
                final /* synthetic */ EnrollUdfpsFragment this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(EnrollUdfpsFragment enrollUdfpsFragment, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = enrollUdfpsFragment;
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
                    NavController navControllerFindNavController;
                    int i;
                    NavOptions navOptionsNewSkipEnrollNavOptions;
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i2 = this.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj);
                        navControllerFindNavController = FragmentKt.findNavController(this.this$0);
                        int i3 = R$id.action_enrolling_to_finish;
                        EnrollUdfpsViewModel viewModel = this.this$0.getViewModel();
                        this.L$0 = navControllerFindNavController;
                        this.I$0 = i3;
                        this.label = 1;
                        Object objIsEnrollable = viewModel.isEnrollable(this);
                        if (objIsEnrollable == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        i = i3;
                        obj = objIsEnrollable;
                    } else {
                        if (i2 != 1) {
                            Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                            return null;
                        }
                        i = this.I$0;
                        navControllerFindNavController = (NavController) this.L$0;
                        ResultKt.throwOnFailure(obj);
                    }
                    if (((Boolean) obj).booleanValue() || this.this$0.getViewModel().isFastEnroll()) {
                        navOptionsNewSkipEnrollNavOptions = NavOptionsUseCase.INSTANCE.newSkipEnrollNavOptions();
                    } else {
                        navOptionsNewSkipEnrollNavOptions = NavOptionsUseCase.INSTANCE.newPopAllScreensNavOptions();
                    }
                    navControllerFindNavController.navigate(i, (Bundle) null, navOptionsNewSkipEnrollNavOptions);
                    return Unit.INSTANCE;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.this$0.getMetricsViewModel().appendAction(BiometricsOnboardingProto$OnboardingAction.ACTION_NEXT);
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), null, null, new AnonymousClass1(this.this$0, null), 3, null);
            }
        };
        this.dialogDetachedCallback = new FragmentManager.FragmentLifecycleCallbacks() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$dialogDetachedCallback$1
            @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
            public void onFragmentDetached(FragmentManager fragmentManager, Fragment fragment) {
                FragmentActivity activity;
                FragmentActivity activity2;
                fragmentManager.getClass();
                fragment.getClass();
                FragmentActivity activity3 = this.this$0.getActivity();
                Log.d("EnrollUdfpsFragment", "onFragmentDetached(), activityIsFinish:" + (activity3 != null ? Boolean.valueOf(activity3.isFinishing()) : null) + ", " + fragment);
                if (!(fragment instanceof EnrollUdfpsErrorDialog) || (activity = this.this$0.getActivity()) == null || activity.isFinishing() || (activity2 = this.this$0.getActivity()) == null) {
                    return;
                }
                activity2.recreate();
            }
        };
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
    public final VibratorViewModel getMVibratorViewModel() {
        return (VibratorViewModel) this.mVibratorViewModel$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final EnrollUdfpsViewModel getViewModel() {
        return (EnrollUdfpsViewModel) this.viewModel$delegate.getValue();
    }

    private final boolean getUseExpressStyle() {
        return ((Boolean) this.useExpressStyle$delegate.getValue()).booleanValue();
    }

    @Override // androidx.fragment.app.Fragment, androidx.lifecycle.HasDefaultViewModelProviderFactory
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return new UdfpsViewModelFactory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LottieAnimationView getIllustrationLottieView() {
        Integer num;
        Integer num2 = this.rotation;
        if (((num2 == null || num2.intValue() != 0) && ((num = this.rotation) == null || num.intValue() != 2)) || !getShouldShowLottie()) {
            return null;
        }
        View view = getView();
        view.getClass();
        return (LottieAnimationView) view.requireViewById(com.google.android.settings.biometrics.udfps.R$id.illustration_lottie);
    }

    private final boolean getShouldShowLottie() {
        DisplayDensityUtils displayDensityUtils = new DisplayDensityUtils(requireContext());
        int currentIndex = displayDensityUtils.getCurrentIndex();
        int[] values = displayDensityUtils.getValues();
        Integer numValueOf = values != null ? Integer.valueOf(values[currentIndex]) : null;
        int defaultDensity = displayDensityUtils.getDefaultDensity();
        Log.d("EnrollUdfpsFragment", "shouldShowLottie, defaultDensity: " + defaultDensity + ", currentDensity: " + numValueOf);
        return requireContext().getResources().getConfiguration().fontScale <= 1.0f && numValueOf != null && defaultDensity >= numValueOf.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UdfpsEnrollHelper getEnrollHelper() {
        return (UdfpsEnrollHelper) this.enrollHelper$delegate.getValue();
    }

    /* JADX INFO: renamed from: $r8$lambda$iLLZzYAUx2XaRy-CC9INqXt8LiI, reason: not valid java name */
    public static UdfpsEnrollHelper m8033$r8$lambda$iLLZzYAUx2XaRyCC9INqXt8LiI(EnrollUdfpsFragment enrollUdfpsFragment) {
        return new UdfpsEnrollHelper(enrollUdfpsFragment.requireContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isErrorDialogShown() {
        return getChildFragmentManager().findFragmentByTag(EnrollUdfpsErrorDialog.class.getName()) != null;
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$onViewCreated$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
    final class C05721 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $view;
        int I$0;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C05721(View view, Continuation continuation) {
            super(2, continuation);
            this.$view = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EnrollUdfpsFragment.this.new C05721(this.$view, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C05721) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:20:0x00af  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Companion companion;
            UdfpsEnrollEnrollingView udfpsEnrollEnrollingView;
            final LottieAnimationView illustrationLottieView;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow rotation = EnrollUdfpsFragment.this.getViewModel().getRotation();
                this.label = 1;
                obj = FlowKt.first(rotation, this);
                if (obj != coroutine_suspended) {
                }
                return coroutine_suspended;
            }
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
            } else {
                if (i != 2) {
                    Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                UdfpsEnrollEnrollingView udfpsEnrollEnrollingView2 = (UdfpsEnrollEnrollingView) this.L$1;
                companion = (Companion) this.L$0;
                ResultKt.throwOnFailure(obj);
                udfpsEnrollEnrollingView = udfpsEnrollEnrollingView2;
            }
            companion.bindView(udfpsEnrollEnrollingView, (FingerprintSensor) obj, EnrollUdfpsFragment.this.getEnrollHelper(), new FooterButton.Builder(EnrollUdfpsFragment.this.requireContext()), EnrollUdfpsFragment.this.onSkipClickListener);
            Companion companion2 = EnrollUdfpsFragment.Companion;
            companion2.getTitleText(this.$view).setHyphenationFrequency(0);
            companion2.getTitleText(this.$view).setAccessibilityLiveRegion(2);
            EnrollUdfpsFragment.this.adjustScrollableHeaderIfNeeded((UdfpsEnrollEnrollingView) this.$view);
            illustrationLottieView = EnrollUdfpsFragment.this.getIllustrationLottieView();
            if (illustrationLottieView != null) {
                illustrationLottieView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment.onViewCreated.1.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        LottieAnimationView lottieAnimationView = illustrationLottieView;
                        if (lottieAnimationView != null) {
                            if (lottieAnimationView.isAnimating()) {
                                lottieAnimationView.pauseAnimation();
                            } else {
                                lottieAnimationView.resumeAnimation();
                            }
                        }
                    }
                });
            }
            Log.d("EnrollUdfpsFragment", "onViewCreated(), bindView finished");
            return Unit.INSTANCE;
            int iIntValue = ((Number) obj).intValue();
            EnrollUdfpsFragment.this.rotation = Boxing.boxInt(iIntValue);
            companion = EnrollUdfpsFragment.Companion;
            View view = this.$view;
            view.getClass();
            UdfpsEnrollEnrollingView udfpsEnrollEnrollingView3 = (UdfpsEnrollEnrollingView) view;
            EnrollUdfpsViewModel viewModel = EnrollUdfpsFragment.this.getViewModel();
            this.L$0 = companion;
            this.L$1 = udfpsEnrollEnrollingView3;
            this.I$0 = iIntValue;
            this.label = 2;
            obj = viewModel.getSensorProp(this);
            if (obj != coroutine_suspended) {
                udfpsEnrollEnrollingView = udfpsEnrollEnrollingView3;
                companion.bindView(udfpsEnrollEnrollingView, (FingerprintSensor) obj, EnrollUdfpsFragment.this.getEnrollHelper(), new FooterButton.Builder(EnrollUdfpsFragment.this.requireContext()), EnrollUdfpsFragment.this.onSkipClickListener);
                Companion companion3 = EnrollUdfpsFragment.Companion;
                companion3.getTitleText(this.$view).setHyphenationFrequency(0);
                companion3.getTitleText(this.$view).setAccessibilityLiveRegion(2);
                EnrollUdfpsFragment.this.adjustScrollableHeaderIfNeeded((UdfpsEnrollEnrollingView) this.$view);
                illustrationLottieView = EnrollUdfpsFragment.this.getIllustrationLottieView();
                if (illustrationLottieView != null) {
                    illustrationLottieView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment.onViewCreated.1.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            LottieAnimationView lottieAnimationView = illustrationLottieView;
                            if (lottieAnimationView != null) {
                                if (lottieAnimationView.isAnimating()) {
                                    lottieAnimationView.pauseAnimation();
                                } else {
                                    lottieAnimationView.resumeAnimation();
                                }
                            }
                        }
                    });
                }
                Log.d("EnrollUdfpsFragment", "onViewCreated(), bindView finished");
                return Unit.INSTANCE;
            }
            return coroutine_suspended;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        view.getClass();
        super.onViewCreated(view, bundle);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C05721(view, null), 3, null);
        getParentFragmentManager().registerFragmentLifecycleCallbacks(this.dialogDetachedCallback, true);
        getLifecycle().addObserver(new AnonymousClass2());
        Companion.getUdfpsEnrollView(view).setAccessibilityLiveRegion(2);
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$onViewCreated$2, reason: invalid class name */
    /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
    public final class AnonymousClass2 implements DefaultLifecycleObserver {
        AnonymousClass2() {
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onPause(LifecycleOwner lifecycleOwner) {
            super.onPause(lifecycleOwner);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onResume(LifecycleOwner lifecycleOwner) {
            super.onResume(lifecycleOwner);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onCreate(LifecycleOwner lifecycleOwner) {
            lifecycleOwner.getClass();
            EnrollUdfpsFragment.this.observeVibratorStatus();
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onStart(LifecycleOwner lifecycleOwner) {
            lifecycleOwner.getClass();
            Log.d("EnrollUdfpsFragment", "onStart(), isEnrolling:" + EnrollUdfpsFragment.this.getViewModel().isEnrolling() + ", isErrorDialog:" + EnrollUdfpsFragment.this.isErrorDialogShown());
            if (!EnrollUdfpsFragment.this.isErrorDialogShown()) {
                boolean zIsEnrolling = EnrollUdfpsFragment.this.getViewModel().isEnrolling();
                EnrollUdfpsFragment enrollUdfpsFragment = EnrollUdfpsFragment.this;
                if (zIsEnrolling) {
                    enrollUdfpsFragment.collectEnrollFlows();
                } else {
                    enrollUdfpsFragment.startEnroll();
                }
            }
            EnrollUdfpsFragment.this.updateProgressAndHelpMessageWithoutAnimation();
            EnrollUdfpsFragment enrollUdfpsFragment2 = EnrollUdfpsFragment.this;
            enrollUdfpsFragment2.rotationJob = enrollUdfpsFragment2.collectInLifecycleScope(enrollUdfpsFragment2.getViewModel().getRotation(), new EnrollUdfpsFragment$onViewCreated$2$onStart$1(EnrollUdfpsFragment.this));
            EnrollUdfpsFragment.this.getMetricsViewModel().setScreen(BiometricsOnboardingProto$OnboardingScreen.SCREEN_ENROLLING);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object onStart$onRotationChanged(EnrollUdfpsFragment enrollUdfpsFragment, int i, Continuation continuation) {
            enrollUdfpsFragment.onRotationChanged(i);
            return Unit.INSTANCE;
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onStop(LifecycleOwner lifecycleOwner) {
            lifecycleOwner.getClass();
            Job job = EnrollUdfpsFragment.this.rotationJob;
            if (job != null) {
                Job.cancel$default(job, null, 1, null);
            }
            EnrollUdfpsFragment.this.cancelEnrollFlows();
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onDestroy(LifecycleOwner lifecycleOwner) {
            lifecycleOwner.getClass();
            EnrollUdfpsFragment.this.getParentFragmentManager().unregisterFragmentLifecycleCallbacks(EnrollUdfpsFragment.this.dialogDetachedCallback);
            boolean zIsChangingConfigurations = EnrollUdfpsFragment.this.requireActivity().isChangingConfigurations();
            Log.d("EnrollUdfpsFragment", "onDestroy(), enrolling:" + EnrollUdfpsFragment.this.getViewModel().isEnrolling() + ", isChangingConfig:" + zIsChangingConfigurations);
            if (!EnrollUdfpsFragment.this.getViewModel().isEnrolling() || zIsChangingConfigurations) {
                return;
            }
            EnrollUdfpsFragment.this.cancelEnroll();
        }
    }

    public final void updateProgressAndHelpMessageWithoutAnimation() {
        updateProgress(false, (FingerEnrollState.EnrollProgress) getViewModel().getProgressFlow().getValue());
        FingerEnrollState.EnrollHelp enrollHelp = (FingerEnrollState.EnrollHelp) CollectionsKt.first(getViewModel().getHelpFlow().getReplayCache());
        if (enrollHelp != null) {
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C05741(enrollHelp, null), 3, null);
        } else {
            updateTitleAndDescription();
        }
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$updateProgressAndHelpMessageWithoutAnimation$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
    final class C05741 extends SuspendLambda implements Function2 {
        final /* synthetic */ FingerEnrollState.EnrollHelp $helpMsg;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C05741(FingerEnrollState.EnrollHelp enrollHelp, Continuation continuation) {
            super(2, continuation);
            this.$helpMsg = enrollHelp;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EnrollUdfpsFragment.this.new C05741(this.$helpMsg, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C05741) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = EnrollUdfpsFragment.this.helpMsgCollector;
                FingerEnrollState.EnrollHelp enrollHelp = this.$helpMsg;
                this.label = 1;
                if (flowCollector.emit(enrollHelp, this) == coroutine_suspended) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void collectEnrollFlows() {
        cancelEnrollFlows();
        this.progressJob = collectInLifecycleScope(getViewModel().getProgressFlow(), this.progressCollector);
        this.helpMsgJob = collectInLifecycleScope(getViewModel().getHelpFlow(), this.helpMsgCollector);
        this.errorMsgJob = collectInLifecycleScope(getViewModel().getErrorFlow(), this.errorMsgCollector);
        this.acquiredJob = collectInLifecycleScope(getViewModel().getAcquiredFlow(), this.acquiredCollector);
        this.pointerDownJob = collectInLifecycleScope(getViewModel().getPointerDownFlow(), this.pointerDownCollector);
        this.pointerUpJob = collectInLifecycleScope(getViewModel().getPointerUpFlow(), this.pointerUpCollector);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cancelEnrollFlows() {
        Job job = this.errorMsgJob;
        if (job != null) {
            Job.cancel$default(job, null, 1, null);
        }
        this.errorMsgJob = null;
        Job job2 = this.progressJob;
        if (job2 != null) {
            Job.cancel$default(job2, null, 1, null);
        }
        this.progressJob = null;
        Job job3 = this.helpMsgJob;
        if (job3 != null) {
            Job.cancel$default(job3, null, 1, null);
        }
        this.helpMsgJob = null;
        Job job4 = this.acquiredJob;
        if (job4 != null) {
            Job.cancel$default(job4, null, 1, null);
        }
        this.acquiredJob = null;
        Job job5 = this.pointerDownJob;
        if (job5 != null) {
            Job.cancel$default(job5, null, 1, null);
        }
        this.pointerDownJob = null;
        Job job6 = this.pointerUpJob;
        if (job6 != null) {
            Job.cancel$default(job6, null, 1, null);
        }
        this.pointerUpJob = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startEnroll() {
        if (getViewModel().startEnroll()) {
            Log.d("EnrollUdfpsFragment", "startEnroll(), success");
            collectEnrollFlows();
        } else {
            Log.e("EnrollUdfpsFragment", "startEnroll(), failed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cancelEnroll() {
        if (!getViewModel().isEnrolling()) {
            Log.d("EnrollUdfpsFragment", "cancelEnroll(), failed because isEnrolling is false");
        } else {
            cancelEnrollFlows();
            getViewModel().cancelEnroll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateProgress(boolean z, FingerEnrollState.EnrollProgress enrollProgress) {
        if (!getViewModel().isEnrolling()) {
            Log.d("EnrollUdfpsFragment", "Enroll not started yet");
            return;
        }
        boolean zIsFinished = isFinished(enrollProgress);
        Log.d("EnrollUdfpsFragment", "updateProgress(" + z + ", " + enrollProgress + "), isFinished:" + zIsFinished);
        if (enrollProgress != null) {
            getEnrollHelper().onEnrollmentProgress(enrollProgress.getTotalStepsRequired(), enrollProgress.getRemainingSteps());
            if (zIsFinished) {
                if (z) {
                    BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C05731(null), 3, null);
                } else {
                    this.delayedFinishRunnable.run();
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$updateProgress$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
    final class C05731 extends SuspendLambda implements Function2 {
        int label;

        C05731(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EnrollUdfpsFragment.this.new C05731(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C05731) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(400L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
            }
            EnrollUdfpsFragment.this.delayedFinishRunnable.run();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateTitleAndDescription() {
        int i;
        int i2;
        UdfpsEnrollEnrollingView udfpsEnrollEnrollingView;
        int i3;
        UdfpsEnrollEnrollingView udfpsEnrollEnrollingView2;
        int i4;
        UdfpsEnrollEnrollingView udfpsEnrollEnrollingView3;
        int i5;
        int i6;
        UdfpsEnrollEnrollingView udfpsEnrollEnrollingView4;
        int i7;
        int i8;
        UdfpsEnrollEnrollingView udfpsEnrollEnrollingView5;
        int i9;
        Companion companion = Companion;
        View view = getView();
        view.getClass();
        TextView titleText = companion.getTitleText(view);
        View view2 = getView();
        view2.getClass();
        TextView subTitleText = companion.getSubTitleText(view2);
        EnrollStage enrollStage = (EnrollStage) getViewModel().getStageFlow().getValue();
        Log.d("EnrollUdfpsFragment", "updateTitleAndDescription(" + enrollStage + ")");
        switch (WhenMappings.$EnumSwitchMapping$0[enrollStage.ordinal()]) {
            case 1:
                View view3 = getView();
                view3.getClass();
                companion.getTitleText(view3).setText(R.string.security_settings_fingerprint_enroll_repeat_title);
                LottieAnimationView illustrationLottieView = getIllustrationLottieView();
                if (getUseExpressStyle()) {
                    i = R$raw.udfps_center_hint_lottie_expressive;
                } else {
                    i = R.raw.udfps_center_hint_lottie;
                }
                if (getUseExpressStyle() && illustrationLottieView != null) {
                    setupIllustrationAnim(getIllustrationLottieView(), i);
                }
                if (getViewModel().isAccessibilityEnabled() || illustrationLottieView == null) {
                    View view4 = getView();
                    view4.getClass();
                    TextView subTitleText2 = companion.getSubTitleText(view4);
                    if (((Boolean) getMVibratorViewModel().isVibratorEnabled().getValue()).booleanValue()) {
                        i2 = R$string.security_settings_udfps_enroll_start_message_new;
                    } else {
                        i2 = R$string.security_settings_udfps_enroll_start_message_without_haptic;
                    }
                    subTitleText2.setText(i2);
                } else if (this.haveShownCenterLottie.compareAndSet(false, true)) {
                    configureEnrollStage(illustrationLottieView, R.string.security_settings_sfps_enroll_finger_center_title, i);
                }
                if (getViewModel().isAccessibilityEnabled() && (udfpsEnrollEnrollingView = (UdfpsEnrollEnrollingView) getView()) != null) {
                    udfpsEnrollEnrollingView.setFocusOnDescription();
                }
                View view5 = getView();
                view5.getClass();
                adjustScrollableHeaderIfNeeded((UdfpsEnrollEnrollingView) view5);
                break;
            case 2:
                titleText.setText(R.string.security_settings_fingerprint_enroll_repeat_title);
                LottieAnimationView illustrationLottieView2 = getIllustrationLottieView();
                if (getUseExpressStyle()) {
                    i3 = R$raw.udfps_center_hint_lottie_expressive;
                } else {
                    i3 = R.raw.udfps_center_hint_lottie;
                }
                if (getUseExpressStyle() && illustrationLottieView2 != null) {
                    setupIllustrationAnim(getIllustrationLottieView(), i3);
                }
                if (getViewModel().isAccessibilityEnabled() || illustrationLottieView2 == null) {
                    subTitleText.setText(R.string.security_settings_udfps_enroll_repeat_a11y_message);
                } else if (this.haveShownGuideLottie.compareAndSet(false, true)) {
                    configureEnrollStage(illustrationLottieView2, R.string.security_settings_fingerprint_enroll_repeat_message, i3);
                }
                if (getViewModel().isAccessibilityEnabled() && (udfpsEnrollEnrollingView2 = (UdfpsEnrollEnrollingView) getView()) != null) {
                    udfpsEnrollEnrollingView2.setFocusOnDescription();
                }
                View view6 = getView();
                view6.getClass();
                adjustScrollableHeaderIfNeeded((UdfpsEnrollEnrollingView) view6);
                break;
            case 3:
                titleText.setText(R.string.security_settings_udfps_enroll_fingertip_title);
                LottieAnimationView illustrationLottieView3 = getIllustrationLottieView();
                if (getUseExpressStyle()) {
                    i4 = R$raw.udfps_tip_hint_lottie_expressive;
                } else {
                    i4 = R.raw.udfps_tip_hint_lottie;
                }
                if (getUseExpressStyle() && illustrationLottieView3 != null) {
                    setupIllustrationAnim(getIllustrationLottieView(), i4);
                }
                if (illustrationLottieView3 != null && this.haveShownTipLottie.compareAndSet(false, true)) {
                    configureEnrollStage(illustrationLottieView3, R.string.security_settings_udfps_tip_fingerprint_help, i4);
                }
                if (getViewModel().isAccessibilityEnabled() && (udfpsEnrollEnrollingView3 = (UdfpsEnrollEnrollingView) getView()) != null) {
                    udfpsEnrollEnrollingView3.setFocusOnDescription();
                }
                View view7 = getView();
                view7.getClass();
                adjustScrollableHeaderIfNeeded((UdfpsEnrollEnrollingView) view7);
                break;
            case 4:
                titleText.setText(R.string.security_settings_udfps_enroll_left_edge_title);
                LottieAnimationView illustrationLottieView4 = getIllustrationLottieView();
                if (getUseExpressStyle()) {
                    i5 = R$raw.udfps_left_edge_hint_lottie_expressive;
                } else {
                    i5 = R.raw.udfps_left_edge_hint_lottie;
                }
                if (getUseExpressStyle() && illustrationLottieView4 != null) {
                    setupIllustrationAnim(getIllustrationLottieView(), i5);
                }
                if (illustrationLottieView4 != null && this.haveShownLeftEdgeLottie.compareAndSet(false, true)) {
                    configureEnrollStage(illustrationLottieView4, R.string.security_settings_udfps_side_fingerprint_help, i5);
                } else if (illustrationLottieView4 == null) {
                    if (((Boolean) getViewModel().isStageHalfCompletedFlow().getValue()).booleanValue()) {
                        i6 = R.string.security_settings_fingerprint_enroll_repeat_message;
                    } else {
                        i6 = R.string.security_settings_udfps_enroll_edge_message;
                    }
                    subTitleText.setText(i6);
                }
                if (getViewModel().isAccessibilityEnabled() && (udfpsEnrollEnrollingView4 = (UdfpsEnrollEnrollingView) getView()) != null) {
                    udfpsEnrollEnrollingView4.setFocusOnDescription();
                }
                View view8 = getView();
                view8.getClass();
                adjustScrollableHeaderIfNeeded((UdfpsEnrollEnrollingView) view8);
                break;
            case 5:
                titleText.setText(R.string.security_settings_udfps_enroll_right_edge_title);
                LottieAnimationView illustrationLottieView5 = getIllustrationLottieView();
                if (getUseExpressStyle()) {
                    i7 = R$raw.udfps_right_edge_hint_lottie_expressive;
                } else {
                    i7 = R.raw.udfps_right_edge_hint_lottie;
                }
                if (getUseExpressStyle() && illustrationLottieView5 != null) {
                    setupIllustrationAnim(getIllustrationLottieView(), i7);
                }
                if (illustrationLottieView5 != null && this.haveShownRightEdgeLottie.compareAndSet(false, true)) {
                    configureEnrollStage(illustrationLottieView5, R.string.security_settings_udfps_side_fingerprint_help, i7);
                } else if (illustrationLottieView5 == null) {
                    if (((Boolean) getViewModel().isStageHalfCompletedFlow().getValue()).booleanValue()) {
                        i8 = R.string.security_settings_fingerprint_enroll_repeat_message;
                    } else {
                        i8 = R.string.security_settings_udfps_enroll_edge_message;
                    }
                    subTitleText.setText(i8);
                }
                if (getViewModel().isAccessibilityEnabled() && (udfpsEnrollEnrollingView5 = (UdfpsEnrollEnrollingView) getView()) != null) {
                    udfpsEnrollEnrollingView5.setFocusOnDescription();
                }
                View view9 = getView();
                view9.getClass();
                adjustScrollableHeaderIfNeeded((UdfpsEnrollEnrollingView) view9);
                break;
            case 6:
                titleText.setText(R.string.security_settings_fingerprint_enroll_udfps_title);
                if (((Boolean) getMVibratorViewModel().isVibratorEnabled().getValue()).booleanValue()) {
                    i9 = R$string.security_settings_udfps_enroll_start_message_new;
                } else {
                    i9 = R$string.security_settings_udfps_enroll_start_message_without_haptic;
                }
                subTitleText.setText(i9);
                String string = getString(R.string.security_settings_udfps_enroll_a11y);
                string.getClass();
                requireActivity().setTitle(string);
                break;
            default:
                LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void adjustScrollableHeaderIfNeeded(UdfpsEnrollEnrollingView udfpsEnrollEnrollingView) {
        Integer num;
        Integer num2 = this.rotation;
        if ((num2 != null && num2.intValue() == 0) || ((num = this.rotation) != null && num.intValue() == 2)) {
            adjustScrollableHeader(udfpsEnrollEnrollingView);
        }
    }

    private final void setupIllustrationAnim(LottieAnimationView lottieAnimationView, int i) {
        if (lottieAnimationView != null) {
            lottieAnimationView.setAnimation(i);
        }
        String[] stringArray = requireContext().getResources().getStringArray(R$array.udfps_enroll_enrolling);
        stringArray.getClass();
        LottieAnimationHelper.get().applyColor(requireContext(), lottieAnimationView, ArraysKt.toList(stringArray));
    }

    private final void configureEnrollStage(final LottieAnimationView lottieAnimationView, int i, int i2) {
        if (getViewModel().isAccessibilityEnabled()) {
            return;
        }
        lottieAnimationView.setContentDescription(getString(i));
        Companion companion = Companion;
        View view = getView();
        view.getClass();
        companion.getSubTitleText(view).setText("");
        LottieCompositionFactory.fromRawRes(requireContext(), i2).addListener(new LottieListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment.configureEnrollStage.1
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(LottieComposition lottieComposition) {
                lottieComposition.getClass();
                lottieAnimationView.setComposition(lottieComposition);
                Log.d("EnrollUdfpsFragment", "Set lottie visible for " + ((Object) lottieAnimationView.getContentDescription()));
                lottieAnimationView.setVisibility(0);
                lottieAnimationView.playAnimation();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showError(CharSequence charSequence) {
        Companion companion = Companion;
        View view = getView();
        view.getClass();
        TextView titleText = companion.getTitleText(view);
        titleText.setText(charSequence);
        titleText.setContentDescription(charSequence);
        View view2 = getView();
        view2.getClass();
        companion.getSubTitleText(view2).setContentDescription("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onRotationChanged(int i) {
        Log.d("EnrollUdfpsFragment", "onRotationChanged(), newRotation: " + i + " oldRotation:" + this.rotation);
        int i2 = (i + 2) % 4;
        Integer num = this.rotation;
        if (num != null && i2 == num.intValue()) {
            this.rotation = Integer.valueOf(i);
            UdfpsEnrollEnrollingView udfpsEnrollEnrollingView = (UdfpsEnrollEnrollingView) getView();
            if (udfpsEnrollEnrollingView != null) {
                udfpsEnrollEnrollingView.relayoutForFingerprintSensor();
            }
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C05711(null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$onRotationChanged$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
    final class C05711 extends SuspendLambda implements Function2 {
        Object L$0;
        Object L$1;
        int label;

        C05711(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EnrollUdfpsFragment.this.new C05711(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C05711) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Companion companion;
            UdfpsEnrollEnrollingView udfpsEnrollEnrollingView;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Companion companion2 = EnrollUdfpsFragment.Companion;
                View view = EnrollUdfpsFragment.this.getView();
                view.getClass();
                UdfpsEnrollEnrollingView udfpsEnrollEnrollingView2 = (UdfpsEnrollEnrollingView) view;
                EnrollUdfpsViewModel viewModel = EnrollUdfpsFragment.this.getViewModel();
                this.L$0 = companion2;
                this.L$1 = udfpsEnrollEnrollingView2;
                this.label = 1;
                Object sensorProp = viewModel.getSensorProp(this);
                if (sensorProp == coroutine_suspended) {
                    return coroutine_suspended;
                }
                companion = companion2;
                udfpsEnrollEnrollingView = udfpsEnrollEnrollingView2;
                obj = sensorProp;
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                UdfpsEnrollEnrollingView udfpsEnrollEnrollingView3 = (UdfpsEnrollEnrollingView) this.L$1;
                Companion companion3 = (Companion) this.L$0;
                ResultKt.throwOnFailure(obj);
                udfpsEnrollEnrollingView = udfpsEnrollEnrollingView3;
                companion = companion3;
            }
            companion.bindView(udfpsEnrollEnrollingView, (FingerprintSensor) obj, EnrollUdfpsFragment.this.getEnrollHelper(), new FooterButton.Builder(EnrollUdfpsFragment.this.requireContext()), EnrollUdfpsFragment.this.onSkipClickListener);
            Log.d("EnrollUdfpsFragment", "onRotationChanged(), bindView finished");
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$collectInLifecycleScope$1, reason: invalid class name */
    /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FlowCollector $collector;
        final /* synthetic */ Flow $this_collectInLifecycleScope;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Flow flow, FlowCollector flowCollector, Continuation continuation) {
            super(2, continuation);
            this.$this_collectInLifecycleScope = flow;
            this.$collector = flowCollector;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$this_collectInLifecycleScope, this.$collector, continuation);
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
                Flow flow = this.$this_collectInLifecycleScope;
                FlowCollector flowCollector = this.$collector;
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutine_suspended) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public final Job collectInLifecycleScope(Flow flow, FlowCollector flowCollector) {
        return BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1(flow, flowCollector, null), 3, null);
    }

    private final boolean isFinished(FingerEnrollState.EnrollProgress enrollProgress) {
        return enrollProgress != null && (RangesKt.coerceAtLeast(0, (enrollProgress.getTotalStepsRequired() + 1) - enrollProgress.getRemainingSteps()) * 10000) / (enrollProgress.getTotalStepsRequired() + 1) >= 10000;
    }

    /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TextView getTitleText(View view) {
            View viewRequireViewById = view.requireViewById(com.google.android.setupdesign.R$id.suc_layout_title);
            viewRequireViewById.getClass();
            return (TextView) viewRequireViewById;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TextView getSubTitleText(View view) {
            View viewRequireViewById = view.requireViewById(com.google.android.setupdesign.R$id.sud_layout_subtitle);
            viewRequireViewById.getClass();
            return (TextView) viewRequireViewById;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final UdfpsEnrollView getUdfpsEnrollView(View view) {
            View viewRequireViewById = view.requireViewById(com.google.android.settings.biometrics.udfps.R$id.udfps_animation_view);
            viewRequireViewById.getClass();
            return (UdfpsEnrollView) viewRequireViewById;
        }

        public final void bindView(UdfpsEnrollEnrollingView udfpsEnrollEnrollingView, FingerprintSensor fingerprintSensor, UdfpsEnrollHelper udfpsEnrollHelper, FooterButton.Builder builder, View.OnClickListener onClickListener) {
            udfpsEnrollEnrollingView.getClass();
            fingerprintSensor.getClass();
            udfpsEnrollHelper.getClass();
            builder.getClass();
            onClickListener.getClass();
            udfpsEnrollEnrollingView.initView(fingerprintSensor, udfpsEnrollHelper);
            Mixin mixin = udfpsEnrollEnrollingView.getMixin(FooterBarMixin.class);
            mixin.getClass();
            ((FooterBarMixin) mixin).setSecondaryButton(builder.setText(R.string.security_settings_fingerprint_enroll_enrolling_skip).setListener(onClickListener).setButtonType(7).setTheme(R$style.SudGlifButton_Secondary).build());
        }
    }

    private final void adjustScrollableHeader(UdfpsEnrollEnrollingView udfpsEnrollEnrollingView) {
        ScrollView scrollView = (ScrollView) udfpsEnrollEnrollingView.findViewById(R.id.sud_header_scroll_view);
        if (scrollView != null) {
            long integer = udfpsEnrollEnrollingView.getResources().getInteger(R.integer.config_biometrics_header_scroll_duration);
            udfpsEnrollEnrollingView.adjustScrollableHeaderHeight(scrollView, getShouldShowLottie());
            udfpsEnrollEnrollingView.headerVerticalScrolling(scrollView, integer);
        }
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$observeVibratorStatus$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
    final class C05701 extends SuspendLambda implements Function2 {
        int label;

        C05701(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EnrollUdfpsFragment.this.new C05701(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C05701) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment$observeVibratorStatus$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: EnrollUdfpsFragment.kt */
        final class C01761 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ EnrollUdfpsFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01761(EnrollUdfpsFragment enrollUdfpsFragment, Continuation continuation) {
                super(2, continuation);
                this.this$0 = enrollUdfpsFragment;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01761(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C01761) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    StateFlow stateFlowIsVibratorEnabled = this.this$0.getMVibratorViewModel().isVibratorEnabled();
                    C01771 c01771 = new FlowCollector() { // from class: com.google.android.settings.biometrics.udfps.ui.view.EnrollUdfpsFragment.observeVibratorStatus.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation continuation) {
                            Log.d("EnrollUdfpsFragment", "Vibrator enabled: " + z);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (stateFlowIsVibratorEnabled.collect(c01771, this) == coroutine_suspended) {
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

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                EnrollUdfpsFragment enrollUdfpsFragment = EnrollUdfpsFragment.this;
                Lifecycle.State state = Lifecycle.State.STARTED;
                C01761 c01761 = new C01761(enrollUdfpsFragment, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(enrollUdfpsFragment, state, c01761, this) == coroutine_suspended) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void observeVibratorStatus() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C05701(null), 3, null);
    }
}

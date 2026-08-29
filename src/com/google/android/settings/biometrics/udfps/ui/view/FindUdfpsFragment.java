package com.google.android.settings.biometrics.udfps.ui.view;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.navigation.fragment.FragmentKt;
import com.airbnb.lottie.LottieAnimationView;
import com.android.settings.R;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingAction;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingScreen;
import com.google.android.settings.biometrics.R$array;
import com.google.android.settings.biometrics.R$id;
import com.google.android.settings.biometrics.R$raw;
import com.google.android.settings.biometrics.R$string;
import com.google.android.settings.biometrics.fingerprint.ui.view.GlifLayoutUseCase;
import com.google.android.settings.biometrics.fingerprint.ui.view.NavOptionsUseCase;
import com.google.android.settings.biometrics.fingerprint.ui.view.SkipFindFpsDialog;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollResult;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel;
import com.google.android.settings.biometrics.udfps.R$layout;
import com.google.android.settings.biometrics.udfps.factory.UdfpsViewModelFactory;
import com.google.android.settings.biometrics.udfps.ui.viewmodel.FindUdfpsViewModel;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.R$style;
import com.google.android.setupdesign.util.LottieAnimationHelper;
import com.google.android.setupdesign.util.ThemeHelper;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: FindUdfpsFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
public class FindUdfpsFragment extends Fragment {
    public static final Companion Companion = new Companion(null);
    private final View.OnClickListener mOnNextClickListener;
    private final View.OnClickListener mOnSkipClickListener;
    private final Lazy metricsViewModel$delegate;
    private final Lazy setEnrollResultViewModel$delegate;
    private final Lazy useExpressStyle$delegate;
    private final Lazy viewModel$delegate;

    public FindUdfpsFragment() {
        super(R$layout.find_udfps);
        final Function0 function0 = new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.requireActivity().getDefaultViewModelCreationExtras();
            }
        };
        Function0 function1 = new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.getDefaultViewModelProviderFactory();
            }
        };
        final Function0 function2 = new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$special$$inlined$viewModels$default$1
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                return this;
            }
        };
        final Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$special$$inlined$viewModels$default$2
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStoreOwner invoke() {
                return (ViewModelStoreOwner) function2.invoke();
            }
        });
        this.viewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(FindUdfpsViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$special$$inlined$viewModels$default$3
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return FragmentViewModelLazyKt.m3756viewModels$lambda1(lazy).getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$special$$inlined$viewModels$default$4
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function3 = function0;
                if (function3 != null && (creationExtras = (CreationExtras) function3.invoke()) != null) {
                    return creationExtras;
                }
                ViewModelStoreOwner viewModelStoreOwnerM3756viewModels$lambda1 = FragmentViewModelLazyKt.m3756viewModels$lambda1(lazy);
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwnerM3756viewModels$lambda1 instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwnerM3756viewModels$lambda1 : null;
                return hasDefaultViewModelProviderFactory != null ? hasDefaultViewModelProviderFactory.getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
            }
        }, function1);
        this.useExpressStyle$delegate = LazyKt.lazy(new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(ThemeHelper.shouldApplyGlifExpressiveStyle(this.f$0.requireContext()));
            }
        });
        final Function0 function3 = null;
        this.setEnrollResultViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(SetEnrollResultViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$special$$inlined$activityViewModels$default$1
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$special$$inlined$activityViewModels$default$2
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function4 = function3;
                return (function4 == null || (creationExtras = (CreationExtras) function4.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$special$$inlined$activityViewModels$default$3
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        this.metricsViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(FingerprintMetricsViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$special$$inlined$activityViewModels$default$4
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$special$$inlined$activityViewModels$default$5
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function4 = function3;
                return (function4 == null || (creationExtras = (CreationExtras) function4.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$special$$inlined$activityViewModels$default$6
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        this.mOnSkipClickListener = new View.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$mOnSkipClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (!this.this$0.getViewModel().isSuw()) {
                    BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), null, null, new AnonymousClass1(this.this$0, null), 3, null);
                    return;
                }
                SkipFindFpsDialog.Companion companion = SkipFindFpsDialog.Companion;
                FragmentManager childFragmentManager = this.this$0.getChildFragmentManager();
                childFragmentManager.getClass();
                companion.showDialog(childFragmentManager);
            }

            /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$mOnSkipClickListener$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: FindUdfpsFragment.kt */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ FindUdfpsFragment this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(FindUdfpsFragment findUdfpsFragment, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = findUdfpsFragment;
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
                        FingerprintEnrollResult fingerprintEnrollResult = FingerprintEnrollResult.FIND_SENSOR_SKIP_BUTTON;
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
        };
        this.mOnNextClickListener = new View.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$mOnNextClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.this$0.getMetricsViewModel().appendAction(BiometricsOnboardingProto$OnboardingAction.ACTION_NEXT);
                FragmentKt.findNavController(this.this$0).navigate(R$id.action_find_sensor_to_enroll, (Bundle) null, NavOptionsUseCase.INSTANCE.newNavOptions());
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FindUdfpsViewModel getViewModel() {
        return (FindUdfpsViewModel) this.viewModel$delegate.getValue();
    }

    private final boolean getUseExpressStyle() {
        return ((Boolean) this.useExpressStyle$delegate.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SetEnrollResultViewModel getSetEnrollResultViewModel() {
        return (SetEnrollResultViewModel) this.setEnrollResultViewModel$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FingerprintMetricsViewModel getMetricsViewModel() {
        return (FingerprintMetricsViewModel) this.metricsViewModel$delegate.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        view.getClass();
        super.onViewCreated(view, bundle);
        if (getUseExpressStyle()) {
            View viewRequireViewById = view.requireViewById(com.google.android.settings.biometrics.udfps.R$id.illustration_lottie);
            viewRequireViewById.getClass();
            LottieAnimationView lottieAnimationView = (LottieAnimationView) viewRequireViewById;
            lottieAnimationView.setAnimation(R$raw.fingerprint_udfps_edu_lottie_expressive);
            String[] stringArray = requireContext().getResources().getStringArray(R$array.fingerprint_udfps_education_illustration);
            stringArray.getClass();
            LottieAnimationHelper.get().applyColor(requireContext(), lottieAnimationView, ArraysKt.toList(stringArray));
        }
        Companion companion = Companion;
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        fragmentActivityRequireActivity.getClass();
        companion.bindView(fragmentActivityRequireActivity, (GlifLayout) view, this.mOnNextClickListener, this.mOnSkipClickListener);
        getViewLifecycleOwner().getLifecycle().addObserver(new DefaultLifecycleObserver() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment.onViewCreated.1
            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public void onCreate(LifecycleOwner lifecycleOwner) {
                super.onCreate(lifecycleOwner);
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public void onDestroy(LifecycleOwner lifecycleOwner) {
                super.onDestroy(lifecycleOwner);
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
            public void onStop(LifecycleOwner lifecycleOwner) {
                super.onStop(lifecycleOwner);
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public void onStart(LifecycleOwner lifecycleOwner) {
                lifecycleOwner.getClass();
                FindUdfpsFragment.this.getMetricsViewModel().setScreen(BiometricsOnboardingProto$OnboardingScreen.SCREEN_EDUCATION);
            }
        });
    }

    @Override // androidx.fragment.app.Fragment, androidx.lifecycle.HasDefaultViewModelProviderFactory
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return new UdfpsViewModelFactory();
    }

    /* JADX INFO: compiled from: FindUdfpsFragment.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void bindView(FragmentActivity fragmentActivity, GlifLayout glifLayout, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
            fragmentActivity.getClass();
            glifLayout.getClass();
            onClickListener.getClass();
            onClickListener2.getClass();
            GlifLayoutUseCase glifLayoutUseCase = new GlifLayoutUseCase(glifLayout);
            glifLayoutUseCase.setHeaderText(fragmentActivity, R.string.security_settings_udfps_enroll_find_sensor_title);
            glifLayoutUseCase.setDescriptionText(glifLayout.getContext().getText(R$string.fingerprint_udfps_enroll_find_sensor_description));
            Mixin mixin = glifLayout.getMixin(FooterBarMixin.class);
            mixin.getClass();
            FooterBarMixin footerBarMixin = (FooterBarMixin) mixin;
            footerBarMixin.setSecondaryButton(new FooterButton.Builder(fragmentActivity).setText(R.string.security_settings_fingerprint_enroll_enrolling_skip).setButtonType(7).setTheme(R$style.SudGlifButton_Secondary).build());
            footerBarMixin.getSecondaryButton().setOnClickListener(onClickListener2);
            footerBarMixin.setPrimaryButton(new FooterButton.Builder(fragmentActivity).setText(R.string.security_settings_udfps_enroll_find_sensor_start_button).setButtonType(5).setTheme(R$style.SudGlifButton_Primary).build());
            footerBarMixin.getPrimaryButton().setOnClickListener(onClickListener);
            View viewRequireViewById = glifLayout.requireViewById(com.google.android.settings.biometrics.udfps.R$id.illustration_lottie);
            viewRequireViewById.getClass();
            final LottieAnimationView lottieAnimationView = (LottieAnimationView) viewRequireViewById;
            lottieAnimationView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.FindUdfpsFragment$Companion$bindView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    boolean zIsAnimating = lottieAnimationView.isAnimating();
                    LottieAnimationView lottieAnimationView2 = lottieAnimationView;
                    if (zIsAnimating) {
                        lottieAnimationView2.pauseAnimation();
                    } else {
                        lottieAnimationView2.playAnimation();
                    }
                }
            });
            lottieAnimationView.playAnimation();
        }
    }
}

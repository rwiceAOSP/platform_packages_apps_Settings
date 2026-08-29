package com.google.android.settings.biometrics.udfps.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
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
import com.google.android.settings.biometrics.R$layout;
import com.google.android.settings.biometrics.R$raw;
import com.google.android.settings.biometrics.fingerprint.ui.view.GlifLayoutUseCase;
import com.google.android.settings.biometrics.fingerprint.ui.view.NavOptionsUseCase;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollResult;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel;
import com.google.android.settings.biometrics.udfps.factory.UdfpsViewModelFactory;
import com.google.android.settings.biometrics.udfps.ui.viewmodel.ConfirmUdfpsViewModel;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupdesign.GlifLayout;
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
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: ConfirmUdfpsFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
public class ConfirmUdfpsFragment extends Fragment {
    private final View.OnClickListener addButtonClickListener;
    private final Lazy metricsViewModel$delegate;
    private final View.OnClickListener nextButtonClickListener;
    private final Lazy setEnrollResultViewModel$delegate;
    private final Lazy useExpressStyle$delegate;
    private final Lazy viewModel$delegate;

    public ConfirmUdfpsFragment() {
        super(R.layout.fingerprint_enroll_finish);
        final Function0 function0 = new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.requireActivity().getDefaultViewModelCreationExtras();
            }
        };
        Function0 function1 = new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.getDefaultViewModelProviderFactory();
            }
        };
        final Function0 function2 = new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$special$$inlined$viewModels$default$1
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                return this;
            }
        };
        final Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$special$$inlined$viewModels$default$2
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStoreOwner invoke() {
                return (ViewModelStoreOwner) function2.invoke();
            }
        });
        this.viewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(ConfirmUdfpsViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$special$$inlined$viewModels$default$3
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return FragmentViewModelLazyKt.m3756viewModels$lambda1(lazy).getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$special$$inlined$viewModels$default$4
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
        this.useExpressStyle$delegate = LazyKt.lazy(new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(ThemeHelper.shouldApplyGlifExpressiveStyle(this.f$0.requireContext()));
            }
        });
        final Function0 function3 = null;
        this.setEnrollResultViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(SetEnrollResultViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$special$$inlined$activityViewModels$default$1
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$special$$inlined$activityViewModels$default$2
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function4 = function3;
                return (function4 == null || (creationExtras = (CreationExtras) function4.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$special$$inlined$activityViewModels$default$3
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        this.metricsViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(FingerprintMetricsViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$special$$inlined$activityViewModels$default$4
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$special$$inlined$activityViewModels$default$5
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function4 = function3;
                return (function4 == null || (creationExtras = (CreationExtras) function4.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$special$$inlined$activityViewModels$default$6
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        this.addButtonClickListener = new View.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$addButtonClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.this$0.getMetricsViewModel().appendAction(BiometricsOnboardingProto$OnboardingAction.ACTION_ADD_ANOTHER_FINGERPRINT);
                FragmentKt.findNavController(this.this$0).navigate(R$id.action_finish_to_enrolling, (Bundle) null, NavOptionsUseCase.INSTANCE.newBackToEnrollNavOptions());
            }
        };
        this.nextButtonClickListener = new View.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$nextButtonClickListener$1

            /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$nextButtonClickListener$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: ConfirmUdfpsFragment.kt */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ ConfirmUdfpsFragment this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(ConfirmUdfpsFragment confirmUdfpsFragment, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = confirmUdfpsFragment;
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
                        FingerprintEnrollResult fingerprintEnrollResult = FingerprintEnrollResult.CONFIRMATION_NEXT_BUTTON;
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
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), null, null, new AnonymousClass1(this.this$0, null), 3, null);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConfirmUdfpsViewModel getViewModel() {
        return (ConfirmUdfpsViewModel) this.viewModel$delegate.getValue();
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

    @Override // androidx.fragment.app.Fragment, androidx.lifecycle.HasDefaultViewModelProviderFactory
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return new UdfpsViewModelFactory();
    }

    private final GlifLayout getGlifLayout() {
        View view = getView();
        view.getClass();
        return (GlifLayout) view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final GlifLayoutUseCase getGlifLayoutUseCase() {
        return new GlifLayoutUseCase(getGlifLayout());
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        layoutInflater.getClass();
        if (getUseExpressStyle()) {
            i = R$layout.udfps_enroll_finish_expressive;
        } else {
            i = R.layout.fingerprint_enroll_finish;
        }
        return layoutInflater.inflate(i, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        view.getClass();
        super.onViewCreated(view, bundle);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1((FooterBarMixin) getGlifLayout().getMixin(FooterBarMixin.class), null), 3, null);
        getLifecycle().addObserver(new DefaultLifecycleObserver() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment.onViewCreated.2
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
                ConfirmUdfpsFragment.this.getMetricsViewModel().setScreen(BiometricsOnboardingProto$OnboardingScreen.SCREEN_CONFIRMATION);
            }
        });
        if (getUseExpressStyle()) {
            setupExpressiveStyleAnim(view);
        }
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment$onViewCreated$1, reason: invalid class name */
    /* JADX INFO: compiled from: ConfirmUdfpsFragment.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FooterBarMixin $footer;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(FooterBarMixin footerBarMixin, Continuation continuation) {
            super(2, continuation);
            this.$footer = footerBarMixin;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ConfirmUdfpsFragment.this.new AnonymousClass1(this.$footer, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:27:0x009b  */
        /* JADX WARN: Code duplicated, block: B:28:0x00a2  */
        /* JADX WARN: Code duplicated, block: B:37:0x00e4  */
        /* JADX WARN: Code duplicated, block: B:38:0x00e7  */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x00ae, code lost:
        
            if (r10 == r0) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0119, code lost:
        
            if (r10 == r0) goto L41;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r10) {
            /*
                Method dump skipped, instruction units count: 342
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private final void setupExpressiveStyleAnim(View view) {
        View viewFindViewById = view.findViewById(R$id.fingerprint_enroll_finish_lottie);
        viewFindViewById.getClass();
        final LottieAnimationView lottieAnimationView = (LottieAnimationView) viewFindViewById;
        lottieAnimationView.setAnimation(R$raw.fingerprint_enroll_finish_expressive);
        lottieAnimationView.playAnimation();
        lottieAnimationView.setVisibility(0);
        lottieAnimationView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.settings.biometrics.udfps.ui.view.ConfirmUdfpsFragment.setupExpressiveStyleAnim.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LottieAnimationView lottieAnimationView2 = lottieAnimationView;
                if (lottieAnimationView2 != null) {
                    if (lottieAnimationView2.isAnimating()) {
                        lottieAnimationView2.pauseAnimation();
                    } else {
                        lottieAnimationView2.resumeAnimation();
                    }
                }
            }
        });
        String[] stringArray = requireContext().getResources().getStringArray(R$array.add_fingerprint_success);
        stringArray.getClass();
        LottieAnimationHelper.get().applyColor(requireContext(), lottieAnimationView, ArraysKt.toList(stringArray));
    }
}

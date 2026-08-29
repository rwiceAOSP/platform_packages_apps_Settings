package com.google.android.settings.biometrics.fingerprint.ui.view;

import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResourcesManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.settings.R;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingScreen;
import com.android.settingslib.widget.theme.R$color;
import com.google.android.settings.biometrics.R$drawable;
import com.google.android.settings.biometrics.R$id;
import com.google.android.settings.biometrics.R$layout;
import com.google.android.settings.biometrics.R$string;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollIntroUiState;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollResult;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel;
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.R$style;
import com.google.android.setupdesign.template.RequireScrollMixin;
import com.google.android.setupdesign.util.DeviceHelper;
import com.google.android.setupdesign.util.ThemeHelper;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: compiled from: IntroFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
public class IntroFragment extends Fragment {
    public static final Companion Companion = new Companion(null);
    private final Lazy metricsViewModel$delegate;
    private final View.OnClickListener onNextClickListener;
    private final View.OnClickListener onSkipOrCancelClickListener;
    private final Lazy setEnrollResultViewModel$delegate;
    private final Lazy viewModel$delegate;

    public IntroFragment() {
        super(R.layout.fingerprint_enroll_introduction);
        final Function0 function0 = null;
        this.viewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(IntroViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$special$$inlined$activityViewModels$default$1
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$special$$inlined$activityViewModels$default$2
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$special$$inlined$activityViewModels$default$3
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        this.setEnrollResultViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(SetEnrollResultViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$special$$inlined$activityViewModels$default$4
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$special$$inlined$activityViewModels$default$5
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$special$$inlined$activityViewModels$default$6
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        this.metricsViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(FingerprintMetricsViewModel.class), new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$special$$inlined$activityViewModels$default$7
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$special$$inlined$activityViewModels$default$8
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        }, new Function0() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$special$$inlined$activityViewModels$default$9
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return this.requireActivity().getDefaultViewModelProviderFactory();
            }
        });
        this.onNextClickListener = new View.OnClickListener() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$onNextClickListener$1

            /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$onNextClickListener$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: IntroFragment.kt */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                boolean Z$0;
                int label;
                final /* synthetic */ IntroFragment this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(IntroFragment introFragment, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = introFragment;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass1(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code duplicated, block: B:28:0x0070 A[DONT_INVERT] */
                /* JADX WARN: Code duplicated, block: B:29:0x0072  */
                /* JADX WARN: Code duplicated, block: B:31:0x0076  */
                /* JADX WARN: Code duplicated, block: B:34:0x0087  */
                /* JADX WARN: Code duplicated, block: B:36:0x00a4  */
                /* JADX WARN: Code duplicated, block: B:38:0x00c3  */
                /* JADX WARN: Code restructure failed: missing block: B:32:0x0084, code lost:
                
                    if (r7.emit(r2, r6) == r0) goto L42;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:41:0x00ee, code lost:
                
                    if (r7.emit(r1, r6) == r0) goto L42;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r7) {
                    /*
                        Method dump skipped, instruction units count: 244
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$onNextClickListener$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), null, null, new AnonymousClass1(this.this$0, null), 3, null);
            }
        };
        this.onSkipOrCancelClickListener = new View.OnClickListener() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$onSkipOrCancelClickListener$1

            /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$onSkipOrCancelClickListener$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: IntroFragment.kt */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ IntroFragment this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(IntroFragment introFragment, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = introFragment;
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
                        FingerprintEnrollResult fingerprintEnrollResult = FingerprintEnrollResult.INTRO_FRAGMENT_SKIP_OR_CANCEL_BUTTON;
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
    public final IntroViewModel getViewModel() {
        return (IntroViewModel) this.viewModel$delegate.getValue();
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
    public final FooterBarMixin getFooterBarMixin() {
        View view = getView();
        view.getClass();
        Mixin mixin = ((GlifLayout) view).getMixin(FooterBarMixin.class);
        mixin.getClass();
        return (FooterBarMixin) mixin;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final RequireScrollMixin getRequireScrollMixin() {
        View view = getView();
        view.getClass();
        Mixin mixin = ((GlifLayout) view).getMixin(RequireScrollMixin.class);
        mixin.getClass();
        return (RequireScrollMixin) mixin;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater.getClass();
        return layoutInflater.inflate(R$layout.fingerprint_enroll_introduction_2, viewGroup, false);
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$onViewCreated$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: IntroFragment.kt */
    final class C05661 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $view;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C05661(View view, Continuation continuation) {
            super(2, continuation);
            this.$view = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return IntroFragment.this.new C05661(this.$view, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C05661) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:22:0x00de  */
        /* JADX WARN: Code duplicated, block: B:23:0x00e4  */
        /* JADX WARN: Code duplicated, block: B:27:0x0126  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            GlifLayout glifLayout;
            Object sensorType;
            Companion companion;
            PorterDuffColorFilter porterDuffColorFilter;
            FragmentActivity fragmentActivity;
            String str;
            Object sensorType2;
            PorterDuffColorFilter porterDuffColorFilter2;
            String str2;
            GlifLayout glifLayout2;
            FragmentActivity fragmentActivity2;
            Companion companion2;
            boolean z;
            PorterDuffColorFilter porterDuffColorFilter3;
            String str3;
            GlifLayout glifLayout3;
            FragmentActivity fragmentActivity3;
            Companion companion3;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    porterDuffColorFilter = (PorterDuffColorFilter) this.L$4;
                    str = (String) this.L$3;
                    glifLayout = (GlifLayout) this.L$2;
                    fragmentActivity = (FragmentActivity) this.L$1;
                    companion = (Companion) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    sensorType = obj;
                } else {
                    if (i != 2) {
                        Segment$$ExternalSyntheticBUOutline1.m("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                    porterDuffColorFilter2 = (PorterDuffColorFilter) this.L$4;
                    str2 = (String) this.L$3;
                    glifLayout2 = (GlifLayout) this.L$2;
                    fragmentActivity2 = (FragmentActivity) this.L$1;
                    companion2 = (Companion) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    sensorType2 = obj;
                }
                if (((Number) sensorType2).intValue() == 2) {
                    companion = companion2;
                    fragmentActivity = fragmentActivity2;
                    glifLayout = glifLayout2;
                    str = str2;
                    porterDuffColorFilter = porterDuffColorFilter2;
                    porterDuffColorFilter3 = porterDuffColorFilter;
                    z = true;
                    str3 = str;
                    glifLayout3 = glifLayout;
                    fragmentActivity3 = fragmentActivity;
                    companion3 = companion;
                } else {
                    porterDuffColorFilter3 = porterDuffColorFilter2;
                    str3 = str2;
                    z = false;
                    glifLayout3 = glifLayout2;
                    fragmentActivity3 = fragmentActivity2;
                    companion3 = companion2;
                }
                boolean zIsFingerprintUnlockDisabledByAdmin = IntroFragment.this.getViewModel().isFingerprintUnlockDisabledByAdmin();
                boolean zIsParentalConsentRequired = IntroFragment.this.getViewModel().isParentalConsentRequired();
                final IntroFragment introFragment = IntroFragment.this;
                Supplier supplier = new Supplier() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment.onViewCreated.1.1
                    @Override // java.util.function.Supplier
                    public final String get() {
                        DevicePolicyResourcesManager resources = ((DevicePolicyManager) introFragment.requireContext().getSystemService(DevicePolicyManager.class)).getResources();
                        final IntroFragment introFragment2 = introFragment;
                        return resources.getString("Settings.FINGERPRINT_UNLOCK_DISABLED", new Supplier() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment.onViewCreated.1.1.1
                            @Override // java.util.function.Supplier
                            public final String get() {
                                return introFragment2.requireContext().getString(R.string.security_settings_fingerprint_enroll_introduction_message_unlock_disabled);
                            }
                        });
                    }
                };
                final IntroFragment introFragment2 = IntroFragment.this;
                companion3.bindView(fragmentActivity3, glifLayout3, str3, porterDuffColorFilter3, z, zIsFingerprintUnlockDisabledByAdmin, zIsParentalConsentRequired, supplier, new Supplier() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment.onViewCreated.1.2
                    @Override // java.util.function.Supplier
                    public final CharSequence get() {
                        return DeviceHelper.getDeviceName(introFragment2.requireContext());
                    }
                });
                if (ThemeHelper.shouldApplyGlifExpressiveStyle(IntroFragment.this.requireContext())) {
                    View viewFindViewById = this.$view.findViewById(R$id.illustrationImage);
                    viewFindViewById.getClass();
                    ((ImageView) viewFindViewById).setImageResource(R$drawable.fingerprint_enroll_introduction_expressive);
                }
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            Companion companion4 = IntroFragment.Companion;
            FragmentActivity fragmentActivityRequireActivity = IntroFragment.this.requireActivity();
            fragmentActivityRequireActivity.getClass();
            View view = this.$view;
            view.getClass();
            glifLayout = (GlifLayout) view;
            String string = IntroFragment.this.requireContext().getString(R$string.security_settings_fingerprint_v2_enroll_introduction_message_learn_more_2, Boxing.boxInt(0));
            string.getClass();
            PorterDuffColorFilter porterDuffColorFilter4 = new PorterDuffColorFilter(IntroFragment.this.requireContext().getColor(R$color.settingslib_materialColorOnSurfaceVariant), PorterDuff.Mode.SRC_IN);
            IntroViewModel viewModel = IntroFragment.this.getViewModel();
            this.L$0 = companion4;
            this.L$1 = fragmentActivityRequireActivity;
            this.L$2 = glifLayout;
            this.L$3 = string;
            this.L$4 = porterDuffColorFilter4;
            this.label = 1;
            sensorType = viewModel.getSensorType(this);
            if (sensorType != coroutine_suspended) {
                companion = companion4;
                porterDuffColorFilter = porterDuffColorFilter4;
                fragmentActivity = fragmentActivityRequireActivity;
                str = string;
            }
            return coroutine_suspended;
            if (((Number) sensorType).intValue() != 3) {
                IntroViewModel viewModel2 = IntroFragment.this.getViewModel();
                this.L$0 = companion;
                this.L$1 = fragmentActivity;
                this.L$2 = glifLayout;
                this.L$3 = str;
                this.L$4 = porterDuffColorFilter;
                this.label = 2;
                sensorType2 = viewModel2.getSensorType(this);
                if (sensorType2 != coroutine_suspended) {
                    porterDuffColorFilter2 = porterDuffColorFilter;
                    str2 = str;
                    glifLayout2 = glifLayout;
                    fragmentActivity2 = fragmentActivity;
                    companion2 = companion;
                    if (((Number) sensorType2).intValue() == 2) {
                        companion = companion2;
                        fragmentActivity = fragmentActivity2;
                        glifLayout = glifLayout2;
                        str = str2;
                        porterDuffColorFilter = porterDuffColorFilter2;
                        porterDuffColorFilter3 = porterDuffColorFilter;
                        z = true;
                        str3 = str;
                        glifLayout3 = glifLayout;
                        fragmentActivity3 = fragmentActivity;
                        companion3 = companion;
                    } else {
                        porterDuffColorFilter3 = porterDuffColorFilter2;
                        str3 = str2;
                        z = false;
                        glifLayout3 = glifLayout2;
                        fragmentActivity3 = fragmentActivity2;
                        companion3 = companion2;
                    }
                }
                return coroutine_suspended;
            }
            porterDuffColorFilter3 = porterDuffColorFilter;
            z = true;
            str3 = str;
            glifLayout3 = glifLayout;
            fragmentActivity3 = fragmentActivity;
            companion3 = companion;
            boolean zIsFingerprintUnlockDisabledByAdmin2 = IntroFragment.this.getViewModel().isFingerprintUnlockDisabledByAdmin();
            boolean zIsParentalConsentRequired2 = IntroFragment.this.getViewModel().isParentalConsentRequired();
            final IntroFragment introFragment3 = IntroFragment.this;
            Supplier supplier2 = new Supplier() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment.onViewCreated.1.1
                @Override // java.util.function.Supplier
                public final String get() {
                    DevicePolicyResourcesManager resources = ((DevicePolicyManager) introFragment3.requireContext().getSystemService(DevicePolicyManager.class)).getResources();
                    final IntroFragment introFragment4 = introFragment3;
                    return resources.getString("Settings.FINGERPRINT_UNLOCK_DISABLED", new Supplier() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment.onViewCreated.1.1.1
                        @Override // java.util.function.Supplier
                        public final String get() {
                            return introFragment4.requireContext().getString(R.string.security_settings_fingerprint_enroll_introduction_message_unlock_disabled);
                        }
                    });
                }
            };
            final IntroFragment introFragment4 = IntroFragment.this;
            companion3.bindView(fragmentActivity3, glifLayout3, str3, porterDuffColorFilter3, z, zIsFingerprintUnlockDisabledByAdmin2, zIsParentalConsentRequired2, supplier2, new Supplier() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment.onViewCreated.1.2
                @Override // java.util.function.Supplier
                public final CharSequence get() {
                    return DeviceHelper.getDeviceName(introFragment4.requireContext());
                }
            });
            if (ThemeHelper.shouldApplyGlifExpressiveStyle(IntroFragment.this.requireContext())) {
                View viewFindViewById2 = this.$view.findViewById(R$id.illustrationImage);
                viewFindViewById2.getClass();
                ((ImageView) viewFindViewById2).setImageResource(R$drawable.fingerprint_enroll_introduction_expressive);
            }
            return Unit.INSTANCE;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        view.getClass();
        super.onViewCreated(view, bundle);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C05661(view, null), 3, null);
        getViewLifecycleOwner().getLifecycle().addObserver(new DefaultLifecycleObserver() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment.onViewCreated.2
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
                IntroFragment.this.getMetricsViewModel().setScreen(BiometricsOnboardingProto$OnboardingScreen.SCREEN_INTRO);
                IntroFragment.this.initPrimaryFooterButton();
                IntroFragment.this.initSecondaryFooterButton();
                IntroFragment.this.collectPageStatusFlowIfNeed();
                IntroFragment.this.showSplitScreenDialogIfNeed();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initPrimaryFooterButton() {
        if (getFooterBarMixin().getPrimaryButton() != null) {
            return;
        }
        FooterButton footerButtonBuild = new FooterButton.Builder(requireContext()).setText(R.string.security_settings_fingerprint_enroll_introduction_agree).setButtonType(6).setTheme(R$style.SudGlifButton_Primary).build();
        footerButtonBuild.setOnClickListener(this.onNextClickListener);
        getFooterBarMixin().setPrimaryButton(footerButtonBuild);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initSecondaryFooterButton() {
        if (getFooterBarMixin().getSecondaryButton() != null) {
            return;
        }
        FooterButton footerButtonBuild = new FooterButton.Builder(requireContext()).setText(R.string.security_settings_fingerprint_enroll_introduction_no_thanks).setButtonType(5).setTheme(R$style.SudGlifButton_Primary).build();
        footerButtonBuild.setOnClickListener(this.onSkipOrCancelClickListener);
        getFooterBarMixin().setSecondaryButton(footerButtonBuild, true);
    }

    /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$collectPageStatusFlowIfNeed$1, reason: invalid class name */
    /* JADX INFO: compiled from: IntroFragment.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return IntroFragment.this.new AnonymousClass1(continuation);
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
                Flow uiState = IntroFragment.this.getViewModel().getUiState();
                FlowCollector flowCollector = new FlowCollector() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment.collectPageStatusFlowIfNeed.1.1
                    private boolean hasRequireScrollWithButton;

                    {
                        this.hasRequireScrollWithButton = this.this$0.getRequireScrollMixin().isScrollingRequired();
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public Object emit(final FingerprintEnrollIntroUiState fingerprintEnrollIntroUiState, Continuation continuation) {
                        boolean zShouldApplyGlifExpressiveStyle = ThemeHelper.shouldApplyGlifExpressiveStyle(this.this$0.requireContext());
                        Log.d("IntroFragment", "collectPageStatusFlowIfNeed uiState:" + fingerprintEnrollIntroUiState);
                        if (!this.hasRequireScrollWithButton && !fingerprintEnrollIntroUiState.getHasScrolledToBottom()) {
                            RequireScrollMixin requireScrollMixin = this.this$0.getRequireScrollMixin();
                            FragmentActivity fragmentActivityRequireActivity = this.this$0.requireActivity();
                            FooterButton primaryButton = this.this$0.getFooterBarMixin().getPrimaryButton();
                            primaryButton.getClass();
                            FooterButton secondaryButton = this.this$0.getFooterBarMixin().getSecondaryButton();
                            secondaryButton.getClass();
                            requireScrollMixin.requireScrollWithButton(fragmentActivityRequireActivity, primaryButton, secondaryButton, this.this$0.getMoreButtonTextRes(), this.this$0.onNextClickListener);
                            if (!zShouldApplyGlifExpressiveStyle) {
                                RequireScrollMixin requireScrollMixin2 = this.this$0.getRequireScrollMixin();
                                final IntroFragment introFragment = this.this$0;
                                requireScrollMixin2.setOnRequireScrollStateChangedListener(new RequireScrollMixin.OnRequireScrollStateChangedListener() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$collectPageStatusFlowIfNeed$1$1$emit$2

                                    /* JADX INFO: renamed from: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment$collectPageStatusFlowIfNeed$1$1$emit$2$1, reason: invalid class name */
                                    /* JADX INFO: compiled from: IntroFragment.kt */
                                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                        int label;
                                        final /* synthetic */ IntroFragment this$0;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        AnonymousClass1(IntroFragment introFragment, Continuation continuation) {
                                            super(2, continuation);
                                            this.this$0 = introFragment;
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
                                                IntroViewModel viewModel = this.this$0.getViewModel();
                                                this.label = 1;
                                                if (viewModel.onScrollToBottom(this) == coroutine_suspended) {
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

                                    @Override // com.google.android.setupdesign.template.RequireScrollMixin.OnRequireScrollStateChangedListener
                                    public final void onRequireScrollStateChanged(boolean z) {
                                        if (!z) {
                                            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(introFragment), null, null, new AnonymousClass1(introFragment, null), 3, null);
                                        }
                                        introFragment.updateFooterButtons(fingerprintEnrollIntroUiState, false);
                                    }
                                });
                            }
                            this.hasRequireScrollWithButton = true;
                        }
                        this.this$0.updateFooterButtons(fingerprintEnrollIntroUiState, zShouldApplyGlifExpressiveStyle);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (uiState.collect(flowCollector, this) == coroutine_suspended) {
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
    public final void collectPageStatusFlowIfNeed() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean showSplitScreenDialogIfNeed() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return false;
        }
        SplitScreenDialog.Companion companion = SplitScreenDialog.Companion;
        FragmentManager childFragmentManager = getChildFragmentManager();
        childFragmentManager.getClass();
        companion.dismissExistingDialog(childFragmentManager);
        if (!companion.shouldShowDialog(activity)) {
            return false;
        }
        FragmentManager childFragmentManager2 = getChildFragmentManager();
        childFragmentManager2.getClass();
        companion.showDialog(childFragmentManager2);
        if (getViewModel().getRequest().isSuw()) {
            return true;
        }
        getChildFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() { // from class: com.google.android.settings.biometrics.fingerprint.ui.view.IntroFragment.showSplitScreenDialogIfNeed.1
            @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
            public void onFragmentDetached(FragmentManager fragmentManager, Fragment fragment) {
                fragmentManager.getClass();
                fragment.getClass();
                if (fragment instanceof SplitScreenDialog) {
                    BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(IntroFragment.this), null, null, new IntroFragment$showSplitScreenDialogIfNeed$1$onFragmentDetached$1(IntroFragment.this, null), 3, null);
                }
            }
        }, false);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateFooterButtons(FingerprintEnrollIntroUiState fingerprintEnrollIntroUiState, boolean z) {
        int moreButtonTextRes;
        boolean z2 = (fingerprintEnrollIntroUiState.getEnrollable() && fingerprintEnrollIntroUiState.getHasScrolledToBottom()) || !getRequireScrollMixin().isScrollingRequired();
        Log.d("IntroFragment", "updateFooterButtons(" + fingerprintEnrollIntroUiState + "), showSecondaryBtn:" + z2 + ", isExpressive:" + z);
        View view = getView();
        view.getClass();
        View viewRequireViewById = view.requireViewById(R.id.error_text);
        viewRequireViewById.getClass();
        TextView textView = (TextView) viewRequireViewById;
        if (fingerprintEnrollIntroUiState.getEnrollable()) {
            textView.setText((CharSequence) null);
            textView.setVisibility(8);
        } else {
            textView.setText(R.string.fingerprint_intro_error_max);
            textView.setVisibility(0);
        }
        if (z) {
            return;
        }
        FooterButton primaryButton = getFooterBarMixin().getPrimaryButton();
        if (primaryButton != null) {
            Context context = getContext();
            if (!fingerprintEnrollIntroUiState.getEnrollable()) {
                moreButtonTextRes = R.string.done;
            } else if (z2) {
                moreButtonTextRes = R.string.security_settings_fingerprint_enroll_introduction_agree;
            } else {
                moreButtonTextRes = getMoreButtonTextRes();
            }
            primaryButton.setText(context, moreButtonTextRes);
        }
        FooterButton secondaryButton = getFooterBarMixin().getSecondaryButton();
        if (secondaryButton != null) {
            secondaryButton.setVisibility(z2 ? 0 : 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getMoreButtonTextRes() {
        return R.string.security_settings_face_enroll_introduction_more;
    }

    /* JADX INFO: compiled from: IntroFragment.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void bindView(FragmentActivity fragmentActivity, GlifLayout glifLayout, String str, PorterDuffColorFilter porterDuffColorFilter, boolean z, boolean z2, boolean z3, Supplier supplier, Supplier supplier2) {
            fragmentActivity.getClass();
            glifLayout.getClass();
            str.getClass();
            porterDuffColorFilter.getClass();
            supplier.getClass();
            supplier2.getClass();
            View viewRequireViewById = glifLayout.requireViewById(R.id.footer_learn_more);
            viewRequireViewById.getClass();
            TextView textView = (TextView) viewRequireViewById;
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(Html.fromHtml(str, 0));
            ((ImageView) glifLayout.requireViewById(R.id.icon_fingerprint)).setColorFilter(porterDuffColorFilter);
            ((ImageView) glifLayout.requireViewById(R.id.icon_device_locked)).setColorFilter(porterDuffColorFilter);
            ((ImageView) glifLayout.requireViewById(R.id.icon_trash_can)).setColorFilter(porterDuffColorFilter);
            ((ImageView) glifLayout.requireViewById(R.id.icon_info)).setColorFilter(porterDuffColorFilter);
            ((ImageView) glifLayout.requireViewById(R.id.icon_link)).setColorFilter(porterDuffColorFilter);
            glifLayout.requireViewById(com.google.android.setupdesign.R$id.sud_scroll_view).setImportantForAccessibility(1);
            ((ImageView) glifLayout.requireViewById(R$id.icon_security_privacy_safe)).setColorFilter(porterDuffColorFilter);
            ((ImageView) glifLayout.requireViewById(R$id.icon_privacy_tip)).setColorFilter(porterDuffColorFilter);
            View viewRequireViewById2 = glifLayout.requireViewById(R.id.footer_message_6);
            viewRequireViewById2.getClass();
            TextView textView2 = (TextView) viewRequireViewById2;
            View viewRequireViewById3 = glifLayout.requireViewById(R.id.icon_shield);
            viewRequireViewById3.getClass();
            ImageView imageView = (ImageView) viewRequireViewById3;
            imageView.setColorFilter(porterDuffColorFilter);
            textView2.setText(fragmentActivity.getString(R$string.security_settings_fingerprint_v2_enroll_introduction_footer_message_6_2));
            if (z) {
                textView2.setVisibility(0);
                imageView.setVisibility(0);
            } else {
                textView2.setVisibility(8);
                imageView.setVisibility(8);
            }
            GlifLayoutUseCase glifLayoutUseCase = new GlifLayoutUseCase(glifLayout);
            if (z2 && !z3) {
                glifLayoutUseCase.setHeaderText(fragmentActivity, R.string.security_settings_fingerprint_enroll_introduction_title_unlock_disabled);
                glifLayoutUseCase.setDescriptionText((CharSequence) supplier.get());
            } else {
                glifLayoutUseCase.setHeaderText(fragmentActivity, R.string.security_settings_fingerprint_enroll_introduction_title);
                glifLayoutUseCase.setDescriptionText(fragmentActivity.getString(R$string.security_settings_fingerprint_enroll_introduction_v3_message_2, new Object[]{supplier2.get()}));
            }
        }
    }
}

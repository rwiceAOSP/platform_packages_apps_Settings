package com.google.android.settings.biometrics.udfps.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.android.settings.R
import com.android.settings.biometrics.BiometricsOnboardingProto.OnboardingAction
import com.android.settings.biometrics.BiometricsOnboardingProto.OnboardingScreen
import com.google.android.settings.biometrics.fingerprint.ui.view.GlifLayoutUseCase
import com.google.android.settings.biometrics.fingerprint.ui.view.NavOptionsUseCase
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollResult
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel
import com.google.android.settings.biometrics.udfps.factory.UdfpsViewModelFactory
import com.google.android.settings.biometrics.udfps.ui.viewmodel.ConfirmUdfpsViewModel
import com.google.android.setupcompat.template.FooterBarMixin
import com.google.android.setupcompat.template.FooterButton
import com.google.android.setupdesign.GlifLayout
import com.google.android.setupdesign.util.LottieAnimationHelper
import com.google.android.setupdesign.util.ThemeHelper
import kotlinx.coroutines.launch

class ConfirmUdfpsFragment : Fragment() {

    private val viewModel: ConfirmUdfpsViewModel by
        viewModels(extrasProducer = { requireActivity().defaultViewModelCreationExtras }) {
            defaultViewModelProviderFactory
        }
    private val setEnrollResultViewModel: SetEnrollResultViewModel by activityViewModels()
    private val metricsViewModel: FingerprintMetricsViewModel by activityViewModels()

    private val useExpressStyle: Boolean by lazy {
        ThemeHelper.shouldApplyGlifExpressiveStyle(requireContext())
    }

    private val addButtonClickListener = View.OnClickListener {
        metricsViewModel.appendAction(OnboardingAction.ACTION_ADD_ANOTHER_FINGERPRINT)
        findNavController()
            .navigate(
                R.id.action_finish_to_enrolling,
                null,
                NavOptionsUseCase.newBackToEnrollNavOptions(),
            )
    }

    private val nextButtonClickListener = View.OnClickListener {
        lifecycleScope.launch {
            setEnrollResultViewModel.emit(FingerprintEnrollResult.CONFIRMATION_NEXT_BUTTON)
        }
    }

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = UdfpsViewModelFactory()

    private val glifLayout: GlifLayout
        get() = requireView() as GlifLayout

    private val glifLayoutUseCase: GlifLayoutUseCase
        get() = GlifLayoutUseCase(glifLayout)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val layoutRes =
            if (useExpressStyle) {
                R.layout.udfps_enroll_finish_expressive
            } else {
                R.layout.fingerprint_enroll_finish_base
            }
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val footer = glifLayout.getMixin(FooterBarMixin::class.java)

        lifecycleScope.launch {
            val enrolledCount = viewModel.getEnrolledFingerprints()
            val titleRes =
                if (enrolledCount == 1) {
                    R.string.fingerprint_enroll_finish_title_one_fingerprint_enrolled
                } else {
                    R.string.fingerprint_enroll_finish_title
                }
            glifLayoutUseCase.setHeaderText(requireActivity(), titleRes)

            val descStr =
                when (enrolledCount) {
                    1 ->
                        getString(
                            R.string.fingerprint_enroll_finish_subtitle_one_fingerprint_enrolled
                        )
                    2 ->
                        getString(
                            R.string.fingerprint_enroll_finish_subtitle_two_fingerprints_enrolled
                        )
                    else ->
                        getString(
                            R.string
                                .fingerprint_enroll_finish_subtitle_three_and_more_fingerprints_enrolled
                        )
                }
            glifLayoutUseCase.setDescriptionText(descStr)

            val buttonTextRes =
                if (viewModel.isSuw) {
                    R.string.next_label
                } else {
                    R.string.security_settings_fingerprint_enroll_done
                }

            footer.primaryButton =
                FooterButton.Builder(requireContext())
                    .setText(buttonTextRes)
                    .setListener(nextButtonClickListener)
                    .setButtonType(FooterButton.ButtonType.DONE)
                    .setTheme(com.google.android.setupdesign.R.style.SudGlifButton_Primary)
                    .build()

            if (viewModel.isEnrollable()) {
                footer.secondaryButton =
                    FooterButton.Builder(requireContext())
                        .setText(R.string.fingerprint_enroll_finish_footer_button_add)
                        .setListener(addButtonClickListener)
                        .setButtonType(FooterButton.ButtonType.OTHER)
                        .setTheme(com.google.android.setupdesign.R.style.SudGlifButton_Secondary)
                        .build()
            }
        }

        lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    metricsViewModel.setScreen(OnboardingScreen.SCREEN_CONFIRMATION)
                }
            }
        )

        if (useExpressStyle) {
            setupExpressiveStyleAnim(view)
        }
    }

    private fun setupExpressiveStyleAnim(view: View) {
        val lottieView =
            view.findViewById<LottieAnimationView>(R.id.fingerprint_enroll_finish_lottie) ?: return
        lottieView.setAnimation(R.raw.fingerprint_enroll_finish_expressive)
        lottieView.playAnimation()
        lottieView.visibility = View.VISIBLE
        lottieView.setOnClickListener {
            if (lottieView.isAnimating) {
                lottieView.pauseAnimation()
            } else {
                lottieView.resumeAnimation()
            }
        }
        val colors =
            requireContext().resources.getStringArray(R.array.add_fingerprint_success).toList()
        LottieAnimationHelper.get().applyColor(requireContext(), lottieView, colors)
    }
}

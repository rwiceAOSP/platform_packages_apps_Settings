package com.google.android.settings.biometrics.fingerprint.ui.view

import android.app.admin.DevicePolicyManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.settings.R
import com.android.settings.biometrics.BiometricsOnboardingProto.OnboardingAction
import com.android.settings.biometrics.BiometricsOnboardingProto.OnboardingScreen
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollIntroUiState
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintEnrollResult
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.FingerprintMetricsViewModel
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.IntroViewModel
import com.google.android.settings.biometrics.fingerprint.ui.viewmodel.SetEnrollResultViewModel
import com.google.android.setupcompat.template.FooterBarMixin
import com.google.android.setupcompat.template.FooterButton
import com.google.android.setupdesign.GlifLayout
import com.google.android.setupdesign.template.RequireScrollMixin
import com.google.android.setupdesign.util.DeviceHelper
import com.google.android.setupdesign.util.ThemeHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class IntroFragment : Fragment() {

    private val viewModel: IntroViewModel by activityViewModels()
    private val setEnrollResultViewModel: SetEnrollResultViewModel by activityViewModels()
    private val metricsViewModel: FingerprintMetricsViewModel by activityViewModels()

    private val onNextClickListener = View.OnClickListener {
        lifecycleScope.launch {
            if (viewModel.uiState.first().enrollable) {
                if (showSplitScreenDialogIfNeed()) {
                    return@launch
                }
                when (viewModel.getSensorType()) {
                    SENSOR_TYPE_UDFPS_OPTICAL,
                    SENSOR_TYPE_UDFPS_ULTRASONIC,
                    SENSOR_TYPE_POWER_BUTTON -> {
                        metricsViewModel.appendAction(OnboardingAction.ACTION_NEXT)
                        findNavController()
                            .navigate(
                                R.id.action_intro_to_find_sensor,
                                null,
                                NavOptionsUseCase.newNavOptions(),
                            )
                    }
                    else ->
                        setEnrollResultViewModel.emit(
                            FingerprintEnrollResult.INTRO_FRAGMENT_CONTINUE_ENROLL
                        )
                }
            } else {
                setEnrollResultViewModel.emit(
                    FingerprintEnrollResult.INTRO_FRAGMENT_DONE_AND_FINISH_BUTTON
                )
            }
        }
    }

    private val onSkipOrCancelClickListener = View.OnClickListener {
        lifecycleScope.launch {
            metricsViewModel.appendAction(OnboardingAction.ACTION_SKIP)
            setEnrollResultViewModel.emit(
                FingerprintEnrollResult.INTRO_FRAGMENT_SKIP_OR_CANCEL_BUTTON
            )
        }
    }

    private val footerBarMixin: FooterBarMixin
        get() = (requireView() as GlifLayout).getMixin(FooterBarMixin::class.java)

    private val requireScrollMixin: RequireScrollMixin
        get() = (requireView() as GlifLayout).getMixin(RequireScrollMixin::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fingerprint_enroll_introduction_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val glifLayout = view as GlifLayout

        lifecycleScope.launch {
            val isSfps = viewModel.getSensorType() == 2 || viewModel.getSensorType() == 3
            val disabledByAdmin = viewModel.isFingerprintUnlockDisabledByAdmin
            val parentalConsentRequired = viewModel.isParentalConsentRequired
            val colorFilter =
                PorterDuffColorFilter(
                    requireContext()
                        .getColor(
                            com.android.settingslib.widget.theme.R.color
                                .settingslib_materialColorOnSurfaceVariant
                        ),
                    PorterDuff.Mode.SRC_IN,
                )

            bindView(
                requireActivity(),
                glifLayout,
                getString(
                    R.string
                        .security_settings_fingerprint_v2_enroll_introduction_message_learn_more_2,
                    0,
                ),
                colorFilter,
                isSfps,
                disabledByAdmin,
                parentalConsentRequired,
            )

            if (ThemeHelper.shouldApplyGlifExpressiveStyle(requireContext())) {
                val illustration = view.findViewById<ImageView>(R.id.illustrationImage)
                illustration?.setImageResource(
                    R.drawable.fingerprint_enroll_introduction_expressive
                )
            }
        }

        viewLifecycleOwner.lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    metricsViewModel.setScreen(OnboardingScreen.SCREEN_INTRO)
                    initPrimaryFooterButton()
                    initSecondaryFooterButton()
                    collectPageStatusFlowIfNeed()
                    showSplitScreenDialogIfNeed()
                }
            }
        )
    }

    private fun initPrimaryFooterButton() {
        if (footerBarMixin.primaryButton != null) return
        val button =
            FooterButton.Builder(requireContext())
                .setText(R.string.security_settings_fingerprint_enroll_introduction_agree)
                .setButtonType(FooterButton.ButtonType.NEXT)
                .setTheme(com.google.android.setupdesign.R.style.SudGlifButton_Primary)
                .build()
        button.setOnClickListener(onNextClickListener)
        footerBarMixin.primaryButton = button
    }

    private fun initSecondaryFooterButton() {
        if (footerBarMixin.secondaryButton != null) return
        val button =
            FooterButton.Builder(requireContext())
                .setText(R.string.security_settings_fingerprint_enroll_introduction_no_thanks)
                .setButtonType(FooterButton.ButtonType.CANCEL)
                .setTheme(com.google.android.setupdesign.R.style.SudGlifButton_Primary)
                .build()
        button.setOnClickListener(onSkipOrCancelClickListener)
        footerBarMixin.setSecondaryButton(button, true)
    }

    private fun collectPageStatusFlowIfNeed() {
        lifecycleScope.launch {
            var hasRequireScrollWithButton = requireScrollMixin.isScrollingRequired
            viewModel.uiState.collect { uiState ->
                val expressive = ThemeHelper.shouldApplyGlifExpressiveStyle(requireContext())
                if (!hasRequireScrollWithButton && !uiState.hasScrolledToBottom) {
                    val primary = footerBarMixin.primaryButton
                    val secondary = footerBarMixin.secondaryButton
                    if (primary != null && secondary != null) {
                        requireScrollMixin.requireScrollWithButton(
                            requireActivity(),
                            primary,
                            secondary,
                            getMoreButtonTextRes(),
                            onNextClickListener,
                        )
                        if (!expressive) {
                            requireScrollMixin.setOnRequireScrollStateChangedListener { scrollNeeded
                                ->
                                if (!scrollNeeded) {
                                    lifecycleScope.launch { viewModel.onScrollToBottom() }
                                }
                                updateFooterButtons(uiState, false)
                            }
                        }
                        hasRequireScrollWithButton = true
                    }
                }
                updateFooterButtons(uiState, expressive)
            }
        }
    }

    private fun updateFooterButtons(uiState: FingerprintEnrollIntroUiState, isExpressive: Boolean) {
        val showSecondary =
            (uiState.enrollable && uiState.hasScrolledToBottom) ||
                !requireScrollMixin.isScrollingRequired
        val errorText = requireView().findViewById<TextView>(R.id.error_text)
        if (uiState.enrollable) {
            errorText?.text = null
            errorText?.visibility = View.GONE
        } else {
            errorText?.setText(R.string.fingerprint_intro_error_max)
            errorText?.visibility = View.VISIBLE
        }
        if (isExpressive) return

        footerBarMixin.primaryButton?.let { primary ->
            val textRes =
                when {
                    !uiState.enrollable -> R.string.done
                    showSecondary ->
                        R.string.security_settings_fingerprint_enroll_introduction_agree
                    else -> getMoreButtonTextRes()
                }
            primary.setText(requireContext(), textRes)
        }
        footerBarMixin.secondaryButton?.visibility =
            if (showSecondary) View.VISIBLE else View.INVISIBLE
    }

    private fun getMoreButtonTextRes(): Int {
        return R.string.security_settings_face_enroll_introduction_more
    }

    private fun showSplitScreenDialogIfNeed(): Boolean {
        val activity = activity ?: return false
        val companion = SplitScreenDialog.Companion
        val childFm = childFragmentManager
        companion.dismissExistingDialog(childFm)
        if (!companion.shouldShowDialog(activity)) {
            return false
        }
        companion.showDialog(childFm)
        if (viewModel.request.isSuw) {
            return true
        }
        childFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
                    if (f is SplitScreenDialog) {
                        lifecycleScope.launch {
                            setEnrollResultViewModel.emit(
                                FingerprintEnrollResult.SPLIT_DIALOG_DISMISS
                            )
                        }
                    }
                }
            },
            false,
        )
        return true
    }

    companion object {
        const val TAG = "IntroFragment"
        private const val SENSOR_TYPE_UDFPS_OPTICAL = 2
        private const val SENSOR_TYPE_UDFPS_ULTRASONIC = 3
        private const val SENSOR_TYPE_POWER_BUTTON = 4

        fun bindView(
            activity: FragmentActivity,
            glifLayout: GlifLayout,
            learnMoreText: String,
            colorFilter: PorterDuffColorFilter,
            isSfps: Boolean,
            disabledByAdmin: Boolean,
            parentalConsentRequired: Boolean,
        ) {
            val learnMoreView = glifLayout.requireViewById<TextView>(R.id.footer_learn_more)
            learnMoreView.movementMethod = LinkMovementMethod.getInstance()
            learnMoreView.text = Html.fromHtml(learnMoreText, 0)

            glifLayout.requireViewById<ImageView>(R.id.icon_fingerprint).colorFilter = colorFilter
            glifLayout.requireViewById<ImageView>(R.id.icon_device_locked).colorFilter = colorFilter
            glifLayout.requireViewById<ImageView>(R.id.icon_trash_can).colorFilter = colorFilter
            glifLayout.requireViewById<ImageView>(R.id.icon_info).colorFilter = colorFilter
            glifLayout.requireViewById<ImageView>(R.id.icon_link).colorFilter = colorFilter
            glifLayout
                .requireViewById<View>(com.google.android.setupdesign.R.id.sud_scroll_view)
                .importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
            glifLayout.requireViewById<ImageView>(R.id.icon_security_privacy_safe).colorFilter =
                colorFilter
            glifLayout.requireViewById<ImageView>(R.id.icon_privacy_tip).colorFilter = colorFilter

            val footer6 = glifLayout.requireViewById<TextView>(R.id.footer_message_6)
            val iconShield = glifLayout.requireViewById<ImageView>(R.id.icon_shield)
            iconShield.colorFilter = colorFilter
            footer6.text =
                activity.getString(
                    R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_6_2
                )
            if (isSfps) {
                footer6.visibility = View.VISIBLE
                iconShield.visibility = View.VISIBLE
            } else {
                footer6.visibility = View.GONE
                iconShield.visibility = View.GONE
            }

            val glifLayoutUseCase = GlifLayoutUseCase(glifLayout)
            if (disabledByAdmin && !parentalConsentRequired) {
                glifLayoutUseCase.setHeaderText(
                    activity,
                    R.string.security_settings_fingerprint_enroll_introduction_title_unlock_disabled,
                )
                val dpm = activity.getSystemService(DevicePolicyManager::class.java)
                val adminMsg =
                    dpm?.resources?.getString("Settings.FINGERPRINT_UNLOCK_DISABLED") {
                        activity.getString(
                            R.string
                                .security_settings_fingerprint_enroll_introduction_message_unlock_disabled
                        )
                    }
                        ?: activity.getString(
                            R.string
                                .security_settings_fingerprint_enroll_introduction_message_unlock_disabled
                        )
                glifLayoutUseCase.setDescriptionText(adminMsg)
            } else {
                glifLayoutUseCase.setHeaderText(
                    activity,
                    R.string.security_settings_fingerprint_enroll_introduction_title,
                )
                val deviceName = DeviceHelper.getDeviceName(activity)
                glifLayoutUseCase.setDescriptionText(
                    activity.getString(
                        R.string.security_settings_fingerprint_enroll_introduction_v3_message_2,
                        deviceName,
                    )
                )
            }
        }
    }
}

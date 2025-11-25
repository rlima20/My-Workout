package com.example.onboarding.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.onboarding.domain.model.OnboardingPage
import com.example.onboarding.ui.Components.PagerWithCustomIndicator
import com.example.onboarding.viewmodel.OnboardingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingActivity : ComponentActivity() {
    private val onboardingViewModel: OnboardingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pages: ArrayList<OnboardingPage>? = intent.getParcelableArrayListExtra(EXTRA_PAGES)
        val showSkip = intent.getBooleanExtra(EXTRA_SHOW_SKIP, true)
        val finishButtonText = intent.getStringExtra(EXTRA_FINISH_TEXT)

        pages?.let {
            setContent {
                PagerWithCustomIndicator(
                    pages = pages,
                    onFinished = { this.finish() },
                    onNextPage = {  }
                )
            }
        }
    }

    companion object {
        private const val EXTRA_PAGES = "extra_onboarding_pages"
        private const val EXTRA_SHOW_SKIP = "extra_onboarding_show_skip"
        private const val EXTRA_FINISH_TEXT = "extra_onboarding_finish_text"

        fun createIntent(
            context: Context,
            pages: List<OnboardingPage>,
            showSkip: Boolean = true,
            finishButtonText: String? = null
        ): Intent {
            return Intent(context, OnboardingActivity::class.java).apply {
                putParcelableArrayListExtra(EXTRA_PAGES, ArrayList(pages))
                putExtra(EXTRA_SHOW_SKIP, showSkip)
                finishButtonText?.let { putExtra(EXTRA_FINISH_TEXT, it) }
            }
        }
    }
}
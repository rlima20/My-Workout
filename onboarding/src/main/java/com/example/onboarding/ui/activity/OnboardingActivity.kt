package com.example.onboarding.ui.activity

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import com.example.onboarding.domain.data.OnboardingPrefs
import com.example.onboarding.domain.model.OnboardingPage
import com.example.onboarding.ui.Components.PagerWithCustomIndicator

class OnboardingActivity : ComponentActivity() {

    private val prefs = OnboardingPrefs(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pages: ArrayList<OnboardingPage>? = intent.getParcelableArrayListExtra(EXTRA_PAGES)
        val skipButtonText = intent.getStringExtra(EXTRA_SKIP_BUTTON_TEXT) as String
        val nextButtonText = intent.getSerializableExtra(EXTRA_NEXT_BUTTON) as Pair<String, String>
        val showSkipButton = intent.getBooleanExtra(EXTRA_SHOW_SKIP, true)
        val finishPendingIntent = intent.getParcelableExtra<PendingIntent>(EXTRA_FINISH_PENDING_INTENT)

        pages?.let {
            setContent {
                PagerWithCustomIndicator(
                    pages = pages,
                    modifier = Modifier,
                    nextButtonText = nextButtonText,
                    skipButtonText = skipButtonText,
                    showSkipButton = showSkipButton,
                    onFinished = {
                        completeOnboarding(prefs)
                        finishAndOpenMainActivity(finishPendingIntent)
                    },
                )
            }
        }
    }

    companion object {
        private const val EXTRA_PAGES = "extra_onboarding_pages"
        private const val EXTRA_SHOW_SKIP = "extra_onboarding_show_skip"
        private const val EXTRA_FINISH_TEXT = "extra_onboarding_finish_text"
        private const val EXTRA_NEXT_BUTTON = "extra_onboarding_next_button"
        private const val EXTRA_SKIP_BUTTON_TEXT = "extra_onboarding_skip_button_text"
        private const val EXTRA_FINISH_PENDING_INTENT = "extra_onboarding_finish_pending_intent"

        fun createIntent(
            context: Context,
            pages: List<OnboardingPage>,
            skipButtonText: String,
            showSkipButton: Boolean = true,
            finishButtonText: String,
            nextButtonText: Pair<String, String>,
            finishPendingIntent: PendingIntent?
        ): Intent {
            return Intent(context, OnboardingActivity::class.java).apply {
                putParcelableArrayListExtra(EXTRA_PAGES, ArrayList(pages))
                putExtra(EXTRA_FINISH_PENDING_INTENT, finishPendingIntent)
                putExtra(EXTRA_SHOW_SKIP, showSkipButton)
                putExtra(EXTRA_FINISH_TEXT, finishButtonText)
                putExtra(EXTRA_NEXT_BUTTON, nextButtonText)
                putExtra(EXTRA_SKIP_BUTTON_TEXT, skipButtonText)
            }
        }
    }
}

private fun OnboardingActivity.finishAndOpenMainActivity(finishPendingIntent: PendingIntent?) {
    finishPendingIntent?.send()
    finish()
}

private fun completeOnboarding(prefs: OnboardingPrefs) {
    prefs.setCompleted(true)
}
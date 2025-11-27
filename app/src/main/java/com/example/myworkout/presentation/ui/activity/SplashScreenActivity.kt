package com.example.myworkout.presentation.ui.activity

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.presentation.ui.components.commons.SplashComponent
import com.example.onboarding.domain.data.OnboardingPrefs
import com.example.onboarding.ui.activity.OnboardingActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alreadySeen = OnboardingPrefs(this@SplashScreenActivity).isCompleted()

        // Usar esse comando para ver a tela de OnboardingNovamente
        OnboardingPrefs(this@SplashScreenActivity).setCompleted(false)

        val finishIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        lifecycleScope.launch {
            delay(3000)

            if (alreadySeen) {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            } else {
                startActivity(
                    OnboardingActivity.createIntent(
                        context = this@SplashScreenActivity,
                        pages = Constants().onboardingPages,
                        showSkipButton = true,
                        finishButtonText = getString(R.string.start),
                        nextButtonText = Pair(
                            this@SplashScreenActivity.getString(R.string.start),
                            this@SplashScreenActivity.getString(R.string.next)
                        ),
                        skipButtonText = this@SplashScreenActivity.getString(R.string.skip),
                        finishPendingIntent = finishIntent
                    )
                )
            }
            finish()
        }

        setContent {
            SplashComponent()
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashComponent()
}
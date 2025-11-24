package com.example.onboarding.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.onboarding.onboardingPages
import com.example.onboarding.ui.Components.PagerWithCustomIndicator
import com.example.onboarding.viewmodel.OnboardingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val onboardingViewModel: OnboardingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PagerWithCustomIndicator(
                pages = onboardingPages,
                onFinished = { },
                onNextPage = { }
            )
        }
    }
}
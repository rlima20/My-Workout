package com.example.onboarding.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.onboarding.domain.data.OnboardingPrefs
import com.example.onboarding.viewmodel.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingPrefs = module {
    factory<OnboardingPrefs> { OnboardingPrefs(get()) }
}

@RequiresApi(Build.VERSION_CODES.O)
val onboardingViewModelDI = module {
    viewModel { OnboardingViewModel(get()) }
}

@RequiresApi(Build.VERSION_CODES.O)
val appModules = listOf(
    onboardingViewModelDI,
    onboardingPrefs
)

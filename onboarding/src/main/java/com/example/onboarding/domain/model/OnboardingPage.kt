package com.example.onboarding.domain.model

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int? = null
)
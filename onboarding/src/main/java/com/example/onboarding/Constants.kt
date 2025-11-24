package com.example.onboarding

import com.example.onboarding.domain.model.OnboardingPage

val onboardingPages = listOf(
    OnboardingPage(
        image = R.drawable.ic_launcher_background,
        title = "Bem-vindo!",
        description = "Conheça as funcionalidades incríveis do nosso app."
    ),
    OnboardingPage(
        image = R.drawable.ic_launcher_background,
        title = "Organize tudo",
        description = "Tenha controle do seu progresso de forma simples."
    ),
    OnboardingPage(
        image = R.drawable.ic_launcher_background,
        title = "Comece agora",
        description = "Vamos iniciar sua jornada!"
    )
)
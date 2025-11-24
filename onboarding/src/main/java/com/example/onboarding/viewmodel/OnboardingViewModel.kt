package com.example.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding.domain.data.OnboardingPrefs
import com.example.onboarding.domain.model.OnboardingPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(private val prefs: OnboardingPrefs) : ViewModel() {

    private val _pages = MutableStateFlow<List<OnboardingPage>>(emptyList())
    val pages: StateFlow<List<OnboardingPage>> = _pages

    private val _showOnboarding = MutableStateFlow(true)
    val showOnboarding: StateFlow<Boolean> = _showOnboarding

    init {
        _pages.value = loadPages()
        viewModelScope.launch {
            _showOnboarding.value = prefs.isCompleted()
        }
    }

    private fun loadPages(): List<OnboardingPage> = listOf(
        OnboardingPage(
            title = "Organize seus treinos",
            description = "Crie e personalize sua rotina.",
            imageRes = null
        ),
        OnboardingPage(
            title = "Acompanhe sua evolução",
            description = "Veja seu progresso ao longo dos dias.",
            imageRes = null
        ),
        OnboardingPage(
            title = "Tudo no mesmo lugar",
            description = "Informações centralizadas para você.",
            imageRes = null
        )
    )

    fun completeOnboarding() {
        viewModelScope.launch {
            prefs.setCompleted(true)
            _showOnboarding.value = false
        }
    }
}
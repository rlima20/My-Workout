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
        // _pages.value = loadPages()
        viewModelScope.launch {
            _showOnboarding.value = prefs.isCompleted()
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            prefs.setCompleted(true)
            _showOnboarding.value = false
        }
    }
}
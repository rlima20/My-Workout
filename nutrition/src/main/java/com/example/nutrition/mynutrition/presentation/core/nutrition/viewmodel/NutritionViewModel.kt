package com.example.nutrition.mynutrition.presentation.core.nutrition.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NutritionViewModel : ViewModel() {
    private val _showNutritionCard = MutableStateFlow(false)
    val showNutritionCard: StateFlow<Boolean> = _showNutritionCard

    fun setShowNutritionCard(value: Boolean) {
        _showNutritionCard.value = value
    }
}
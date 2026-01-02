package com.example.nutrition.mynutrition.presentation.nutrition

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private val _showNutritionCard = MutableStateFlow(false)
val showNutritionCard: StateFlow<Boolean> = _showNutritionCard

fun setShowNutritionCard(value: Boolean){
    _showNutritionCard.value = value
}
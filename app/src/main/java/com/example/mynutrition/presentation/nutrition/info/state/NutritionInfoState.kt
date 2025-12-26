package com.example.mynutrition.presentation.nutrition.info.state

import com.example.mynutrition.domain.model.enums.ActivityLevel
import com.example.mynutrition.domain.model.enums.Sex

data class NutritionInfoState(
    val name: String = "",
    val age: String = "",
    val sex: Sex = Sex.MALE,
    val height: String = "",
    val weight: String = "",
    val activity: ActivityLevel = ActivityLevel.MODERATE,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val success: Boolean = false
)
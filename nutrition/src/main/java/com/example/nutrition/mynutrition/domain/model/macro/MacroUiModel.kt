package com.example.nutrition.mynutrition.domain.model.macro

import com.example.nutrition.mynutrition.domain.model.enums.MacroType


data class MacroUiModel(
    val name: String,
    val type: MacroType,
    val kcal: Int,
    val grams: Int
)
package com.example.mynutrition.domain.model.macro

import com.example.mynutrition.domain.model.enums.MacroType

data class MacroUiModel(
    val name: String,
    val type: MacroType,
    val kcal: Int
)
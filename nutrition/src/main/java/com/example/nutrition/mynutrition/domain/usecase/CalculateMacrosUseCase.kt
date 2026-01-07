package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.macro.MacroResult

interface CalculateMacrosUseCase {
    fun calculateMacros(totalKcal: Int): MacroResult
}
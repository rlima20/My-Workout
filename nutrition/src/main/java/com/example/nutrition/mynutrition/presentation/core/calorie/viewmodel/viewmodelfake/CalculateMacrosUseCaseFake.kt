package com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.viewmodelfake

import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.model.macro.MacroResult
import com.example.nutrition.mynutrition.domain.usecase.CalculateMacrosUseCase

class CalculateMacrosUseCaseFake(): CalculateMacrosUseCase{
    override fun calculateMacros(
        totalKcal: Int,
        goalType: CalorieGoalType
    ): MacroResult {
        TODO("Not yet implemented")
    }
}
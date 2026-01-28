package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.model.macro.MacroResult
import kotlin.math.roundToInt

data class MacroDistribution(
    val carbs: Double,
    val proteins: Double,
    val fats: Double
)

class CalculateMacrosUseCaseImpl : CalculateMacrosUseCase {

    override fun calculateMacros(
        totalKcal: Int,
        goalType: CalorieGoalType
    ): MacroResult {

        val distribution = macroDistributionByGoal(goalType)

        val carbsKcal = (totalKcal * distribution.carbs).roundToInt()
        val proteinsKcal = (totalKcal * distribution.proteins).roundToInt()
        val fatsKcal = (totalKcal * distribution.fats).roundToInt()

        val fibersGrams = (totalKcal / 1000.0 * 14).roundToInt()

        return MacroResult(
            macroResultId = 1,
            carbsGrams = carbsKcal / 4,
            proteinsGrams = proteinsKcal / 4,
            fatsGrams = fatsKcal / 9,
            fibersGrams = fibersGrams,

            carbsKcal = carbsKcal,
            proteinsKcal = proteinsKcal,
            fatsKcal = fatsKcal,
        )
    }

    private fun macroDistributionByGoal(
        goalType: CalorieGoalType
    ): MacroDistribution = when (goalType) {

        CalorieGoalType.LOSE -> MacroDistribution(
            carbs = 0.40,
            proteins = 0.30,
            fats = 0.30
        )

        CalorieGoalType.MAINTAIN -> MacroDistribution(
            carbs = 0.50,
            proteins = 0.25,
            fats = 0.25
        )

        CalorieGoalType.GAIN -> MacroDistribution(
            carbs = 0.55,
            proteins = 0.25,
            fats = 0.20
        )
    }
}
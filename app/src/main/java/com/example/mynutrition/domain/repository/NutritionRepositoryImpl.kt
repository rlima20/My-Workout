package com.example.mynutrition.domain.repository

import com.example.mynutrition.domain.model.MacroResult
import com.example.mynutrition.domain.model.UserInfo
import com.example.mynutrition.domain.model.enums.ActivityLevel
import com.example.mynutrition.domain.model.enums.CalorieGoalType
import com.example.mynutrition.domain.model.enums.Sex
import kotlin.math.roundToInt

class NutritionRepositoryImpl : NutritionRepository {
    override fun calculateTmb(info: UserInfo): Int {
        val base = if (info.sex == Sex.MALE) {
            (10 * info.weightKg) + (6.25 * info.heightCm) - (5 * info.age) + 5
        } else {
            (10 * info.weightKg) + (6.25 * info.heightCm) - (5 * info.age) - 161
        }
        return base.roundToInt()
    }

    override fun calculateCalorieGoal(tmb: Int, activity: ActivityLevel, goal: CalorieGoalType): Int {
        val maintenance = (tmb * activity.factor).roundToInt()
        return when (goal) {
            CalorieGoalType.GAIN -> maintenance + 500
            CalorieGoalType.MAINTAIN -> maintenance
            CalorieGoalType.LOSE -> maintenance - 500
        }
    }

    override fun calculateMacros(totalKcal: Int): MacroResult {
        val carbsKcal = (totalKcal * 0.50).roundToInt()
        val proteinsKcal = (totalKcal * 0.25).roundToInt()
        val fatsKcal = (totalKcal * 0.25).roundToInt()
        val fibersGrams = (totalKcal / 1000.0 * 14).roundToInt()

        val carbsGrams = carbsKcal / 4
        val proteinsGrams = proteinsKcal / 4
        val fatsGrams = fatsKcal / 9
        val fibersKcal = fibersGrams * 2 // aproximação (2 kcal/g de fibra como placeholder)

        return MacroResult(
            carbsGrams = carbsGrams,
            proteinsGrams = proteinsGrams,
            fatsGrams = fatsGrams,
            fibersGrams = fibersGrams,
            carbsKcal = carbsKcal,
            proteinsKcal = proteinsKcal,
            fatsKcal = fatsKcal,
            fibersKcal = fibersKcal
        )
    }
}
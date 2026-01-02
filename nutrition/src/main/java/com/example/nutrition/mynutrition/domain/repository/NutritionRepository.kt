package com.example.nutrition.mynutrition.domain.repository

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.model.macro.MacroResult
import com.example.nutrition.mynutrition.domain.model.user.UserInfo

interface NutritionRepository {
    fun calculateTmb(info: UserInfo): Int
    fun calculateCalorieGoal(tmb: Int, activity: ActivityLevel, goal: CalorieGoalType): Int
    fun calculateMacros(totalKcal: Int): MacroResult

}
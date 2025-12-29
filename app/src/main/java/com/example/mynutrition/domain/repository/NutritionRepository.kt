package com.example.mynutrition.domain.repository

import com.example.mynutrition.domain.model.macro.MacroResult
import com.example.mynutrition.domain.model.user.UserInfo
import com.example.mynutrition.domain.model.enums.ActivityLevel
import com.example.mynutrition.domain.model.enums.CalorieGoalType

interface NutritionRepository {
    fun calculateTmb(info: UserInfo): Int
    fun calculateCalorieGoal(tmb: Int, activity: ActivityLevel, goal: CalorieGoalType): Int
    fun calculateMacros(totalKcal: Int): MacroResult
}
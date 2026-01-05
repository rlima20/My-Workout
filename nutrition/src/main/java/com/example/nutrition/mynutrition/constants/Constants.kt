package com.example.nutrition.mynutrition.constants

import com.example.nutrition.mynutrition.domain.model.macro.MacroResult
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult

fun getNutritionResult(): NutritionResult =
    NutritionResult(
        tmb = 2000,
        maintenanceCalories = 2000,
        calorieGoal = 2500,
        macros =
            MacroResult(
                100,
                100,
                100,
                100,
                100,
                100,
                100,
                100
            )
    )
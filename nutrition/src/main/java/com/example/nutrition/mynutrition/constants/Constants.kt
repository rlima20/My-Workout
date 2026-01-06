package com.example.nutrition.mynutrition.constants

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.Sex
import com.example.nutrition.mynutrition.domain.model.macro.MacroResult
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.presentation.info.state.NutritionInfoState

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

fun getNutritionInfoState(): NutritionInfoState =
    NutritionInfoState(
        name = "Raphael",
        age = "31",
        sex = Sex.MALE,
        height = "1,73",
        weight = "83",
        activity = ActivityLevel.HIGH,
        isEditing = false,
        isLoading = false,
        success = true,
        error = ""
    )
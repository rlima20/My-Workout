package com.example.nutrition.mynutrition.constants

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.Sex
import com.example.nutrition.mynutrition.domain.model.macro.MacroResult
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.state.UserInfoState

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

fun getMacros(): MacroResult =
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

fun getUserInfoState(): UserInfoState =
    UserInfoState(
        name = "Raphael",
        age = "31",
        sex = Sex.MALE,
        height = "1,73",
        weight = "83",
        activity = ActivityLevel.HIGH
    )

fun getUserInfo(): UserInfo =
    UserInfo(
        name = "Raphael",
        age = 31,
        sex = Sex.MALE,
        heightCm = 173,
        weightKg = 83F,
        activityLevel = ActivityLevel.HIGH
    )
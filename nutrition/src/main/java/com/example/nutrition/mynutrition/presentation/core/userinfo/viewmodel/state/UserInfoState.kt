package com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.state

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.model.enums.Sex

data class UserInfoState(
    val name: String = "",
    val age: String = "",
    val sex: Sex = Sex.MALE,
    val height: String = "",
    val weight: String = "",
    val activity: ActivityLevel = ActivityLevel.MODERATE,
    val goalType: CalorieGoalType = CalorieGoalType.MAINTAIN,
)
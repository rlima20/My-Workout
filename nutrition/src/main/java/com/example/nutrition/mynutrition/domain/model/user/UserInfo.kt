package com.example.nutrition.mynutrition.domain.model.user

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.Sex

data class UserInfo(
    val name: String,
    val age: Int,
    val sex: Sex,
    val heightCm: Int,
    val weightKg: Float,
    val activityLevel: ActivityLevel
)
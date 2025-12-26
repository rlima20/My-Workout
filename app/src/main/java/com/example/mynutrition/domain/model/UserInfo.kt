package com.example.mynutrition.domain.model

import com.example.mynutrition.domain.model.enums.ActivityLevel
import com.example.mynutrition.domain.model.enums.Sex

data class UserInfo(
    val name: String,
    val age: Int,
    val sex: Sex,
    val heightCm: Int,
    val weightKg: Float,
    val activityLevel: ActivityLevel
)
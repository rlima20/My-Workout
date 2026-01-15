package com.example.nutrition.mynutrition.domain.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.model.enums.Sex

@Entity(tableName = "user_info")
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val age: Int,
    val sex: Sex,
    val heightCm: Int,
    val weightKg: Float,
    val activityLevel: ActivityLevel,
    val goalType: CalorieGoalType = CalorieGoalType.MAINTAIN
)

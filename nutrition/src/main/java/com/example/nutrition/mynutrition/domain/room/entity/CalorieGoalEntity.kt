package com.example.nutrition.mynutrition.domain.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "calorie_goal",
    foreignKeys = [
        ForeignKey(
            entity = MacroResultEntity::class,
            parentColumns = ["macrosId"],
            childColumns = ["macrosId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CalorieGoalEntity(
    @PrimaryKey(autoGenerate = true)
    val calorieGoalId: Int = 0,
    val tmb: Int = 0,
    val calorieGoal: Int = 0,
    val macrosId: Int? = null
)
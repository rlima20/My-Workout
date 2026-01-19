package com.example.nutrition.mynutrition.domain.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CalorieGoalWithMacros(
    @Embedded
    val calorieGoal: CalorieGoalEntity,

    @Relation(
        parentColumn = "macrosId",
        entityColumn = "macrosId"
    )
    val macros: MacroResultEntity?
)



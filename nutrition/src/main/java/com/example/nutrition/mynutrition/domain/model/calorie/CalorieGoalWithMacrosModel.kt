package com.example.nutrition.mynutrition.domain.model.calorie

import androidx.room.Embedded
import androidx.room.Relation
import com.example.nutrition.mynutrition.domain.model.macro.MacroResult

data class CalorieGoalWithMacrosModel(
    @Embedded
    val calorieGoal: CalorieGoal,

    @Relation(
        parentColumn = "macrosId",
        entityColumn = "macrosId"
    )
    val macros: MacroResult?
)
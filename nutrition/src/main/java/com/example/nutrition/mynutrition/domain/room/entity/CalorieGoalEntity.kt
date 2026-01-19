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


//@Entity(tableName = "calorie_goal")
//data class CalorieGoalEntity(
//    @PrimaryKey(autoGenerate = true)
//    val calorieGoalId: Int,
//    val tmb: Int = 0,
//    val calorieGoal: Int = 0,
//    val macros: MacroResultEntity? = null,
//)

// Todo - Criar modelo e Entity de MacroResult - ok
// Todo - Criar relacionamento simples entre CalorieGoalEntitty e MacroResult Entity
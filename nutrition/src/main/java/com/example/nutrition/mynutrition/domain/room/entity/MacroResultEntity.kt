package com.example.nutrition.mynutrition.domain.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "macro_result")
data class MacroResultEntity(
    @PrimaryKey(autoGenerate = true)
    val macrosId: Int,
    val carbsGrams: Int,
    val carbsKcal: Int,
    val proteinsGrams: Int,
    val proteinsKcal: Int,
    val fatsGrams: Int,
    val fatsKcal: Int,
    val fibersGrams: Int,
    val fibersKcal: Int
)
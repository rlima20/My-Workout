package com.example.nutrition.mynutrition.domain.model.macro

data class MacroResult(
    val macroResultId: Int,
    val carbsGrams: Int,
    val carbsKcal: Int,
    val proteinsGrams: Int,
    val proteinsKcal: Int,
    val fatsGrams: Int,
    val fatsKcal: Int,
    val fibersGrams: Int,
    val fibersKcal: Int
)
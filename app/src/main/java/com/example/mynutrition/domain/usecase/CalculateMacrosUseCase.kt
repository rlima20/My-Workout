package com.example.mynutrition.domain.usecase

import com.example.mynutrition.domain.model.MacroResult

class CalculateMacrosUseCase {

    operator fun invoke(totalKcal: Int): MacroResult {
        val carbsKcal = (totalKcal * 0.50).toInt()
        val proteinsKcal = (totalKcal * 0.25).toInt()
        val fatsKcal = (totalKcal * 0.25).toInt()
        val fibersGrams = (totalKcal / 1000.0 * 14).toInt()

        return MacroResult(
            carbsGrams = carbsKcal / 4,
            proteinsGrams = proteinsKcal / 4,
            fatsGrams = fatsKcal / 9,
            fibersGrams = fibersGrams,
            carbsKcal = carbsKcal,
            proteinsKcal = proteinsKcal,
            fatsKcal = fatsKcal,
            fibersKcal = fibersGrams * 2
        )
    }
}
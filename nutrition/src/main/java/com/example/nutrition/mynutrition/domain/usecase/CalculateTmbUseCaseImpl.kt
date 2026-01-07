package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.enums.Sex
import com.example.nutrition.mynutrition.domain.model.user.UserInfo

class CalculateTmbUseCaseImpl : CalculateTmbUseCase {
    override fun calculateTmb(info: UserInfo): Int {
        return if (info.sex == Sex.MALE) {
            (10 * info.weightKg) + (6.25 * info.heightCm) - (5 * info.age) + 5
        } else {
            (10 * info.weightKg) + (6.25 * info.heightCm) - (5 * info.age) - 161
        }.toInt()
    }
}
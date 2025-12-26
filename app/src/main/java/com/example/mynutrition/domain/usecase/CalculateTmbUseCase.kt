package com.example.mynutrition.domain.usecase

import com.example.mynutrition.domain.model.UserInfo
import com.example.mynutrition.domain.model.enums.Sex

class CalculateTmbUseCase {
    operator fun invoke(info: UserInfo): Int {
        return if (info.sex == Sex.MALE) {
            (10 * info.weightKg) + (6.25 * info.heightCm) - (5 * info.age) + 5
        } else {
            (10 * info.weightKg) + (6.25 * info.heightCm) - (5 * info.age) - 161
        }.toInt()
    }
}
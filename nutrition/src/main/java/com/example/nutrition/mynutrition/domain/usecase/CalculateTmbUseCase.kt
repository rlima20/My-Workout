package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.user.UserInfo

interface CalculateTmbUseCase {
    fun calculateTmb(info: UserInfo): Int
}
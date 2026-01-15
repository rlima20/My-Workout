package com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.viewmodelfake

import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.usecase.CalculateTmbUseCase

class CalculateTmbUseCaseFake(): CalculateTmbUseCase{
    override fun calculateTmb(info: UserInfo): Int {
        TODO("Not yet implemented")
    }
}
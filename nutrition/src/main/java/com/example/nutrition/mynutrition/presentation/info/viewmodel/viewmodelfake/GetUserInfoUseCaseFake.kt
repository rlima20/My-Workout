package com.example.nutrition.mynutrition.presentation.info.viewmodel.viewmodelfake

import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.usecase.GetUserInfoUseCase

class UserInfo

class GetUserInfoUseCaseFake(): GetUserInfoUseCase{
    override suspend fun getUserInfo(): UserInfo? {
        TODO("Not yet implemented")
    }
}
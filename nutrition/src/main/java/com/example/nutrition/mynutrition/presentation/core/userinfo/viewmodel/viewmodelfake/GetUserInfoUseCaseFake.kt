package com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.viewmodelfake

import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.usecase.GetUserInfoUseCase

class GetUserInfoUseCaseFake(): GetUserInfoUseCase{
    override suspend fun getUserInfo(): UserInfo? {
        TODO("Not yet implemented")
    }
}
package com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.viewmodelfake

import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.usecase.SaveUserInfoUseCase

class SaveUserInfoUseCaseFake() : SaveUserInfoUseCase {
    override suspend fun saveUser(info: UserInfo) {
        TODO("Not yet implemented")
    }
}
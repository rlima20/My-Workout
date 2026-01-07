package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.user.UserInfo

interface SaveUserInfoUseCase {
    suspend fun saveUser(info: UserInfo)
}

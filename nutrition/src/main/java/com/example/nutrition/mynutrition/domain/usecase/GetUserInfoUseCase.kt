package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.user.UserInfo

interface GetUserInfoUseCase {
    suspend fun getUserInfo(): UserInfo?
}
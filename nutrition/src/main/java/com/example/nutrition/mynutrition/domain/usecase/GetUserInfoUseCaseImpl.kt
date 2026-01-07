package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.repository.UserInfoRepository

class GetUserInfoUseCaseImpl(private val repo: UserInfoRepository) : GetUserInfoUseCase {
    override suspend fun getUserInfo() = repo.getUserInfo()
}
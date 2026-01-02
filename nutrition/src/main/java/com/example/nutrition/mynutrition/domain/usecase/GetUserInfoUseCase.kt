package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.repository.UserInfoRepository

class GetUserInfoUseCase(private val repo: UserInfoRepository) {
    suspend operator fun invoke() = repo.getUserInfo()
}
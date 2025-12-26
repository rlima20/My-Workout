package com.example.mynutrition.domain.usecase

import com.example.mynutrition.domain.repository.UserInfoRepository

class GetUserInfoUseCase(private val repo: UserInfoRepository) {
    suspend operator fun invoke() = repo.getUserInfo()
}
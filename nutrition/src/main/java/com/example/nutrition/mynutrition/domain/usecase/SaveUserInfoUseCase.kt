package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.repository.UserInfoRepository

class SaveUserInfoUseCase(private val repo: UserInfoRepository) {
    suspend operator fun invoke(info: UserInfo) = repo.saveUserInfo(info)
}


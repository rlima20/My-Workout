package com.example.mynutrition.domain.usecase

import com.example.mynutrition.domain.model.UserInfo
import com.example.mynutrition.domain.repository.UserInfoRepository

class SaveUserInfoUseCase(private val repo: UserInfoRepository) {
    suspend operator fun invoke(info: UserInfo) = repo.saveUserInfo(info)
}


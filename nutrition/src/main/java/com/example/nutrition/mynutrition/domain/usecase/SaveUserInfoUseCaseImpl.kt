package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.repository.UserInfoRepository

class SaveUserInfoUseCaseImpl(private val repo: UserInfoRepository) : SaveUserInfoUseCase {
    override suspend fun saveUser(info: UserInfo) = repo.saveUserInfo(info)
}

package com.example.nutrition.mynutrition.domain.repository

import com.example.nutrition.mynutrition.domain.model.user.UserInfo

class UserInfoRepositoryImpl(): UserInfoRepository {
    override suspend fun saveUserInfo(info: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserInfo(): UserInfo? {
        TODO("Not yet implemented")
    }
}
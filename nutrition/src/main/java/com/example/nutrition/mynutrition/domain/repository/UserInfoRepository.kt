package com.example.nutrition.mynutrition.domain.repository

import com.example.nutrition.mynutrition.domain.model.user.UserInfo

interface UserInfoRepository {
    suspend fun saveUserInfo(info: UserInfo)
    suspend fun getUserInfo(): UserInfo?
}
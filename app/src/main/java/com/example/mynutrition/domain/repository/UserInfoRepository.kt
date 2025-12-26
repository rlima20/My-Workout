package com.example.mynutrition.domain.repository

import com.example.mynutrition.domain.model.UserInfo

interface UserInfoRepository {
    suspend fun saveUserInfo(info: UserInfo)
    suspend fun getUserInfo(): UserInfo?
}
package com.example.nutrition.mynutrition.domain.repository

import com.example.nutrition.mynutrition.domain.mappers.toModel
import com.example.nutrition.mynutrition.domain.mappers.toEntity
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.room.dao.UserInfoDao

class UserInfoRepositoryImpl(private val userInfoDao: UserInfoDao) : UserInfoRepository {
    override suspend fun saveUserInfo(info: UserInfo) {
        userInfoDao.insert(info.toEntity())
    }

    override suspend fun getUserInfo(): UserInfo? {
        return userInfoDao.getUserInfo()?.toModel()
    }
}
package com.example.nutrition.mynutrition.domain.repository

import com.example.nutrition.mynutrition.domain.mappers.toUserInfo
import com.example.nutrition.mynutrition.domain.mappers.toUserInfoEntity
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.room.dao.UserInfoDao

class UserInfoRepositoryImpl(private val userInfoDao: UserInfoDao) : UserInfoRepository {
    override suspend fun saveUserInfo(info: UserInfo) {
        userInfoDao.insert(info.toUserInfoEntity())
    }

    override suspend fun getUserInfo(): UserInfo? {
        return userInfoDao.getUserInfo()?.toUserInfo()
    }
}
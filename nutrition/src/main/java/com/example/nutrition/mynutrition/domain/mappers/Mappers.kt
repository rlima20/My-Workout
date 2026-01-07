package com.example.nutrition.mynutrition.domain.mappers

import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.room.entity.UserInfoEntity

fun UserInfo.toUserInfoEntity(): UserInfoEntity {
    return UserInfoEntity(
        name = this.name,
        age = this.age,
        sex = this.sex,
        weightKg = this.weightKg,
        heightCm = this.heightCm,
        activityLevel = this.activityLevel
    )
}

fun UserInfoEntity.toUserInfo(): UserInfo {
    return UserInfo(
        name = this.name,
        age = this.age,
        sex = this.sex,
        weightKg = this.weightKg,
        heightCm = this.heightCm,
        activityLevel = this.activityLevel
    )
}
package com.example.nutrition.mynutrition.domain.mappers

import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.room.entity.UserInfoEntity
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.state.UserInfoState

fun UserInfo.toUserInfoState(): UserInfoState {
    return UserInfoState(
        name = this.name,
        age = this.age.toString(),
        sex = this.sex,
        height = this.heightCm.toString(),
        weight = this.weightKg.toString(),
        activity = this.activityLevel
    )
}

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
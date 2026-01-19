package com.example.nutrition.mynutrition.domain.mappers

import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoal
import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoalWithMacrosModel
import com.example.nutrition.mynutrition.domain.model.macro.MacroResult
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.room.entity.CalorieGoalEntity
import com.example.nutrition.mynutrition.domain.room.entity.CalorieGoalWithMacros
import com.example.nutrition.mynutrition.domain.room.entity.MacroResultEntity
import com.example.nutrition.mynutrition.domain.room.entity.UserInfoEntity
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.state.UserInfoState

fun List<CalorieGoalWithMacros>.toModel(): List<CalorieGoalWithMacrosModel> {
    val list: MutableList<CalorieGoalWithMacrosModel> = mutableListOf()

    this.forEach {
        list.add(
            it.toModel()
        )
    }
    return list
}


fun CalorieGoalWithMacros.toModel(): CalorieGoalWithMacrosModel {
    return CalorieGoalWithMacrosModel(
        calorieGoal = this.calorieGoal.toModel(),
        macros = this.macros?.toModel()
    )
}


fun UserInfo.toState(): UserInfoState {
    return UserInfoState(
        name = this.name,
        age = this.age.toString(),
        sex = this.sex,
        height = this.heightCm.toString(),
        weight = this.weightKg.toString(),
        activity = this.activityLevel,
        goalType = this.goalType
    )
}

fun UserInfo.toEntity(): UserInfoEntity {
    return UserInfoEntity(
        name = this.name,
        age = this.age,
        sex = this.sex,
        weightKg = this.weightKg,
        heightCm = this.heightCm,
        activityLevel = this.activityLevel,
        goalType = this.goalType,
    )
}

fun UserInfoEntity.toModel(): UserInfo {
    return UserInfo(
        name = this.name,
        age = this.age,
        sex = this.sex,
        weightKg = this.weightKg,
        heightCm = this.heightCm,
        activityLevel = this.activityLevel,
        goalType = this.goalType,
    )
}

fun CalorieGoal.toEntity(): CalorieGoalEntity {
    return CalorieGoalEntity(
        calorieGoalId = this.calorieGoalId,
        tmb = this.tmb,
        calorieGoal = this.calorieGoal,
        macrosId = this.macrosId,
    )
}

fun CalorieGoalEntity.toModel(): CalorieGoal {
    return CalorieGoal(
        calorieGoalId = this.calorieGoalId,
        tmb = this.tmb,
        calorieGoal = this.calorieGoal,
        macrosId = this.macrosId
    )
}

fun MacroResult.toEntity(): MacroResultEntity {
    return MacroResultEntity(
        macrosId = 0,
        carbsGrams = this.carbsGrams,
        carbsKcal = this.carbsKcal,
        proteinsGrams = this.proteinsGrams,
        proteinsKcal = this.proteinsKcal,
        fatsGrams = this.fatsGrams,
        fatsKcal = this.fatsKcal,
        fibersGrams = this.fibersGrams,
        fibersKcal = this.fatsKcal
    )
}

fun MacroResultEntity.toModel(): MacroResult {
    return MacroResult(
        macroResultId = this.macrosId,
        carbsGrams = this.carbsGrams,
        carbsKcal = this.carbsKcal,
        proteinsGrams = this.proteinsGrams,
        proteinsKcal = this.proteinsKcal,
        fatsGrams = this.fatsGrams,
        fatsKcal = this.fatsKcal,
        fibersGrams = this.fibersGrams,
    )
}
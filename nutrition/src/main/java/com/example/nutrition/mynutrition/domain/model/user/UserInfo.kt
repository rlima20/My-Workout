package com.example.nutrition.mynutrition.domain.model.user

import android.os.Parcelable
import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.Sex
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
    val name: String,
    val age: Int,
    val sex: Sex,
    val heightCm: Int,
    val weightKg: Float,
    val activityLevel: ActivityLevel
) : Parcelable
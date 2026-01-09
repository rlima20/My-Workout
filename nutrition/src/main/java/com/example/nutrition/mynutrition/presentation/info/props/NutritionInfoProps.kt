package com.example.nutrition.mynutrition.presentation.info.props

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.Sex
import com.example.nutrition.mynutrition.presentation.info.state.UserInfoState

data class NutritionInfoProps(
    val state: UserInfoState,
    val onNameChanged: (String) -> Unit,
    val onAgeChanged: (String) -> Unit,
    val onSexChanged: (Sex) -> Unit,
    val onHeightChanged: (String) -> Unit,
    val onWeightChanged: (String) -> Unit,
    val onActivityChanged: (ActivityLevel) -> Unit,
    val onSave: () -> Unit,
)
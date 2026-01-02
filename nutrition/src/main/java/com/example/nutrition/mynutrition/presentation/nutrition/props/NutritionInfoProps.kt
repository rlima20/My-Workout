package com.example.nutrition.mynutrition.presentation.nutrition.props

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.Sex
import com.example.nutrition.mynutrition.presentation.nutrition.info.state.NutritionInfoState

data class NutritionInfoProps(
    val state: NutritionInfoState,
    val onNameChanged: (String) -> Unit,
    val onAgeChanged: (String) -> Unit,
    val onSexChanged: (Sex) -> Unit,
    val onHeightChanged: (String) -> Unit,
    val onWeightChanged: (String) -> Unit,
    val onActivityChanged: (ActivityLevel) -> Unit,
    val onSave: () -> Unit,
)
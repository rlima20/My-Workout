package com.example.nutrition.mynutrition.presentation.core.nutrition

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrition.R
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.presentation.components.Tooltip
import com.example.nutrition.mynutrition.presentation.components.NutritionCard

@Composable
fun NutritionScreen(
    showNutritionCard: Boolean,
    nutritionResult: NutritionResult,
    onToolTipClick: () -> Unit
) {
    if (showNutritionCard) {
        NutritionCard(
            modifier = Modifier.padding(top = 2.dp, start = 16.dp, end = 16.dp),
            circularStrokeWidth = 7.dp,
            kcalTextSize = 12.sp,
            nutritionResult = nutritionResult
        )
    } else {
        Tooltip(
            modifier = Modifier.padding(horizontal = 16.dp),
            backgroundColor = R.color.warning_home,
            fontSize = 12.sp,
            icon = painterResource(R.drawable.baseline_info_24),
            text = stringResource(R.string.nutrition_info_call),
            onClick = { onToolTipClick() }
        )
    }
}
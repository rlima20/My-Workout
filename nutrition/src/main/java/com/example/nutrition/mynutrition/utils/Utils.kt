package com.example.nutrition.mynutrition.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nutrition.R
import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.model.enums.Sex

@Composable
fun colors(): androidx.compose.material3.TextFieldColors = OutlinedTextFieldDefaults.colors(
    unfocusedTextColor = colorResource(R.color.title_color),
    focusedTextColor = colorResource(R.color.title_color),
    focusedLabelColor = colorResource(R.color.title_color),
    unfocusedLabelColor = colorResource(R.color.title_color),
    focusedBorderColor = colorResource(R.color.button_color)
)

@Composable
fun setColor(isSelected: Boolean): Color = if (isSelected) colorResource(R.color.white)
else colorResource(R.color.text_color)

@Composable
fun setButtonTextColor(isEnabled: Boolean): Color = if (isEnabled) colorResource(R.color.white)
else colorResource(R.color.text_color)

@Composable
fun setButtonColor(isEnabled: Boolean): ButtonColors {
    val color = if (isEnabled) R.color.button_color else R.color.text_field_background_color3
    return ButtonDefaults.buttonColors(
        containerColor = colorResource(color),
        disabledContainerColor = colorResource(color),
        disabledContentColor = colorResource(R.color.text_color)
    )
}

@Composable
fun setText(sex: Sex): String = if (sex == Sex.MALE) stringResource(R.string.male)
else stringResource(R.string.female)

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun selectedChipColor() = ChipDefaults.filterChipColors(
    selectedContentColor = colorResource(R.color.button_color),
    selectedBackgroundColor = colorResource(R.color.button_color),
)

object UiFieldDefaults {
    val Shape = RoundedCornerShape(16.dp)

    @Composable
    fun colors() = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(R.color.button_color),
        unfocusedBorderColor = colorResource(R.color.title_color),
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        disabledTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        focusedTextColor = Color.Black
    )
}

@Composable
fun textActivityLevel(selected: ActivityLevel): String = when (selected) {
    ActivityLevel.SEDENTARY -> stringResource(R.string.sedentary)
    ActivityLevel.LIGHT -> stringResource(R.string.light)
    ActivityLevel.MODERATE -> stringResource(R.string.moderate)
    ActivityLevel.HIGH -> stringResource(R.string.high)
    ActivityLevel.EXTREME -> stringResource(R.string.extreme)
}

@Composable
fun textGoalType(selected: CalorieGoalType): String = when (selected) {
    CalorieGoalType.MAINTAIN -> stringResource(R.string.maintain)
    CalorieGoalType.GAIN -> stringResource(R.string.gain)
    CalorieGoalType.LOSE -> stringResource(R.string.lose)
}

@Composable
fun getCardColors(): CardColors = CardDefaults.cardColors(
    containerColor = colorResource(R.color.training_section_card_color),
    contentColor = colorResource(R.color.training_section_card_color),
    disabledContainerColor = colorResource(R.color.training_section_card_color),
    disabledContentColor = colorResource(R.color.training_section_card_color)
)
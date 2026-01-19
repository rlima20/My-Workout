package com.example.nutrition.mynutrition.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrition.R
import com.example.nutrition.mynutrition.domain.model.enums.MacroType
import com.example.nutrition.mynutrition.domain.model.macro.Colors
import com.example.nutrition.mynutrition.domain.model.macro.MacroResult
import com.example.nutrition.mynutrition.domain.model.macro.MacroUiModel
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult

@Composable
fun NutritionCard(
    modifier: Modifier = Modifier,
    circularStrokeWidth: Dp,
    kcalTextSize: TextUnit,
    nutritionResult: NutritionResult
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = buttonSectionCardsColors(),
        shape = CardDefaults.elevatedShape,
        border = BorderStroke(1.dp, colorResource(R.color.border_color)),
        elevation = CardDefaults.cardElevation(),

        ) {
        Column {
            TbmInfo(tmb = nutritionResult.tmb)
            Divider()
            MacroDivisionInfo(
                kcalTextSize,
                circularSize(),
                circularStrokeWidth,
                nutritionResult.macros
            )
        }
    }
}

@Composable
fun buttonSectionCardsColors(): CardColors = CardDefaults.cardColors(
    containerColor = colorResource(R.color.white),
    contentColor = colorResource(R.color.white),
    disabledContainerColor = colorResource(R.color.white),
    disabledContentColor = colorResource(R.color.white)
)

@Composable
fun Divider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = Color.LightGray,
    startIndent: Dp = 1.dp
) {
    Divider(
        modifier = modifier.padding(horizontal = 12.dp),
        thickness = thickness,
        color = color,
        startIndent = startIndent
    )
}

@Composable
private fun circularSize(): Dp {
    val paddingStart = 16
    val paddingEnd = 16
    val spaceBetween = 48
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val size: Int = (screenWidthDp - paddingStart - paddingEnd - spaceBetween) / 4
    return size.dp
}

@Composable
private fun MacroDivisionInfo(
    kcalTextSize: TextUnit,
    circularIndicatorSize: Dp,
    circularStrokeWidth: Dp,
    macroResult: MacroResult
) {
    Text(
        modifier = Modifier.padding(top = 8.dp, start = 16.dp),
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(R.color.title_color),
        text = stringResource(R.string.macro_division)
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 4.dp,
                end = 16.dp,
                bottom = 8.dp
            )
            .fillMaxWidth()
    ) {
        MacroCircularIndicator(
            kcalTextSize = kcalTextSize,
            circularIndicatorSize = circularIndicatorSize,
            circularStrokeWidth = circularStrokeWidth,
            macro = MacroUiModel(
                name = stringResource(R.string.carbs),
                type = MacroType.CARBS,
                kcal = macroResult.carbsKcal,
                grams = macroResult.carbsGrams
            ),
            colors = Colors(
                calorieTextColor = R.color.text_color,
                macroTextColor = R.color.text_color
            ),
            totalCalories = getTotalCalories(macroResult)
        )

        MacroCircularIndicator(
            kcalTextSize = kcalTextSize,
            circularIndicatorSize = circularIndicatorSize,
            circularStrokeWidth = circularStrokeWidth,
            colors = Colors(
                calorieTextColor = R.color.text_color,
                macroTextColor = R.color.text_color
            ),
            macro = MacroUiModel(
                name = stringResource(R.string.proteins),
                type = MacroType.PROTEIN,
                kcal = 250,
                grams = macroResult.proteinsGrams
            ),
            totalCalories = getTotalCalories(macroResult)
        )

        MacroCircularIndicator(
            kcalTextSize = kcalTextSize,
            circularIndicatorSize = circularIndicatorSize,
            circularStrokeWidth = circularStrokeWidth,
            colors = Colors(
                calorieTextColor = R.color.text_color,
                macroTextColor = R.color.text_color
            ),
            macro = MacroUiModel(
                name = stringResource(R.string.fats),
                type = MacroType.FAT,
                kcal = 250,
                grams = macroResult.fatsGrams
            ),
            totalCalories = getTotalCalories(macroResult)
        )

        MacroCircularIndicator(
            kcalTextSize = kcalTextSize,
            circularIndicatorSize = circularIndicatorSize,
            circularStrokeWidth = circularStrokeWidth,
            colors = Colors(
                calorieTextColor = R.color.text_color,
                macroTextColor = R.color.text_color
            ),
            macro = MacroUiModel(
                name = stringResource(R.string.fibers),
                type = MacroType.FIBER,
                kcal = 250,
                grams = macroResult.fibersGrams
            ),
            totalCalories = getTotalCalories(macroResult)
        )
    }
}

fun getTotalCalories(macroResult: MacroResult): Int =
    macroResult.carbsKcal +
            macroResult.proteinsKcal +
            macroResult.fatsKcal
@Composable
private fun TbmInfo(tmb: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp)
    ) {
        TextInfo(
            modifier = Modifier.padding(end = 8.dp),
            text = "TBM:",
            icon = painterResource(R.drawable.baseline_info_24),
            onIconClick = {}
        )

        Text(
            text = tmb.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.title_color),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true, widthDp = 600)
@Composable
fun MacrosCardPreview() {
    MaterialTheme {
        NutritionCard(
            modifier = Modifier,
            circularStrokeWidth = 100.dp,
            kcalTextSize = 5.sp,
            NutritionResult(
                2000,
                2200,
                1900,
                MacroResult(
                    100,
                    100,
                    100,
                    100,
                    100,
                    100,
                    100,
                    100
                )
            )
        )
    }
}
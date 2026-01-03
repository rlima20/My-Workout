package com.example.nutrition.mynutrition.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrition.R
import com.example.nutrition.mynutrition.domain.model.enums.MacroType
import com.example.nutrition.mynutrition.domain.model.macro.Colors
import com.example.nutrition.mynutrition.domain.model.macro.MacroUiModel

@Composable
fun MacroCircularIndicator(
    modifier: Modifier = Modifier,
    macro: MacroUiModel,
    totalCalories: Int,
    colors: Colors,
    kcalTextSize: TextUnit,
    circularIndicatorSize: Dp = 100.dp,
    circularStrokeWidth: Dp = 10.dp
) {
    MaterialTheme {

        val progress = remember(macro, totalCalories) {
            macroCaloriePercentage(macro.kcal, totalCalories)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                fontSize = 12.sp,
                color = colorResource(colors.macroTextColor),
                modifier = Modifier.padding(bottom = 4.dp),
                text = macro.name,
                style = MaterialTheme.typography.bodyMedium,
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.size(circularIndicatorSize)
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    strokeWidth = circularStrokeWidth,
                    color = colorResource(R.color.button_color),
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        color = colorResource(R.color.title_color),
                        text = stringResource(R.string.kcal, macro.kcal),
                        fontSize = kcalTextSize,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = stringResource(R.string.g, macro.grams),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private fun macroCaloriePercentage(
    macroCalories: Int,
    totalCalories: Int
): Float {
    return (macroCalories.toFloat() / totalCalories.toFloat())
        .coerceIn(0f, 1f)
}

@Preview(showBackground = true)
@Composable
fun MacroCircularIndicatorProteinPreview() {
    MaterialTheme {
        MacroCircularIndicator(
            kcalTextSize = 10.sp,
            colors = Colors(
                calorieTextColor = R.color.text_color,
                macroTextColor = R.color.text_color
            ),
            macro = MacroUiModel(
                name = stringResource(R.string.proteins),
                type = MacroType.PROTEIN,
                kcal = 250,
                grams = 50
            ),
            totalCalories = 1000
        )
    }
}

@Preview(showBackground = true, widthDp = 460)
@Composable
fun MacroCircularIndicatorsAllPreview() {
    MaterialTheme {
        val totalCalories = 1000

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            MacroCircularIndicator(
                kcalTextSize = 10.sp,
                colors = Colors(
                    calorieTextColor = R.color.text_color,
                    macroTextColor = R.color.text_color
                ),
                macro = MacroUiModel(
                    name = stringResource(R.string.carbs),
                    type = MacroType.CARBS,
                    kcal = 250,
                    grams = 50
                ),
                totalCalories = totalCalories
            )

            MacroCircularIndicator(
                kcalTextSize = 10.sp,
                colors = Colors(
                    calorieTextColor = R.color.text_color,
                    macroTextColor = R.color.text_color
                ),
                macro = MacroUiModel(
                    name = stringResource(R.string.proteins),
                    type = MacroType.PROTEIN,
                    kcal = 250,
                    grams = 50
                ),
                totalCalories = totalCalories
            )

            MacroCircularIndicator(
                kcalTextSize = 10.sp,
                colors = Colors(
                    calorieTextColor = R.color.text_color,
                    macroTextColor = R.color.text_color
                ),
                macro = MacroUiModel(
                    name = stringResource(R.string.fats),
                    type = MacroType.FAT,
                    kcal = 250,
                    grams = 50
                ),
                totalCalories = totalCalories
            )

            MacroCircularIndicator(
                kcalTextSize = 10.sp,
                colors = Colors(
                    calorieTextColor = R.color.text_color,
                    macroTextColor = R.color.text_color
                ),
                macro = MacroUiModel(
                    name = stringResource(R.string.fibers),
                    type = MacroType.FIBER,
                    kcal = 250,
                    grams = 50
                ),
                totalCalories = totalCalories
            )
        }
    }
}

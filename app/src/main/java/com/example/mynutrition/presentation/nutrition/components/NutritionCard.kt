package com.example.mynutrition.presentation.nutrition.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.mynutrition.domain.model.enums.MacroType
import com.example.mynutrition.domain.model.macro.Colors
import com.example.mynutrition.domain.model.macro.MacroUiModel
import com.example.myworkout.R
import com.example.myworkout.presentation.ui.components.commons.Divider
import com.example.myworkout.utils.Utils


@Composable
fun NutritionCard(
    circularIndicatorSize: Dp,
    circularStrokeWidth: Dp,
    kcalTextSize: TextUnit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = Utils().buttonSectionCardsColors(),
        shape = CardDefaults.elevatedShape,
        border = BorderStroke(1.dp, colorResource(R.color.border_color)),
        elevation = CardDefaults.cardElevation(),

        ) {
        Column {
            Row(
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
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "1200 Kcal",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.title_color),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Divider()

            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.text_color),
                text = stringResource(R.string.macro_division)
            )
            val totalCalories = 1000

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 4.dp, end = 16.dp, bottom = 8.dp
                    )
                    .fillMaxWidth()
            ) {
                MacroCircularIndicator(
                    kcalTextSize = kcalTextSize,
                    circularIndicatorSize = circularIndicatorSize,
                    circularStrokeWidth = circularStrokeWidth,
                    macro = MacroUiModel(
                        name = "Carboidratos",
                        type = MacroType.CARBS,
                        kcal = 250
                    ),
                    totalCalories = totalCalories,
                    colors = Colors(
                        calorieTextColor = R.color.text_color,
                        macroTextColor = R.color.text_color
                    ),
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
                        name = "Proteínas",
                        type = MacroType.PROTEIN,
                        kcal = 250
                    ),
                    totalCalories = totalCalories
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
                        name = "Gorduras",
                        type = MacroType.FAT,
                        kcal = 250
                    ),
                    totalCalories = totalCalories
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
                        name = "Fibras",
                        type = MacroType.FIBER,
                        kcal = 250
                    ),
                    totalCalories = totalCalories
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 600)
@Composable
fun MacrosCardPreview() {
    MaterialTheme {
        NutritionCard(
            100.dp, 5.dp, 10.sp
        )
    }
}
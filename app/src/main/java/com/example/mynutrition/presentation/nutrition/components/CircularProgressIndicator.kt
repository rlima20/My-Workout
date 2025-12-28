package com.example.mynutrition.presentation.nutrition.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class MacroType(val kcalPerGram: Int) {
    CARBS(4),
    PROTEIN(4),
    FAT(9),
    FIBER(2) // opcional
}

data class MacroUiModel(
    val name: String,
    val type: MacroType,
    val kcal: Int
)

fun macroGrams(
    macroCalories: Int,
    kcalPerGram: Int
): Int {
    return (macroCalories / kcalPerGram.toFloat()).toInt()
}

fun macroCaloriePercentage(
    macroCalories: Int,
    totalCalories: Int
): Float {
    return (macroCalories.toFloat() / totalCalories.toFloat())
        .coerceIn(0f, 1f)
}

fun MacroUiModel.grams(): Int {
    return (kcal / type.kcalPerGram.toFloat()).toInt()
}

@Composable
fun MacroCircularIndicator(
    macro: MacroUiModel,
    totalCalories: Int,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 10.dp
) {
    val progress = remember(macro, totalCalories) {
        macroCaloriePercentage(macro.kcal, totalCalories)
    }

    Column(
        modifier = Modifier.padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = macro.name,
            style = MaterialTheme.typography.bodyMedium,
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.size(100.dp)
        ) {
            CircularProgressIndicator(
                progress = { progress },
                strokeWidth = strokeWidth,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${macro.kcal} kcal",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${macro.grams()} g",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MacroCircularIndicatorProteinPreview() {
    MaterialTheme {
        MacroCircularIndicator(
            macro = MacroUiModel(
                name = "Proteínas",
                type = MacroType.PROTEIN,
                kcal = 250
            ),
            totalCalories = 1000
        )
    }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
fun MacroCircularIndicatorsAllPreview() {
    MaterialTheme {
        val totalCalories = 1000

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            MacroCircularIndicator(
                macro = MacroUiModel(
                    name = "Carboidratos",
                    type = MacroType.CARBS,
                    kcal = 250
                ),
                totalCalories = totalCalories
            )

            MacroCircularIndicator(
                macro = MacroUiModel(
                    name = "Proteínas",
                    type = MacroType.PROTEIN,
                    kcal = 250
                ),
                totalCalories = totalCalories
            )

            MacroCircularIndicator(
                macro = MacroUiModel(
                    name = "Gorduras",
                    type = MacroType.FAT,
                    kcal = 250
                ),
                totalCalories = totalCalories
            )

            MacroCircularIndicator(
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

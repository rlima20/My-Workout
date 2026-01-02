package com.example.nutrition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nutrition.mynutrition.domain.model.macro.MacroResult
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.presentation.nutrition.NutritionComponent
import com.example.nutrition.ui.theme.MyWorkoutTheme

class NutritionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWorkoutTheme {

                val showNutritionCard = false
                NutritionComponent(
                    showNutritionCard = showNutritionCard,
                    nutritionResult = getNutritionResult(),
                    onToolTipClick = {}
                )
            }
        }
    }
}

private fun getNutritionResult(): NutritionResult =
    NutritionResult(
        tmb = 2000,
        maintenanceCalories = 2000,
        calorieGoal = 2500,
        macros =
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

@Preview(showBackground = true)
@Composable
fun NutritionActivityPreview() {
    MyWorkoutTheme {
        NutritionComponent(
            true,
            getNutritionResult(),
            {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NutritionActivityFalsePreview() {
    MyWorkoutTheme {
        NutritionComponent(
            false,
            getNutritionResult(),
            {}
        )
    }
}
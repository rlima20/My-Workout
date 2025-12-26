package com.example.mynutrition.presentation.nutrition.calorie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynutrition.domain.model.MacroResult
import com.example.mynutrition.domain.model.enums.CalorieGoalType
import com.example.mynutrition.presentation.nutrition.calorie.state.CalorieGoalState
import com.example.mynutrition.presentation.nutrition.components.GoalToggle
import com.example.mynutrition.presentation.nutrition.components.MacroCard

@Composable
fun CalorieGoalScreen(
    state: CalorieGoalState,
    onGoalChanged: (CalorieGoalType) -> Unit
) {
    if (state.isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        GoalToggle(selected = state.goalType, onSelect = onGoalChanged)
        Text("TMB: ${state.tmb} kcal", modifier = Modifier.padding(top = 12.dp))
        Text("Meta diária: ${state.calorieGoal} kcal", modifier = Modifier.padding(top = 6.dp))
        state.macros?.let { macros ->
            MacroCard(
                title = "Carboidratos",
                grams = macros.carbsGrams,
                kcal = macros.carbsKcal,
                modifier = Modifier.fillMaxWidth()
            )
            MacroCard(
                title = "Proteínas",
                grams = macros.proteinsGrams,
                kcal = macros.proteinsKcal,
                modifier = Modifier.fillMaxWidth()
            )
            MacroCard(
                title = "Gorduras",
                grams = macros.fatsGrams,
                kcal = macros.fatsKcal,
                modifier = Modifier.fillMaxWidth()
            )
            MacroCard(
                title = "Fibras",
                grams = macros.fibersGrams,
                kcal = macros.fibersKcal,
                modifier = Modifier.fillMaxWidth()
            )
        }
        state.error?.let { Text(it) }
    }
}

@Preview(showBackground = true)
@Composable
fun CalorieGoalScreenPreview() {
    MaterialTheme {
        CalorieGoalScreen(
            state = CalorieGoalState(
                goalType = CalorieGoalType.MAINTAIN,
                tmb = 1750,
                calorieGoal = 2600,
                macros = MacroResult(
                    carbsGrams = 325,
                    proteinsGrams = 160,
                    fatsGrams = 72,
                    fibersGrams = 36,
                    carbsKcal = 1300,
                    proteinsKcal = 640,
                    fatsKcal = 648,
                    fibersKcal = 72
                ),
                isLoading = false
            ),
            onGoalChanged = {}
        )
    }
}
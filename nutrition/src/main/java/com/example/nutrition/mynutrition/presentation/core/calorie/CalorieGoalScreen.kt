package com.example.nutrition.mynutrition.presentation.core.calorie

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
import androidx.navigation.compose.rememberNavController
import com.example.nutrition.mynutrition.constants.getUserInfo
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.preferences.NutritionPrefs
import com.example.nutrition.mynutrition.presentation.components.MacroCard
import com.example.nutrition.mynutrition.presentation.core.calorie.props.CalorieGoalProps
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.state.CalorieGoalState

@Composable
fun CalorieGoalScreen(
    calorieGoalProps: CalorieGoalProps,
    onGoalChanged: (CalorieGoalType) -> Unit,
    userInfo: UserInfo?
) {
    // todo - Calcular tbm
    // todo - calcular meta diária
    // Todo - popular macro result

    if (calorieGoalProps.state.isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("TMB: ${calorieGoalProps.state.tmb} kcal", modifier = Modifier.padding(top = 12.dp))
        Text(
            "Meta diária: ${calorieGoalProps.state.calorieGoal} kcal",
            modifier = Modifier.padding(top = 6.dp)
        )
        calorieGoalProps.state.macros?.let { macros ->
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
        calorieGoalProps.state.error?.let { Text(it) }
    }
}

@Preview(showBackground = true)
@Composable
fun CalorieGoalScreenPreview() {
    MaterialTheme {
        CalorieGoalScreen(
            calorieGoalProps = CalorieGoalProps(
                state = CalorieGoalState(
                    isLoading = false
                ),
                onGoalChanged = {},
                navController = rememberNavController(),
                prefs = NutritionPrefs()
            ),
            onGoalChanged = {},
            userInfo = getUserInfo(),
        )
    }
}

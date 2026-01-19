package com.example.nutrition.mynutrition.presentation.core.calorie

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.nutrition.R
import com.example.nutrition.mynutrition.constants.getUserInfo
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.preferences.NutritionPrefs
import com.example.nutrition.mynutrition.presentation.components.EmptyStateComponent
import com.example.nutrition.mynutrition.presentation.components.MacroCard
import com.example.nutrition.mynutrition.presentation.core.calorie.props.CalorieGoalProps
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.CalorieGoalViewModel
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.UiState
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.state.CalorieGoalState
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.viewmodelfake.CalorieGoalViewModelFake

@Composable
fun CalorieGoalScreen(
    calorieGoalProps: CalorieGoalProps,
    calorieGoalViewModel: CalorieGoalViewModel,
    userInfo: UserInfo?,
    onGoalChanged: (CalorieGoalType) -> Unit,
) {

    calorieGoalViewModel.calculateCalorieGoal(
        userInfo = userInfo,
    )

    when (calorieGoalProps.uiState) {
        is UiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            return
        }

        is UiState.Error -> {
            EmptyStateComponent(
                modifier = Modifier.size(150.dp, 180.dp),
                text = stringResource(R.string.error_message),
                painter = painterResource(R.drawable.baseline_info),
                onClick = { calorieGoalViewModel.calculateCalorieGoal(userInfo) }
            )
        }

        is UiState.SuccessFetch -> {}
        is UiState.CalculateCalorieGoalSuccess -> {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "TMB: ${calorieGoalProps.calorieGoalState.tmb} kcal",
                    modifier = Modifier.padding(top = 12.dp)
                )
                Text(
                    "Meta diária: ${calorieGoalProps.calorieGoalState.calorieGoal} kcal",
                    modifier = Modifier.padding(top = 6.dp)
                )
                calorieGoalProps.calorieGoalState.macros?.let { macros ->
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
                        kcal = 0,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CalorieGoalScreenPreview() {
    MaterialTheme {
        CalorieGoalScreen(
            calorieGoalProps = CalorieGoalProps(
                calorieGoalState = CalorieGoalState(),
                uiState = UiState.CalculateCalorieGoalSuccess,
                onGoalChanged = {},
                navController = rememberNavController(),
                prefs = NutritionPrefs(),
            ),
            onGoalChanged = {},
            userInfo = getUserInfo(),
            calorieGoalViewModel = CalorieGoalViewModelFake(),
        )
    }
}

package com.example.nutrition.mynutrition.presentation.core.calorie.props

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.preferences.NutritionPrefs
import com.example.nutrition.mynutrition.presentation.core.baseprops.BaseProps
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.CalorieGoalViewModel
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.UiState
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.state.CalorieGoalState

data class CalorieGoalProps(
    val calorieGoalState: CalorieGoalState,
    val uiState: UiState,
    val onGoalChanged: (CalorieGoalType) -> Unit,
    override val navController: NavHostController,
    override val prefs: NutritionPrefs
) : BaseProps

@Composable
fun getCalorieGoalProps(calorieGoalViewModel: CalorieGoalViewModel): CalorieGoalProps {
    val navController = rememberNavController()
    val nutritionPrefs = NutritionPrefs()
    val calorieGoalState by calorieGoalViewModel.calorieGoalState.collectAsState()
    val uiState by calorieGoalViewModel.uiState.collectAsState()

    return CalorieGoalProps(
        navController = navController,
        prefs = nutritionPrefs,
        calorieGoalState = calorieGoalState,
        uiState = uiState,
        onGoalChanged = {}
    )
}
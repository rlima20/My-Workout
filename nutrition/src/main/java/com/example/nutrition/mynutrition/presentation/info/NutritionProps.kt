package com.example.nutrition.mynutrition.presentation.info

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nutrition.mynutrition.constants.getNutritionResult
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.preferences.NutritionPrefs
import com.example.nutrition.mynutrition.presentation.info.state.NutritionInfoState
import com.example.nutrition.mynutrition.presentation.info.viewmodel.NutritionInfoViewModel
import com.example.nutrition.mynutrition.presentation.info.viewmodel.UserInfo

data class NutritionProps(
    val navController: NavHostController,
    val nutritionResult: NutritionResult,
    val nutritionInfo: NutritionInfoState,
    val prefs: NutritionPrefs
)

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getNutritionProps(nutritionInfoViewModel: NutritionInfoViewModel): NutritionProps {
    val navController = rememberNavController()
    val nutritionPrefs = NutritionPrefs()
    val nutritionInfoState by nutritionInfoViewModel.uiState.collectAsState()
    val nutritionResult = getNutritionResult() // Todo - Substituir pelo valor do banco.

    return NutritionProps(
        navController = navController,
        prefs = nutritionPrefs,
        nutritionInfo = nutritionInfoState,
        nutritionResult = nutritionResult
    )
}
package com.example.nutrition.mynutrition.presentation.info

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nutrition.mynutrition.constants.getNutritionResult
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.preferences.NutritionPrefs

data class NutritionProps(
    val navController: NavHostController,
    val nutritionResult: NutritionResult,
    val prefs: NutritionPrefs
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getNutritionProps(): NutritionProps {
    val navController = rememberNavController()
    val nutritionPrefs = NutritionPrefs()
    val nutritionResult = getNutritionResult() // Todo - Substituir pelo valor do banco

    return NutritionProps(
        navController = navController,
        prefs = nutritionPrefs,
        nutritionResult = nutritionResult
    )
}
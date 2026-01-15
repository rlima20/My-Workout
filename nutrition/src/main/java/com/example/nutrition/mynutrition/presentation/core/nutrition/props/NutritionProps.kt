package com.example.nutrition.mynutrition.presentation.core.nutrition.props

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nutrition.mynutrition.constants.getNutritionResult
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.preferences.NutritionPrefs
import com.example.nutrition.mynutrition.presentation.core.baseprops.BaseProps
import com.example.nutrition.mynutrition.presentation.core.nutrition.viewmodel.NutritionViewModel

data class NutritionProps(
    val nutritionResult: NutritionResult,
    override val navController: NavHostController,
    override val prefs: NutritionPrefs,
) : BaseProps

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getNutritionProps(nutritionViewModel: NutritionViewModel): NutritionProps {
    val navController = rememberNavController()
    val nutritionPrefs = NutritionPrefs()
    val nutritionResult = getNutritionResult() // Todo - Substituir pelo valor do banco.

    return NutritionProps(
        navController = navController,
        prefs = nutritionPrefs,
        nutritionResult = nutritionResult
    )
}
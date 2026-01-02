package com.example.nutrition.mynutrition.presentation.nutrition.info

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

data class NutritionProps(
    val navController: NavHostController,
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getNutritionProps(): NutritionProps {
    val navController = rememberNavController()

    return NutritionProps(
        navController = navController
    )
}
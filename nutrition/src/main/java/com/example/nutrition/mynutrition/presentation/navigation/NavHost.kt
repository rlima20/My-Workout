package com.example.nutrition.mynutrition.presentation.navigation

import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@RequiresApi(35)
@Composable
fun NavHost(
    navController: NavHostController
) {
    val nutritionInfo: String = "Informações nutricionais"

//    NavHostCompose(
//        navController = navController,
//        startDestination = HomeScreen.route,
//        modifier = Modifier.defaultNavHostValues()
//    ) {
//        composable(route = NutritionInfo.route) {
//            NutritionInfoScreen(
//                state = NutritionInfoState(
//                    name = "Raphael",
//                    age = "30",
//                    sex = Sex.MALE,
//                    height = "178",
//                    weight = "82",
//                    activity = ActivityLevel.MODERATE,
//                    isLoading = false,
//                    success = false
//                ),
//                onNameChanged = {},
//                onAgeChanged = {},
//                onSexChanged = {},
//                onHeightChanged = {},
//                onWeightChanged = {},
//                onActivityChanged = {},
//                onSave = {}
//            )
//        }
//    }
}
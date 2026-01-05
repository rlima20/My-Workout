package com.example.nutrition.mynutrition.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutrition.R
import com.example.nutrition.mynutrition.constants.getNutritionResult
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.presentation.nutrition.NutritionComponent
import androidx.navigation.compose.NavHost as NavHostCompose

@RequiresApi(35)
@Composable
fun NavHost(
    navController: NavHostController,
    nutritionResult: NutritionResult,
    showNutritionCard: Boolean,
    onToolTipClick: () -> Unit
) {
    // Todo - Nome da tela.
    val nutritionInfo: String = stringResource(R.string.nutritional_info)

    NavHostCompose(
        navController = navController,
        startDestination = NutritionCard.route,
    ) {
        composable(route = NutritionCard.route) {
            NutritionComponent(
                showNutritionCard = showNutritionCard,
                nutritionResult = nutritionResult,
                onToolTipClick = { onToolTipClick() }
            )
        }
    }
}

@RequiresApi(35)
@Composable
@Preview(showBackground = true)
private fun NavHostPreview(){
    NavHost(
        navController = rememberNavController(),
        nutritionResult = getNutritionResult(),
        showNutritionCard = false,
        onToolTipClick = {}
    )
}

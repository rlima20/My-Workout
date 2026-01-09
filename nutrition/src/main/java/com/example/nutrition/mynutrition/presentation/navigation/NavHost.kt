package com.example.nutrition.mynutrition.presentation.navigation

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.nutrition.R
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.presentation.info.UserInfoScreen
import com.example.nutrition.mynutrition.presentation.info.state.UserInfoState
import com.example.nutrition.mynutrition.presentation.info.viewmodel.UserInfoViewModel
import com.example.nutrition.mynutrition.presentation.nutrition.NutritionComponent
import androidx.navigation.compose.NavHost as NavHostCompose

@RequiresApi(35)
@Composable
fun NavHost(
    navController: NavHostController,
    nutritionResult: NutritionResult,
    showNutritionCard: Boolean,
    nutritionInfoState: UserInfoState,
    nutritionInfoViewModel: UserInfoViewModel,
    onToolTipClick: () -> Unit
) {
    // Todo - Nome da tela.
    val nutritionInfo: String = stringResource(R.string.nutritional_info)

    NavHostCompose(
        modifier = Modifier.padding(top = 80.dp),
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
        composable(route = NutritionInfo.route) {
            UserInfoScreen(
                userInfoState = nutritionInfoState,
                nutritionInfoViewModel = nutritionInfoViewModel,
            )
        }
    }
}

//@RequiresApi(35)
//@Composable
//@Preview(showBackground = true)
//private fun NavHostPreview() {
//    NavHost(
//        navController = rememberNavController(),
//        nutritionResult = getNutritionResult(),
//        showNutritionCard = false,
//        onToolTipClick = {}
//    )
//}

package com.example.nutrition.mynutrition.presentation.navigation

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutrition.mynutrition.constants.getNutritionResult
import com.example.nutrition.mynutrition.constants.getUserInfoState
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.presentation.core.userinfo.UserInfoScreen
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.state.UserInfoState
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.UserInfoViewModel
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.viewmodelfake.UserInfoViewModelFake
import com.example.nutrition.mynutrition.presentation.core.nutrition.NutritionComponent
import androidx.navigation.compose.NavHost as NavHostCompose

@RequiresApi(35)
@Composable
fun NavHost(
    navController: NavHostController,
    nutritionResult: NutritionResult,
    showNutritionCard: Boolean,
    userInfoState: UserInfoState,
    userInfoViewModel: UserInfoViewModel,
    onToolTipClick: () -> Unit
) {
    NavHostCompose(
        modifier = Modifier.padding(top = 80.dp),
        navController = navController,
        startDestination = NutritionScreen.route,
    ) {
        composable(route = NutritionScreen.route) {
            NutritionComponent(
                showNutritionCard = showNutritionCard,
                nutritionResult = nutritionResult,
                onToolTipClick = { onToolTipClick() }
            )
        }
        composable(route = UserInfoScreen.route) {
            UserInfoScreen(
                userInfoState = userInfoState,
                nutritionInfoViewModel = userInfoViewModel,
                onUserInfoSaved = {
                    // todo - navegar para a tela de
                }
            )
        }
    }
}

@RequiresApi(35)
@Composable
@Preview(showBackground = true)
private fun NavHostPreview() {
    NavHost(
        navController = rememberNavController(),
        nutritionResult = getNutritionResult(),
        showNutritionCard = false,
        userInfoState = getUserInfoState(),
        userInfoViewModel = UserInfoViewModelFake(),
        onToolTipClick = {},
        )
}

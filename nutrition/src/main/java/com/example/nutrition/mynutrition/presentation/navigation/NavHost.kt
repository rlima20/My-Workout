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
import com.example.nutrition.mynutrition.domain.model.nutrition.NutritionResult
import com.example.nutrition.mynutrition.extensions.navigateSingleTopTo
import com.example.nutrition.mynutrition.presentation.core.nutrition.NutritionScreen
import com.example.nutrition.mynutrition.presentation.core.userinfo.UserInfoScreen
import com.example.nutrition.mynutrition.presentation.core.userinfo.props.UserInfoProps
import com.example.nutrition.mynutrition.presentation.core.userinfo.props.getUserInfoProps
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.UserInfoViewModel
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.viewmodelfake.UserInfoViewModelFake
import androidx.navigation.compose.NavHost as NavHostCompose

@RequiresApi(35)
@Composable
fun NavHost(
    navController: NavHostController,
    nutritionResult: NutritionResult,
    showNutritionCard: Boolean,
    userInfoProps: UserInfoProps,
    userInfoViewModel: UserInfoViewModel,
) {
    NavHostCompose(
        modifier = Modifier.padding(top = 80.dp),
        navController = navController,
        startDestination = NutritionScreen.route,
    ) {
        composable(route = NutritionScreen.route) {
            NutritionScreen(
                showNutritionCard = showNutritionCard,
                nutritionResult = nutritionResult,
                onToolTipClick = { navController.navigateSingleTopTo(UserInfoScreen.route) }
            )
        }

        composable(route = UserInfoScreen.route) {
            UserInfoScreen(
                userInfoProps = userInfoProps,
                nutritionInfoViewModel = userInfoViewModel,
                onUserInfoSaved = { navController.navigateSingleTopTo(CalorieGoalScreen.route) }
            )
        }
    }
}

@RequiresApi(35)
@Composable
@Preview(showBackground = true)
private fun NavHostPreview() {
    val viewModel = UserInfoViewModelFake()
    NavHost(
        navController = rememberNavController(),
        nutritionResult = getNutritionResult(),
        showNutritionCard = false,
        userInfoProps = getUserInfoProps(viewModel),
        userInfoViewModel = UserInfoViewModelFake()
    )
}

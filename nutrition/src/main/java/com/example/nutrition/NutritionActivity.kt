package com.example.nutrition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.nutrition.mynutrition.constants.getNutritionResult
import com.example.nutrition.mynutrition.constants.getUserInfoState
import com.example.nutrition.mynutrition.extensions.navigateSingleTopTo
import com.example.nutrition.mynutrition.presentation.core.calorie.props.getCalorieGoalProps
import com.example.nutrition.mynutrition.presentation.core.userinfo.props.UserInfoProps
import com.example.nutrition.mynutrition.presentation.core.userinfo.props.getUserInfoProps
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.UserInfoViewModel
import com.example.nutrition.mynutrition.presentation.navigation.NavHost
import com.example.nutrition.mynutrition.presentation.navigation.UserInfoScreen
import com.example.nutrition.mynutrition.presentation.core.nutrition.NutritionComponent
import com.example.nutrition.mynutrition.presentation.core.nutrition.props.getNutritionProps
import com.example.nutrition.mynutrition.presentation.core.nutrition.viewmodel.NutritionViewModel
import com.example.nutrition.ui.theme.MyWorkoutTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class NutritionActivity : ComponentActivity() {

    private val nutritionViewModel: NutritionViewModel by viewModel()
    private val userInfoViewModel: UserInfoViewModel by viewModel()

    @RequiresApi(35)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val showNutritionCard by nutritionViewModel.showNutritionCard.collectAsState()
            val nutritionProps = getNutritionProps(nutritionViewModel)
            val userInfoProps = getUserInfoProps(userInfoViewModel)
            val calorieProps = getCalorieGoalProps()

            setShowCardByPrefsValue(userInfoProps)

            MyWorkoutTheme {
                NavHost(
                    navController = nutritionProps.navController,
                    nutritionResult = nutritionProps.nutritionResult,
                    showNutritionCard = showNutritionCard,
                    userInfoState = userInfoProps.userInfo,
                    userInfoViewModel = userInfoViewModel,
                    onToolTipClick = {
                        navigateToNutritionInfo(nutritionProps.navController)

                        // Todo - fazer isso depois da tela de cadastro
//                        nutritionProps.prefs.setShowNutritionCard(
//                            this@NutritionActivity,
//                            true
//                        )
//                        setShowCardByPrefsValue(nutritionProps)
                    }
                )
            }
        }
    }

    fun setShowCardByPrefsValue(props: UserInfoProps) {
        nutritionViewModel.setShowNutritionCard(
            props.prefs.getShowNutritionCard(this@NutritionActivity)
        )
    }
}

private fun navigateToNutritionInfo(navHostController: NavHostController) {
    navHostController.navigateSingleTopTo(UserInfoScreen.route)
}

@Preview(showBackground = true)
@Composable
fun NutritionActivityPreview() {
    MyWorkoutTheme {
        NutritionComponent(
            true,
            getNutritionResult(),
            {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NutritionActivityFalsePreview() {
    MyWorkoutTheme {
        NutritionComponent(
            false,
            getNutritionResult(),
            {}
        )
    }
}
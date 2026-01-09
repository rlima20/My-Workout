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
import com.example.nutrition.mynutrition.extensions.navigateSingleTopTo
import com.example.nutrition.mynutrition.presentation.info.NutritionProps
import com.example.nutrition.mynutrition.presentation.info.getNutritionProps
import com.example.nutrition.mynutrition.presentation.info.viewmodel.UserInfoViewModel
import com.example.nutrition.mynutrition.presentation.navigation.NavHost
import com.example.nutrition.mynutrition.presentation.navigation.NutritionInfo
import com.example.nutrition.mynutrition.presentation.nutrition.NutritionComponent
import com.example.nutrition.mynutrition.presentation.nutrition.NutritionViewModel
import com.example.nutrition.ui.theme.MyWorkoutTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class NutritionActivity : ComponentActivity() {

    private val nutritionViewModel: NutritionViewModel by viewModel()
    private val nutritionInfoViewModel: UserInfoViewModel by viewModel()

    @RequiresApi(35)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val showNutritionCard by nutritionViewModel.showNutritionCard.collectAsState()
            val nutritionProps = getNutritionProps(nutritionInfoViewModel)

            setShowCardByPrefsValue(nutritionProps)

            MyWorkoutTheme {
                NavHost(
                    navController = nutritionProps.navController,
                    nutritionResult = nutritionProps.nutritionResult,
                    showNutritionCard = showNutritionCard,
                    nutritionInfoState = nutritionProps.nutritionInfo,
                    nutritionInfoViewModel = nutritionInfoViewModel,
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

    fun setShowCardByPrefsValue(props: NutritionProps) {
        nutritionViewModel.setShowNutritionCard(
            props.prefs.getShowNutritionCard(this@NutritionActivity)
        )
    }
}

private fun navigateToNutritionInfo(navHostController: NavHostController) {
    navHostController.navigateSingleTopTo(NutritionInfo.route)
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
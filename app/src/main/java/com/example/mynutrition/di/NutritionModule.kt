package com.example.mynutrition.di

import com.example.mynutrition.domain.usecase.CalculateCalorieGoalUseCase
import com.example.mynutrition.domain.usecase.CalculateMacrosUseCase
import com.example.mynutrition.domain.usecase.CalculateTmbUseCase
import com.example.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.mynutrition.domain.usecase.SaveUserInfoUseCase
import com.example.mynutrition.presentation.nutrition.calorie.viewmodel.CalorieGoalViewModel
import com.example.mynutrition.presentation.nutrition.info.viewmodel.NutritionInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val nutritionModule = module {
    single { SaveUserInfoUseCase(get()) }
    single { GetUserInfoUseCase(get()) }

    single { CalculateTmbUseCase() }
    single { CalculateCalorieGoalUseCase() }
    single { CalculateMacrosUseCase() }

    viewModel { NutritionInfoViewModel(get(), get()) }
    viewModel { CalorieGoalViewModel(get(), get(), get(), get()) }
}
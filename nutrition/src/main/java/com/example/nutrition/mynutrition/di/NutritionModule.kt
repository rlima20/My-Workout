package com.example.nutrition.mynutrition.di

import com.example.nutrition.mynutrition.domain.usecase.CalculateCalorieGoalUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateMacrosUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateTmbUseCase
import com.example.nutrition.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.nutrition.mynutrition.domain.usecase.SaveUserInfoUseCase
import com.example.nutrition.mynutrition.presentation.info.viewmodel.NutritionInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val nutritionModule = module {
    single { SaveUserInfoUseCase(get()) }
    single { GetUserInfoUseCase(get()) }
    single { CalculateTmbUseCase() }
    single { CalculateCalorieGoalUseCase() }
    single { CalculateMacrosUseCase() }
    viewModel { NutritionInfoViewModel(get(), get()) }
}
package com.example.nutrition.mynutrition.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nutrition.mynutrition.domain.repository.NutritionRepository
import com.example.nutrition.mynutrition.domain.repository.NutritionRepositoryImpl
import com.example.nutrition.mynutrition.domain.repository.UserInfoRepository
import com.example.nutrition.mynutrition.domain.repository.UserInfoRepositoryImpl
import com.example.nutrition.mynutrition.domain.usecase.CalculateCalorieGoalUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateCalorieGoalUseCaseImpl
import com.example.nutrition.mynutrition.domain.usecase.CalculateMacrosUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateMacrosUseCaseImpl
import com.example.nutrition.mynutrition.domain.usecase.CalculateTmbUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateTmbUseCaseImpl
import com.example.nutrition.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.nutrition.mynutrition.domain.usecase.GetUserInfoUseCaseImpl
import com.example.nutrition.mynutrition.domain.usecase.SaveUserInfoUseCase
import com.example.nutrition.mynutrition.domain.usecase.SaveUserInfoUseCaseImpl
import com.example.nutrition.mynutrition.presentation.calorie.viewmodel.CalorieGoalViewModel
import com.example.nutrition.mynutrition.presentation.info.viewmodel.NutritionInfoViewModel
import com.example.nutrition.mynutrition.presentation.nutrition.NutritionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val nutritionRepositoryDI = module {
    factory<NutritionRepository> { NutritionRepositoryImpl() }
}

val userInfoRepositoryDI = module {
    factory<UserInfoRepository> { UserInfoRepositoryImpl() }
}

val calculateCalorieGoalUseCaseDI = module {
    factory<CalculateCalorieGoalUseCase> { CalculateCalorieGoalUseCaseImpl() }
}

val calculateMacrosUseCaseDI = module {
    factory<CalculateMacrosUseCase> { CalculateMacrosUseCaseImpl() }
}

val calculateTmbUseCaseDI = module {
    factory<CalculateTmbUseCase> { CalculateTmbUseCaseImpl() }
}

val getUserInfoUseCaseDI = module {
    factory<GetUserInfoUseCase> { GetUserInfoUseCaseImpl(get()) }
}

val saveUserInfoUseCaseDI = module {
    factory<SaveUserInfoUseCase> { SaveUserInfoUseCaseImpl(get()) }
}

val calorieGoalViewModelDI = module {
    viewModel {
        CalorieGoalViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}

val nutritionInfoViewModelDI = module {
    viewModel {
        NutritionInfoViewModel(get(), get())
    }
}

val nutritionViewModelDI = module {
    viewModel {
        NutritionViewModel()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
val appModules = listOf(
    nutritionRepositoryDI,
    userInfoRepositoryDI,
    calculateCalorieGoalUseCaseDI,
    calculateMacrosUseCaseDI,
    calculateTmbUseCaseDI,
    getUserInfoUseCaseDI,
    saveUserInfoUseCaseDI,
    calorieGoalViewModelDI,
    nutritionInfoViewModelDI,
    nutritionViewModelDI
)
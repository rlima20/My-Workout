package com.example.nutrition.mynutrition.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nutrition.mynutrition.domain.repository.CalorieGoalRepository
import com.example.nutrition.mynutrition.domain.repository.CalorieGoalRepositoryImpl
import com.example.nutrition.mynutrition.domain.repository.NutritionRepository
import com.example.nutrition.mynutrition.domain.repository.NutritionRepositoryImpl
import com.example.nutrition.mynutrition.domain.repository.UserInfoRepository
import com.example.nutrition.mynutrition.domain.repository.UserInfoRepositoryImpl
import com.example.nutrition.mynutrition.domain.room.database.AppDatabase
import com.example.nutrition.mynutrition.domain.usecase.CalculateCalorieGoalUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateCalorieGoalUseCaseImpl
import com.example.nutrition.mynutrition.domain.usecase.CalculateMacrosUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateMacrosUseCaseImpl
import com.example.nutrition.mynutrition.domain.usecase.CalculateTmbUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateTmbUseCaseImpl
import com.example.nutrition.mynutrition.domain.usecase.GetCalorieGoalWithMacrosUseCase
import com.example.nutrition.mynutrition.domain.usecase.GetCalorieGoalWithMacrosUseCaseImpl
import com.example.nutrition.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.nutrition.mynutrition.domain.usecase.GetUserInfoUseCaseImpl
import com.example.nutrition.mynutrition.domain.usecase.SaveCalorieGoalUseCase
import com.example.nutrition.mynutrition.domain.usecase.SaveCalorieGoalUseCaseImpl
import com.example.nutrition.mynutrition.domain.usecase.SaveUserInfoUseCase
import com.example.nutrition.mynutrition.domain.usecase.SaveUserInfoUseCaseImpl
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.CalorieGoalViewModel
import com.example.nutrition.mynutrition.presentation.core.nutrition.viewmodel.NutritionViewModel
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.UserInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseDI = module {
    single {
        AppDatabase.getInstance(get())
    }
}

val userInfoDaoDI = module {
    factory { get<AppDatabase>().userInfoDao() }
}

val calorieGoalDaoDI = module {
    factory { get<AppDatabase>().calorieGoalDao() }
}

val macroResultDaoDI = module {
    factory { get<AppDatabase>().macroResultDao() }
}

val nutritionRepositoryDI = module {
    factory<NutritionRepository> { NutritionRepositoryImpl() }
}

val userInfoRepositoryDI = module {
    factory<UserInfoRepository> { UserInfoRepositoryImpl(get()) }
}

val calorieGoalRepositoryDI = module {
    factory<CalorieGoalRepository> { CalorieGoalRepositoryImpl(get()) }
}

val calculateCalorieGoalUseCaseDI = module {
    factory<CalculateCalorieGoalUseCase> { CalculateCalorieGoalUseCaseImpl() }
}

val getCalorieGoalWithMacrosUseCaseDI = module {
    factory<GetCalorieGoalWithMacrosUseCase> { GetCalorieGoalWithMacrosUseCaseImpl(get()) }
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

val saveCalorieGoalUseCaseDI = module {
    factory<SaveCalorieGoalUseCase> {
        SaveCalorieGoalUseCaseImpl(
            get(),
            get()
        )
    }
}

val getCalorieGoalUseCaseDI = module {
    factory<GetCalorieGoalWithMacrosUseCase> { GetCalorieGoalWithMacrosUseCaseImpl(get()) }
}

val userInfoViewModelDI = module {
    viewModel {
        UserInfoViewModel(get(), get())
    }
}

val nutritionViewModelDI = module {
    viewModel {
        NutritionViewModel()
    }
}

val calorieGoalViewModelDI = module {
    viewModel {
        CalorieGoalViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
val appModules = listOf(
    databaseDI,
    macroResultDaoDI,
    userInfoDaoDI,
    calorieGoalDaoDI,
    nutritionRepositoryDI,
    userInfoRepositoryDI,
    calorieGoalRepositoryDI,
    getCalorieGoalWithMacrosUseCaseDI,
    calculateCalorieGoalUseCaseDI,
    calculateMacrosUseCaseDI,
    calculateTmbUseCaseDI,
    getUserInfoUseCaseDI,
    saveUserInfoUseCaseDI,
    saveCalorieGoalUseCaseDI,
    getCalorieGoalUseCaseDI,
    userInfoViewModelDI,
    nutritionViewModelDI,
    calorieGoalViewModelDI,
)
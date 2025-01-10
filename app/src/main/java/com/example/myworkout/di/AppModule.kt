package com.example.myworkout.di

import com.example.myworkout.data.database.AppDatabase
import com.example.myworkout.data.repository.TrainingRepository
import com.example.myworkout.data.repository.TrainingRepositoryImpl
import com.example.myworkout.domain.usecase.TrainingUseCase
import com.example.myworkout.domain.usecase.TrainingUseCaseImpl
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dispatcherDI = module {
    single<Dispatchers> { Dispatchers }
}

val databaseDI = module {
    single {
        AppDatabase.getInstance(get())
    }
}

val trainingDaoDI = module {
    factory {
        get<AppDatabase>().trainingDao()
    }
}

val trainingRepositoryDI = module {
    factory<TrainingRepository> {
        TrainingRepositoryImpl(get())
    }
}

val trainingUseCaseDI = module {
    factory<TrainingUseCase> { TrainingUseCaseImpl(get()) }
}

val trainingViewModelDI = module {
    viewModel { TrainingViewModel(get()) }
}

val appModules = listOf(
    dispatcherDI,
    databaseDI,
    trainingDaoDI,
    trainingRepositoryDI,
    trainingUseCaseDI,
    trainingViewModelDI,
)

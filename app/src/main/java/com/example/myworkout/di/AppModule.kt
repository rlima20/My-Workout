package com.example.myworkout.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myworkout.domain.repository.TrainingRepository
import com.example.myworkout.domain.repository.TrainingRepositoryImpl
import com.example.myworkout.domain.room.database.AppDatabase
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
    factory { get<AppDatabase>().trainingDao() }
}

val muscleGroupDaoDI = module {
    factory { get<AppDatabase>().muscleGroupDao() }
}

val muscleSubGroupDaoDI = module {
    factory { get<AppDatabase>().muscleSubGroupDao() }
}

val muscleGroupMuscleSubGroupDaoDI = module {
    factory { get<AppDatabase>().muscleGroupMuscleSubGroupDao() }
}

val trainingMuscleGroupDaoDI = module {
    factory { get<AppDatabase>().trainingMuscleGroupDao() }
}

val repositoryDI = module {
    factory<TrainingRepository> { TrainingRepositoryImpl(get(), get(), get(), get(), get()) }
}

val trainingUseCaseDI = module {
    factory<TrainingUseCase> { TrainingUseCaseImpl(get()) }
}

@RequiresApi(Build.VERSION_CODES.O)
val muscleViewModelDI = module {
    viewModel { TrainingViewModel(get()) }
}

@RequiresApi(Build.VERSION_CODES.O)
val appModules = listOf(
    dispatcherDI,
    databaseDI,
    trainingDaoDI,
    muscleGroupDaoDI,
    muscleSubGroupDaoDI,
    muscleGroupMuscleSubGroupDaoDI,
    trainingMuscleGroupDaoDI,
    repositoryDI,
    trainingUseCaseDI,
    muscleViewModelDI
)

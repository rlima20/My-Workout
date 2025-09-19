package com.example.myworkout.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myworkout.domain.repository.musclegroup.MuscleGroupRepository
import com.example.myworkout.domain.repository.musclegroup.MuscleGroupRepositoryImpl
import com.example.myworkout.domain.repository.training.TrainingRepository
import com.example.myworkout.domain.repository.training.TrainingRepositoryImpl
import com.example.myworkout.domain.room.database.AppDatabase
import com.example.myworkout.domain.usecase.musclegroup.MuscleGroupUseCase
import com.example.myworkout.domain.usecase.musclegroup.MuscleGroupUseCaseImpl
import com.example.myworkout.domain.usecase.training.TrainingUseCase
import com.example.myworkout.domain.usecase.training.TrainingUseCaseImpl
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val remoteConfigDI = module {
    single {
        Firebase.remoteConfig
    }
}

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

val muscleGroupRepositoryDI = module {
    factory<MuscleGroupRepository> { MuscleGroupRepositoryImpl(get(), get(), get(), get()) }
}

val trainingRepositoryDI = module {
    factory<TrainingRepository> { TrainingRepositoryImpl(get(), get()) }
}

val trainingUseCaseDI = module {
    factory<TrainingUseCase> { TrainingUseCaseImpl(get()) }
}

val muscleGroupUseCaseDI = module {
    factory<MuscleGroupUseCase> { MuscleGroupUseCaseImpl(get()) }
}

@RequiresApi(Build.VERSION_CODES.O)
val trainingViewModelDI = module {
    viewModel { TrainingViewModel(get()) }
}

@RequiresApi(Build.VERSION_CODES.O)
val muscleGroupViewModelDI = module {
    viewModel {
        MuscleGroupViewModel(
            get(),
            get(),
            get()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
val appModules = listOf(
    remoteConfigDI,
    dispatcherDI,
    databaseDI,
    trainingDaoDI,
    muscleGroupDaoDI,
    muscleSubGroupDaoDI,
    muscleGroupMuscleSubGroupDaoDI,
    trainingMuscleGroupDaoDI,
    trainingRepositoryDI,
    muscleGroupRepositoryDI,
    trainingUseCaseDI,
    muscleGroupUseCaseDI,
    trainingViewModelDI,
    muscleGroupViewModelDI
)

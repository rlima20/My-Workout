package com.example.myworkout.di

import android.app.Application
import androidx.room.Room
import com.example.myworkout.data.database.AppDatabase
import com.example.myworkout.data.database.TrainingDao
import com.example.myworkout.data.repository.TrainingRepository
import com.example.myworkout.data.repository.TrainingRepositoryImpl
import com.example.myworkout.domain.usecase.ClearStatusUseCase
import com.example.myworkout.domain.usecase.ClearStatusUseCaseImpl
import com.example.myworkout.domain.usecase.GetTrainingsUseCase
import com.example.myworkout.domain.usecase.GetTrainingsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "my_workout_db").build()
    }


    @Provides
    @Singleton
    fun provideTrainingDao(database: AppDatabase): TrainingDao {
        return database.trainingDao()
    }

    @Provides
    @Singleton
    fun provideTrainingRepository(trainingDao: TrainingDao): TrainingRepository {
        return TrainingRepositoryImpl(trainingDao)
    }

    @Provides
    @Singleton
    fun provideClearStatusUseCases(repository: TrainingRepository): ClearStatusUseCase {
        return ClearStatusUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideGetTrainingsUseCases(repository: TrainingRepository): GetTrainingsUseCase {
        return GetTrainingsUseCaseImpl(repository)
    }
}
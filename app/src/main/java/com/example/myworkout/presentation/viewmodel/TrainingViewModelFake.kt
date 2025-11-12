package com.example.myworkout.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.usecase.training.TrainingUseCase
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import kotlinx.coroutines.Dispatchers

class TrainingUseCaseFake : TrainingUseCase {
    override suspend fun getTrainingDays(): List<DayOfWeek> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainingDaysStatus(): List<Pair<DayOfWeek, Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainings(): List<TrainingModel> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTraining(training: TrainingModel) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTraining(training: TrainingModel) {}

    override suspend fun deleteTraining(training: TrainingModel) {}
    override suspend fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrainingMuscleGroup(trainingId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun clearStatus(
        trainingId: Int,
        status: Status
    ) {
        TODO("Not yet implemented")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class TrainingViewModelFake : TrainingViewModel(
    dispatchers = Dispatchers,
    trainingUseCase = TrainingUseCaseFake()
)
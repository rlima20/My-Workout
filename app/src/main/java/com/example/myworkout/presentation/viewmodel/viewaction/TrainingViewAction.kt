package com.example.myworkout.presentation.viewmodel.viewaction

import com.example.myworkout.domain.model.TrainingModel

sealed class TrainingViewAction {
    object FetchTrainings : TrainingViewAction()
    object NewTraining : TrainingViewAction()
    data class UpdateTraining(val training: TrainingModel): TrainingViewAction ()
    object SetEmptyState : TrainingViewAction()
}
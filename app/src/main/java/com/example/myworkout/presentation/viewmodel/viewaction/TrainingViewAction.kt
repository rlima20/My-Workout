package com.example.myworkout.presentation.viewmodel.viewaction

sealed class TrainingViewAction {
    object FetchTrainings : TrainingViewAction()
    object NewTraining : TrainingViewAction()
    object SetEmptyState : TrainingViewAction()
}
package com.example.myworkout.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.usecase.TrainingUseCase
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class TrainingViewModel(
    private val trainingUseCase: TrainingUseCase
) : ViewModel() {

    private val _databaseOperationStatus = MutableStateFlow(DatabaseState.ISEMPTY)
    val databaseOperationStatus: StateFlow<DatabaseState>
        get() = _databaseOperationStatus

    private val _isHomeScreen = MutableStateFlow(true)
    val isHomeScreen: StateFlow<Boolean>
        get() = _isHomeScreen

    private val _appBarTitle = MutableStateFlow("Home Screen")
    val appBarTitle: StateFlow<String>
        get() = _appBarTitle

    fun setIsHomeScreen(value: Boolean) {
        _isHomeScreen.value = value
    }

    fun setAppBarTitle(value: String) {
        _appBarTitle.value = value
    }

    // Todo - verificar se vou usar esse viewState realmente
    private val _viewState = MutableStateFlow(TrainingViewState())
    val viewState: StateFlow<TrainingViewState>
        get() = _viewState

    val listOfMuscleSubGroups: MutableStateFlow<List<MuscleSubGroupModel>> =
        MutableStateFlow(listOf())

    val listOfTrainings: MutableStateFlow<List<TrainingModel>> = MutableStateFlow(listOf())

    fun setupDatabase(isFirstInstall: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            _databaseOperationStatus.value = DatabaseState.ISLOADING
            delay(1000)
            try {
                viewModelScope.launch(Dispatchers.Main) {
                    if (isFirstInstall) {
                        createTrainingAAndRelationships()
                        createTrainingBAndRelationships()
                    }
                    _databaseOperationStatus.value = DatabaseState.SUCCESS
                }
            } catch (e: Exception) {
                _databaseOperationStatus.value = DatabaseState.ERROR
            }
        }
    }

    private fun createTrainingAAndRelationships() {
        insertTraining(
            TrainingModel(
                status = Status.PENDING,
                dayOfWeek = DayOfWeek.SUNDAY,
                trainingName = "Treino A"
            )
        )

        insertMuscleGroup(
            MuscleGroupModel(
                name = "Ombro"
            )
        )

        insertMuscleSubGroup(
            MuscleSubGroupModel(
                name = "Posterior"
            )
        )

        insertMuscleSubGroup(
            MuscleSubGroupModel(
                name = "Anterior"
            )
        )

        insertMuscleSubGroup(
            MuscleSubGroupModel(
                name = "Lateral"
            )
        )

        insertTrainingMuscleGroup(
            TrainingMuscleGroupModel(
                trainingId = 1,
                muscleGroupId = 1
            )
        )

        for (i in 1..3) {
            insertMuscleGroupMuscleSubGroup(
                MuscleGroupMuscleSubGroupModel(
                    muscleGroupId = 1,
                    muscleSubGroupId = i
                )
            )
        }
    }

    private fun createTrainingBAndRelationships() {
        insertTraining(
            TrainingModel(
                status = Status.PENDING,
                dayOfWeek = DayOfWeek.MONDAY,
                trainingName = "Treino B"
            )
        )
        insertMuscleGroup(
            MuscleGroupModel(
                name = "Peito"
            )
        )
        insertMuscleSubGroup(
            MuscleSubGroupModel(
                name = "Supino Reto"
            )
        )
        insertMuscleSubGroup(
            MuscleSubGroupModel(
                name = "Máquina x"
            )
        )
        insertMuscleSubGroup(
            MuscleSubGroupModel(
                name = "Supino inclinado"
            )
        )

        insertTrainingMuscleGroup(
            TrainingMuscleGroupModel(
                trainingId = 2,
                muscleGroupId = 2
            )
        )

        for (i in 3..6) {
            insertMuscleGroupMuscleSubGroup(
                MuscleGroupMuscleSubGroupModel(
                    muscleGroupId = 2,
                    muscleSubGroupId = i
                )
            )
        }
    }


    //
    //                        )
    //                        insertTraining(
    //                            TrainingModel(
    //                                status = Status.PENDING,
    //                                dayOfWeek = DayOfWeek.TUESDAY,
    //                                trainingName = "Treino C"
    //                            )
    //                        )
    //                        insertTraining(
    //                            TrainingModel(
    //                                status = Status.PENDING,
    //                                dayOfWeek = DayOfWeek.WEDNESDAY,
    //                                trainingName = "Treino D"
    //                            )
    //                        )
    //                        insertMuscleGroup(
    //                            MuscleGroupModel(
    //                                name = "Peito"
    //                            )
    //                        )
    //                        insertMuscleGroup(
    //                            MuscleGroupModel(
    //                                name = "Bíceps"
    //                            )
    //                        )
    //                        insertMuscleGroup(
    //                            MuscleGroupModel(
    //                                name = "Tríceps"
    //                            )
    //                        )
    //                        insertMuscleGroup(
    //                            MuscleGroupModel(
    //                                name = "Pernas"
    //                            )
    //                        )
    //                        insertMuscleGroup(
    //                            MuscleGroupModel(
    //                                name = "Abdômen"
    //                            )
    //                        )

    fun fetchTrainings() {
        _viewState.value = TrainingViewState(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val trainings = trainingUseCase.getTrainings()
                setListOfTrainings(trainings)
            } catch (e: Exception) {
                Log.e("RAPHAEL", "Erro: $e")
            }
        }
    }

    private fun setListOfTrainings(value: List<TrainingModel>) {
        listOfTrainings.value = value
    }

    fun getMuscleSubGroupsForTraining(trainingId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val value = trainingUseCase.getMuscleSubGroupsForTraining(trainingId)
            updateMuscleSubGroups(value)
        }
    }

    private fun updateMuscleSubGroups(newList: List<MuscleSubGroupModel>) {
        listOfMuscleSubGroups.value = newList
    }

    private fun insertTraining(training: TrainingModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertTraining(training)
        }
    }

    private fun insertMuscleGroup(muscleGroup: MuscleGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertMuscleGroup(muscleGroup)
        }
    }

    private fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertMuscleSubGroup(muscleSubGroup)
        }
    }

    private fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertTrainingMuscleGroup(trainingMuscleGroup)
        }
    }

    private fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup)
        }
    }

    sealed class TrainingIntent {
        object FetchTrainings : TrainingIntent()
        data class SaveTraining(val training: TrainingModel) : TrainingIntent()
        data class ClearStatus(val trainingId: Int, val status: Status) : TrainingIntent()
    }

    fun processIntent(intent: TrainingIntent) {
        when (intent) {
            is TrainingIntent.FetchTrainings -> fetchTrainings()
            is TrainingIntent.SaveTraining -> saveTraining(intent.training)
            is TrainingIntent.ClearStatus -> clearStatus(intent.trainingId, intent.status)
        }
    }

    private fun saveTraining(training: TrainingModel) {
        viewModelScope.launch {
            trainingUseCase.insertTraining(training)
            // fetchTrainings()
        }
    }

    private fun clearStatus(trainingId: Int, status: Status) {
        viewModelScope.launch {
            trainingUseCase.clearStatus(trainingId, status)
            // fetchTrainings()
        }
    }
}
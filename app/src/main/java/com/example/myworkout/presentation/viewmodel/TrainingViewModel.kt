package com.example.myworkout.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.usecase.training.TrainingUseCase
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel.Companion.EXCEPTION
import com.example.myworkout.presentation.viewmodel.viewstate.TrainingViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
open class TrainingViewModel(
    private val dispatchers: Dispatchers,
    private val trainingUseCase: TrainingUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<TrainingViewState> =
        MutableStateFlow(TrainingViewState.Empty)
    val viewState: StateFlow<TrainingViewState> get() = _viewState

    private val _trainings: MutableStateFlow<List<TrainingModel>> = MutableStateFlow(listOf())
    val trainings: StateFlow<List<TrainingModel>> get() = _trainings

    private val _isHomeScreen = MutableStateFlow(true)
    val isHomeScreen: StateFlow<Boolean> get() = _isHomeScreen

    private val _isHomeScreenV2 = MutableStateFlow(false)
    val isHomeScreenV2: StateFlow<Boolean> get() = _isHomeScreenV2

    private val _appBarTitle = MutableStateFlow("Home")
    val appBarTitle: StateFlow<String> get() = _appBarTitle

    private val _trainingName = MutableStateFlow("")
    val trainingName: StateFlow<String> = _trainingName

    private val _listOfDays = MutableStateFlow(
        listOf(
            Pair(DayOfWeek.SATURDAY, true)
        )
    )

    fun setHomeScreenV2 (value: Boolean){
        _isHomeScreenV2.value = value
    }

    val listOfDays: StateFlow<List<Pair<DayOfWeek, Boolean>>> = _listOfDays

    private fun updateListOfDays() {
        val trainingDays = trainings.value.map { it.dayOfWeek } // Domingo, Segunda

        _listOfDays.value = DayOfWeek.values().map { day ->
            Pair(day, trainingDays.contains(day))
        }
    }

    fun updateTrainingName(value: String) {
        _trainingName.value = value
    }

    private suspend fun fetchTrainingsInternal() {
        val trainings = trainingUseCase.getTrainings()
        _trainings.value = trainings
    }

    private fun updateListOfDaysInternal() {
        viewModelScope.launch(dispatchers.IO) {
            try {
                val daysStatus = trainingUseCase.getTrainingDaysStatus()
                _listOfDays.value = daysStatus
            } catch (exception: Exception) {
                setErrorState(exception.message.toString(), null)
            }
        }
    }

    fun updateTraining(trainingModel: TrainingModel) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingState()
            try {

                trainingUseCase.updateTraining(trainingModel)

                // Executa ambos em paralelo
                val jobs = listOf(
                    async { fetchTrainingsInternal() },
                    async { updateListOfDaysInternal() }
                )

                jobs.awaitAll()

                setSuccessState(_trainings.value)

            } catch (exception: Exception) {
                setErrorState(exception.message.toString(), null)
            }
        }
    }

    fun deleteTraining(trainingModel: TrainingModel) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingState()
            try {

                trainingUseCase.deleteTraining(trainingModel)
                trainingUseCase.deleteTrainingMuscleGroup(trainingModel.trainingId)

                // Executa ambos em paralelo
                val jobs = listOf(
                    async { fetchTrainingsInternal() },
                    async { updateListOfDaysInternal() }
                )

                jobs.awaitAll()

                setSuccessState(_trainings.value)

            } catch (exception: Exception) {
                setErrorState(exception.message.toString(), null)
            }
        }
    }

    fun setEmptyState() {
        _viewState.value = TrainingViewState.Empty
    }

    fun fetchTrainings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val trainings = trainingUseCase.getTrainings()
                setListOfTrainings(trainings)
                updateListOfDays()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString(), null)
            }
        }
    }

    fun setListOfTrainings(data: List<TrainingModel>) {
        verifyEmptyList(data)
    }

    fun verifyEmptyList(data: List<TrainingModel>) {
        if (data.isEmpty()) setEmptyState()
        else setSuccessState(data)
    }

    fun setSuccessState(trainings: List<TrainingModel>) {
        _trainings.value = trainings
        _viewState.value = TrainingViewState.Success(trainings)
        updateListOfDays()
    }

    fun setErrorState(exception: String, trainingName: String?) {
        Log.e(EXCEPTION, exception)
        _viewState.value = TrainingViewState.Error(trainingName)
    }

    fun setLoadingState() {
        _viewState.value = TrainingViewState.Loading
    }

    fun setIsHomeScreen(value: Boolean) {
        _isHomeScreen.value = value
    }

    fun setAppBarTitle(value: String) {
        _appBarTitle.value = value
    }

    fun clearStatus(trainingId: Int, status: Status) {
        viewModelScope.launch {
            trainingUseCase.clearStatus(trainingId, status)
            fetchTrainings()
        }
    }

    fun insertTraining(
        training: TrainingModel,
        groupId: Int
    ) {
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                performInsertTrainingAndGetId(training)?.let { insertedTrainingId ->
                    performInsertTrainingMuscleGroup(insertedTrainingId, groupId)
                    setSuccessState(_trainings.value)
                }
            } catch (exception: Exception) {
                setErrorState(exception.message.toString(), null)
            }
        }
    }

    /**
     * Responsável apenas por inserir o treino e retornar seu ID.
     */
    private suspend fun performInsertTrainingAndGetId(training: TrainingModel): Int? {
        trainingUseCase.insertTraining(training)
        val updatedTrainings = fetchAndReturnTrainings()
        return getLastInsertedTrainingId(updatedTrainings)
    }


    /**
     * Busca e atualiza a lista de treinos no estado atual.
     */
    private suspend fun fetchAndReturnTrainings(): List<TrainingModel> {
        val trainings = trainingUseCase.getTrainings()
        setListOfTrainings(trainings)
        return trainings
    }

    /**
     * Obtém o ID do último treino inserido com segurança.
     */
    private fun getLastInsertedTrainingId(trainings: List<TrainingModel>): Int {
        return trainings.maxByOrNull { it.trainingId }?.trainingId
            ?: throw IllegalStateException("Falha ao obter o ID do treinamento recém inserido")
    }

    /**
     * Responsável apenas por criar a relação entre treino e grupo muscular.
     */
    private suspend fun performInsertTrainingMuscleGroup(trainingId: Int, groupId: Int) {
        trainingUseCase.insertTrainingMuscleGroup(
            TrainingMuscleGroupModel(
                trainingId = trainingId,
                muscleGroupId = groupId
            )
        )
    }
}
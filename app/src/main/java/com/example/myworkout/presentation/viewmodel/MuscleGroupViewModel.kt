package com.example.myworkout.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.Constants.Companion.MUSCLE_SUB_GROUP_NAMES
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.usecase.musclegroup.MuscleGroupUseCase
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.preferences.TrainingPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class MuscleGroupViewState {
    object Loading : MuscleGroupViewState()
    object Error : MuscleGroupViewState()
    object Success : MuscleGroupViewState()
    object DatabaseCreated : MuscleGroupViewState()
}

class MuscleGroupViewModel(
    private val muscleGroupUseCase: MuscleGroupUseCase,
    private val dispatchers: Dispatchers
) : ViewModel() {

    private val _viewState: MutableStateFlow<MuscleGroupViewState> =
        MutableStateFlow(MuscleGroupViewState.Loading)
    val viewState: StateFlow<MuscleGroupViewState> get() = _viewState

    private val _muscleGroups: MutableStateFlow<List<MuscleGroupModel>> = MutableStateFlow(listOf())
    val muscleGroups: StateFlow<List<MuscleGroupModel>> get() = _muscleGroups

    private val _muscleGroupsWithRelation: MutableStateFlow<List<MuscleGroupModel>> =
        MutableStateFlow(listOf())
    val muscleGroupsWithRelation: StateFlow<List<MuscleGroupModel>> get() = _muscleGroupsWithRelation

    private val _muscleSubGroups: MutableStateFlow<List<MuscleSubGroupModel>> =
        MutableStateFlow(listOf())
    val muscleSubGroups: StateFlow<List<MuscleSubGroupModel>> get() = _muscleSubGroups

    private val _muscleSubGroupsByTraining: MutableStateFlow<List<MuscleSubGroupModel>> =
        MutableStateFlow(listOf())
    val muscleSubGroupsByTraining: StateFlow<List<MuscleSubGroupModel>> get() = _muscleSubGroupsByTraining

    private val _relations: MutableStateFlow<List<MuscleGroupMuscleSubGroupModel>> =
        MutableStateFlow(listOf())
    val relations: StateFlow<List<MuscleGroupMuscleSubGroupModel>> get() = _relations

    private val _objSelected: MutableStateFlow<Pair<Int, Boolean>> =
        MutableStateFlow(Pair(0, false))
    val objSelected: StateFlow<Pair<Int, Boolean>> get() = _objSelected

    private val _workouts: MutableStateFlow<List<Pair<TrainingModel, List<MuscleSubGroupModel>>>> =
        MutableStateFlow(emptyList())
    val workouts: StateFlow<List<Pair<TrainingModel, List<MuscleSubGroupModel>>>> = _workouts

    fun getGroupsWithRelations() {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val groupsWithRelation = muscleGroupUseCase.getMuscleGroupsWithRelations()
                _muscleGroupsWithRelation.value = groupsWithRelation
                delay(5000)
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun fetchMuscleGroups() {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val muscleGroups = muscleGroupUseCase.getMuscleGroups()
                _muscleGroups.value = muscleGroups
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun fetchMuscleSubGroups() {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val muscleSubGroups = muscleGroupUseCase.getMuscleSubGroups()
                _muscleSubGroups.value = muscleSubGroups
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun fetchWorkouts(trainings: List<TrainingModel>) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val workouts = trainings.map { training ->
                    val subGroups =
                        muscleGroupUseCase.getMuscleSubGroupsByTrainingId(training.trainingId)
                    training to subGroups
                }
                _workouts.value = workouts // ✅ cria uma nova lista (reativa)
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }


    fun insertMuscleGroupMuscleSubGroup(
        muscleGroupMuscleSubGroups: MutableList<MuscleGroupMuscleSubGroupModel>
    ) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupMuscleSubGroups.forEach {
                    muscleGroupUseCase.insertMuscleGroupMuscleSubGroup(it)
                }
                // Configuro o valor inicial
                _objSelected.value = Pair(0, false)
                setSuccessState()
                getGroupsWithRelations()
                clearSubGroups()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun createInitialDatabase(isFirstInstall: Boolean) {
        if (isFirstInstall) {
            viewModelScope.launch(dispatchers.IO) {
                setLoadingState()
                try {
                    insertMuscleSubGroups()
                    _viewState.value = MuscleGroupViewState.DatabaseCreated
                } catch (exception: Exception) {
                    setErrorState(exception.message.toString())
                }
            }
        } else {
            fetchMuscleGroups()
            fetchMuscleSubGroups()
            getGroupsWithRelations()
        }
    }

    private fun insertMuscleSubGroups() {
        MUSCLE_SUB_GROUP_NAMES.forEachIndexed { index, name ->
            insertMuscleSubGroup(
                MuscleSubGroupModel(
                    id = index + 1,
                    name = name
                )
            )
        }
    }

    fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.insertMuscleSubGroup(muscleSubGroup)
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun insertMuscleGroup(name: String, image: BodyPart) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.insertMuscleGroup(name, image)
                fetchMuscleGroups()
                fetchMuscleSubGroups()
                clearSubGroups()
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun clearSubGroups() {
        val subGroupsSelected: List<MuscleSubGroupModel> =
            _muscleSubGroups.value.filter { it.selected }
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.clearSelectedMuscleSubGroups(subGroupsSelected)
                fetchMuscleSubGroups()
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun updateSubGroup(subGroup: MuscleSubGroupModel) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.updateSubGroup(subGroup)
                fetchMuscleSubGroups()
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun setMuscleGroupSelected(objSelected: Pair<Int, Boolean>) {
        _objSelected.value = objSelected
    }

    private fun setSuccessState() {
        Log.e(SUCCESS, SUCCESS)
        _viewState.value = MuscleGroupViewState.Success
    }

    private fun setLoadingState() {
        Log.e(LOADING, LOADING)
        _viewState.value = MuscleGroupViewState.Loading
    }

    fun setErrorState(exception: String) {
        Log.e(EXCEPTION, exception)
        _viewState.value = MuscleGroupViewState.Error
    }

    companion object {
        const val EXCEPTION = "Exception"
        const val SUCCESS = "Success"
        const val LOADING = "Loading"
    }
}
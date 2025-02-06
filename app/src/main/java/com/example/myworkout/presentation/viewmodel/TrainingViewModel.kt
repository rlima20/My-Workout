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

    // Todo - verificar se vou usar esse viewState realmente
    private val _viewState = MutableStateFlow(TrainingViewState())
    val viewState: StateFlow<TrainingViewState>
        get() = _viewState

    private val _databaseOperationStatus = MutableStateFlow(DatabaseState.EMPTY)
    val databaseOperationStatus: StateFlow<DatabaseState>
        get() = _databaseOperationStatus

    private val _isHomeScreen = MutableStateFlow(true)
    val isHomeScreen: StateFlow<Boolean>
        get() = _isHomeScreen

    private val _appBarTitle = MutableStateFlow("Home Screen")
    val appBarTitle: StateFlow<String>
        get() = _appBarTitle

    val listOfMuscleSubGroups: MutableStateFlow<List<MuscleSubGroupModel>> =
        MutableStateFlow(listOf())

    val listOfTrainings: MutableStateFlow<List<TrainingModel>> = MutableStateFlow(listOf())

    fun setIsHomeScreen(value: Boolean) {
        _isHomeScreen.value = value
    }

    fun setAppBarTitle(value: String) {
        _appBarTitle.value = value
    }

    fun setupDatabase(isFirstInstall: Boolean) {
        if (isFirstInstall) {
            CoroutineScope(Dispatchers.Main).launch {
                _databaseOperationStatus.value = DatabaseState.LOADING
                try {
                    viewModelScope.launch(Dispatchers.Main) {
                        createDatabase()
                        delay(2000)
                        _databaseOperationStatus.value = DatabaseState.EMPTY
                    }
                } catch (e: Exception) {
                    _databaseOperationStatus.value = DatabaseState.ERROR
                }
            }
        }
    }

    private suspend fun createDatabase() {
        createBack()
        delay(2000)
        createShoulder()
        delay(2000)
        createArm()
        delay(2000)
        createAbdomen()
        delay(2000)
        createChest()
        delay(2000)
        createLegs()
        delay(2000)
        createTrapezius()
        delay(2000)
    }

    private fun createBack() {
        insertMuscleGroup(MuscleGroupModel(muscleGroupId = 1, name = "Costas"))
        createMuscleSubGroupBack()
        createMuscleGroupMuscleSubGroupBackRelationship()
    }

    private fun createMuscleSubGroupBack() {
        insertMuscleSubGroup(MuscleSubGroupModel(id = 1, name = "Superior"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 2, name = "Dorsal"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 3, name = "Inferior"))
    }

    private fun createMuscleGroupMuscleSubGroupBackRelationship() {
        for (i in 1..3) {
            insertMuscleGroupMuscleSubGroup(
                MuscleGroupMuscleSubGroupModel(
                    muscleGroupId = 1, // ID do grupo Back
                    muscleSubGroupId = i
                )
            )
        }
    }

    private fun createShoulder() {
        insertMuscleGroup(MuscleGroupModel(muscleGroupId = 2, name = "Ombro"))
        createMuscleSubGroupShoulder()
        createMuscleGroupMuscleSubGroupShoulderRelationship()
    }

    private fun createMuscleSubGroupShoulder() {
        insertMuscleSubGroup(MuscleSubGroupModel(id = 4, name = "Posterior"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 5, name = "Anterior"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 6, name = "Lateral"))
    }

    private fun createMuscleGroupMuscleSubGroupShoulderRelationship() {
        for (i in 4..6) {
            insertMuscleGroupMuscleSubGroup(
                MuscleGroupMuscleSubGroupModel(
                    muscleGroupId = 2, // ID do grupo Shoulder
                    muscleSubGroupId = i
                )
            )
        }
    }

    private fun createArm() {
        insertMuscleGroup(MuscleGroupModel(muscleGroupId = 3, name = "Braço"))
        createMuscleSubGroupArm()
        createMuscleGroupMuscleSubGroupArmRelationship()
    }

    private fun createMuscleSubGroupArm() {
        insertMuscleSubGroup(MuscleSubGroupModel(id = 7, name = "Bíceps"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 8, name = "Antebraço"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 9, name = "Tríceps"))
    }

    private fun createMuscleGroupMuscleSubGroupArmRelationship() {
        for (i in 7..9) {
            insertMuscleGroupMuscleSubGroup(
                MuscleGroupMuscleSubGroupModel(
                    muscleGroupId = 3, // ID do grupo Arm
                    muscleSubGroupId = i
                )
            )
        }
    }

    private fun createAbdomen() {
        insertMuscleGroup(MuscleGroupModel(muscleGroupId = 4, name = "Abdomem"))
        createMuscleSubGroupAbdomen()
        createMuscleGroupMuscleSubGroupAbdomenRelationship()
    }

    private fun createMuscleSubGroupAbdomen() {
        insertMuscleSubGroup(MuscleSubGroupModel(id = 10, name = "Superior"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 11, name = "Inferior"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 12, name = "Lateral"))
    }

    private fun createMuscleGroupMuscleSubGroupAbdomenRelationship() {
        for (i in 10..12) {
            insertMuscleGroupMuscleSubGroup(
                MuscleGroupMuscleSubGroupModel(
                    muscleGroupId = 4, // ID do grupo Abdomen
                    muscleSubGroupId = i
                )
            )
        }
    }

    private fun createChest() {
        insertMuscleGroup(MuscleGroupModel(muscleGroupId = 5, name = "Peito"))
        createMuscleSubGroupChest()
        createMuscleGroupMuscleSubGroupChestRelationship()
    }

    private fun createMuscleSubGroupChest() {
        insertMuscleSubGroup(MuscleSubGroupModel(id = 13, name = "Superior"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 14, name = "Reto"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 15, name = "Medial"))
    }

    private fun createMuscleGroupMuscleSubGroupChestRelationship() {
        for (i in 13..15) {
            insertMuscleGroupMuscleSubGroup(
                MuscleGroupMuscleSubGroupModel(
                    muscleGroupId = 5, // ID do grupo Chest
                    muscleSubGroupId = i
                )
            )
        }
    }

    private fun createLegs() {
        insertMuscleGroup(MuscleGroupModel(muscleGroupId = 6, name = "Pernas"))
        createMuscleSubGroupLegs()
        createMuscleGroupMuscleSubGroupLegsRelationship()
    }

    private fun createMuscleSubGroupLegs() {
        insertMuscleSubGroup(MuscleSubGroupModel(id = 16, name = "Quadríceps"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 17, name = "Posterior"))
        insertMuscleSubGroup(MuscleSubGroupModel(id = 18, name = "Panturrilhas"))
    }

    private fun createMuscleGroupMuscleSubGroupLegsRelationship() {
        for (i in 16..18) {
            insertMuscleGroupMuscleSubGroup(
                MuscleGroupMuscleSubGroupModel(
                    muscleGroupId = 6, // ID do grupo Legs
                    muscleSubGroupId = i
                )
            )
        }
    }

    private fun createTrapezius() {
        insertMuscleGroup(MuscleGroupModel(muscleGroupId = 7, name = "Trapézio"))
        createMuscleSubGroupTrapezius()
        createMuscleGroupMuscleSubGroupTrapeziusRelationship()
    }

    private fun createMuscleSubGroupTrapezius() {
        insertMuscleSubGroup(MuscleSubGroupModel(id = 19, name = "Superior"))
    }

    private fun createMuscleGroupMuscleSubGroupTrapeziusRelationship() {
        insertMuscleGroupMuscleSubGroup(
            MuscleGroupMuscleSubGroupModel(
                muscleGroupId = 7, // ID do grupo Trapezius
                muscleSubGroupId = 19 // ID do subgrupo Upper
            )
        )
    }

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
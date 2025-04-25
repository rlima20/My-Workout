package com.example.myworkout.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.usecase.musclegroup.MuscleGroupUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MuscleGroupViewModel(
    private val muscleGroupUseCase: MuscleGroupUseCase
) : ViewModel() {

    private val _muscleGroupViewState: MutableStateFlow<MuscleGroupViewState> =
        MutableStateFlow(MuscleGroupViewState.InitialState)
    val muscleGroupViewState: StateFlow<MuscleGroupViewState>
        get() = _muscleGroupViewState

    val listOfMuscleGroups: MutableStateFlow<List<MuscleGroupModel>> = MutableStateFlow(listOf())

    val listOfMuscleSubGroups: MutableStateFlow<List<MuscleSubGroupModel>> =
        MutableStateFlow(listOf())

    fun dispatchViewAction(viewAction: MuscleGroupViewAction) {
        when (viewAction) {
            is MuscleGroupViewAction.SetupDatabase -> {
                setupDatabase(viewAction.isFirstInstall)
            }
        }
    }

    private fun setupDatabase(isFirstInstall: Boolean) {
        if (isFirstInstall) {
            CoroutineScope(Dispatchers.Main).launch {
                _muscleGroupViewState.value = MuscleGroupViewState.Loading
                try {
                    viewModelScope.launch(Dispatchers.Main) {
                        createDatabase()
                        delay(2000)
                        _muscleGroupViewState.value = MuscleGroupViewState.Success
                    }
                } catch (e: Exception) {
                    _muscleGroupViewState.value = MuscleGroupViewState.Error
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
        insertMuscleGroup(
            MuscleGroupModel(
                muscleGroupId = 1,
                name = "Costas",
                image = R.drawable.costas
            )
        )
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
        insertMuscleGroup(
            MuscleGroupModel(
                muscleGroupId = 2,
                name = "Ombro",
                image = R.drawable.ombro
            )
        )
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
        insertMuscleGroup(
            MuscleGroupModel(
                muscleGroupId = 3,
                name = "Braço",
                image = R.drawable.braco
            )
        )
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
        insertMuscleGroup(
            MuscleGroupModel(
                muscleGroupId = 4,
                name = "Abdomem",
                image = R.drawable.abdomem
            )
        )
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
        insertMuscleGroup(
            MuscleGroupModel(
                muscleGroupId = 5,
                name = "Peito",
                image = R.drawable.peito
            )
        )
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
        insertMuscleGroup(
            MuscleGroupModel(
                muscleGroupId = 6,
                name = "Pernas",
                image = R.drawable.pernas
            )
        )
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
        insertMuscleGroup(
            MuscleGroupModel(
                muscleGroupId = 7,
                name = "Trapézio",
                image = R.drawable.trapezio
            )
        )
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

    private fun updateMuscleSubGroups(newList: List<MuscleSubGroupModel>) {
        listOfMuscleSubGroups.value = newList
    }

    fun fetchMuscleGroups() {
        _muscleGroupViewState.value = MuscleGroupViewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val muscleGroups = muscleGroupUseCase.getMuscleGroups()
                setListOfMuscleGroups(muscleGroups)
//                _muscleGroupViewState.value = MuscleGroupViewState.Success
            } catch (e: Exception) {
                Log.e("RAPHAEL", "Erro: $e")
            }
        }
    }

    private fun setListOfMuscleGroups(value: List<MuscleGroupModel>) {
        listOfMuscleGroups.value = value
    }

    fun getMuscleSubGroupsForTraining(trainingId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val value = muscleGroupUseCase.getMuscleSubGroupsForTraining(trainingId)
            updateMuscleSubGroups(value)
        }
    }

    private fun insertMuscleGroup(muscleGroup: MuscleGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            muscleGroupUseCase.insertMuscleGroup(muscleGroup)
        }
    }

    private fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            muscleGroupUseCase.insertMuscleSubGroup(muscleSubGroup)
        }
    }

    private fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            muscleGroupUseCase.insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup)
        }
    }
}
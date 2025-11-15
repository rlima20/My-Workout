package com.example.myworkout.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myworkout.domain.model.GroupSubGroupModel
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.usecase.musclegroup.MuscleGroupUseCase
import com.example.myworkout.enums.BodyPart
import kotlinx.coroutines.Dispatchers

class MuscleGroupUseCaseFake : MuscleGroupUseCase {

    override suspend fun getMuscleGroups(): List<MuscleGroupModel> {
        return emptyList()
    }

    override suspend fun getMuscleSubGroups(): List<MuscleSubGroupModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubGroups(): List<SubGroupModel> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSubGroup(subGroup: MuscleSubGroupModel) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNewSubGroup(subGroup: SubGroupModel) {
        TODO("Not yet implemented")
    }

    override suspend fun updateGroup(group: MuscleGroupModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getRelationById(muscleGroupId: Int): List<MuscleGroupMuscleSubGroupEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRelations(): List<MuscleGroupMuscleSubGroupModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getMuscleGroupsWithRelations(): List<MuscleGroupModel> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMuscleGroup(
        name: String,
        image: BodyPart
    ): MuscleGroupModel {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGroup(group: MuscleGroupModel) {
        TODO("Not yet implemented")
    }

    override suspend fun clearSelectedMuscleSubGroups(subGroups: List<MuscleSubGroupModel>) {
        TODO("Not yet implemented")
    }

    override suspend fun replaceRelationsForGroup(
        muscleGroupId: Int,
        newRelations: List<MuscleGroupMuscleSubGroupModel>
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun replaceNewRelationsForGroup(
        muscleGroupId: Int,
        newRelations: List<GroupSubGroupModel>
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGroupCascade(group: MuscleGroupModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getMuscleSubGroupsByTrainingId(trainingId: Int): List<MuscleSubGroupModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubGroupsGroupedByMuscleGroups(): Map<MuscleGroupModel, List<MuscleSubGroupModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubgroupsById(id: Int): List<MuscleSubGroupModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubgroupById(id: Int): MuscleSubGroupModel {
        TODO("Not yet implemented")
    }

    override suspend fun getSubGroupIdFromRelation(id: Int): List<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        // no-op
    }

    override suspend fun insertSubGroup(subGroup: SubGroupModel) {
        TODO("Not yet implemented")
    }

    override suspend fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel) {
        TODO("Not yet implemented")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class MuscleGroupViewModelFake : MuscleGroupViewModel(
    muscleGroupUseCase = MuscleGroupUseCaseFake(),
    muscleSubGroupUseCase = MuscleGroupUseCaseFake(),  // importante!
    dispatchers = Dispatchers
)
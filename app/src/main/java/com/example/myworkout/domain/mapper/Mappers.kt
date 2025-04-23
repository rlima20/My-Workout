package com.example.myworkout.domain.mapper

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity

fun TrainingModel.toEntity(): TrainingEntity {
    return TrainingEntity(
        trainingId = this.trainingId,
        status = this.status,
        dayOfWeek = this.dayOfWeek,
        trainingName = this.trainingName
    )
}

fun TrainingEntity.toModel(): TrainingModel {
    return TrainingModel(
        trainingId = this.trainingId,
        status = this.status,
        dayOfWeek = this.dayOfWeek,
        trainingName = this.trainingName
    )
}

fun MuscleGroupModel.toEntity(): MuscleGroupEntity {
    return MuscleGroupEntity(
        muscleGroupId = this.muscleGroupId,
        name = this.name,
        image = this.image
    )
}

fun MuscleSubGroupEntity.toModel(): MuscleSubGroupModel {
    return MuscleSubGroupModel(
        id = this.muscleSubGroupId,
        name = this.name,
        selected = this.selected
    )
}

fun MuscleSubGroupModel.toEntity(): MuscleSubGroupEntity {
    return MuscleSubGroupEntity(
        muscleSubGroupId = this.id,
        name = this.name,
        selected = this.selected
    )
}

fun TrainingMuscleGroupModel.toEntity(): TrainingMuscleGroupEntity {
    return TrainingMuscleGroupEntity(
        trainingId = this.trainingId,
        muscleGroupId = this.muscleGroupId
    )
}

fun MuscleGroupMuscleSubGroupEntity.toModel(): MuscleGroupMuscleSubGroupModel {
    return MuscleGroupMuscleSubGroupModel(
        muscleGroupId = this.muscleGroupId,
        muscleSubGroupId = this.muscleSubGroupId
    )
}

fun MuscleGroupMuscleSubGroupModel.toEntity(): MuscleGroupMuscleSubGroupEntity {
    return MuscleGroupMuscleSubGroupEntity(
        muscleGroupId = this.muscleGroupId,
        muscleSubGroupId = this.muscleSubGroupId
    )
}

fun List<TrainingModel>.toEntityList(): List<TrainingEntity> {
    return this.map { trainingModel ->
        TrainingEntity(
            trainingId = trainingModel.trainingId,
            status = trainingModel.status,
            dayOfWeek = trainingModel.dayOfWeek
        )
    }
}

fun List<TrainingEntity>.toModelTrainingList(): List<TrainingModel> {
    return this.map { trainingEntity ->
        TrainingModel(
            trainingId = trainingEntity.trainingId,
            status = trainingEntity.status,
            dayOfWeek = trainingEntity.dayOfWeek,
            trainingName = trainingEntity.trainingName
        )
    }
}

fun List<MuscleGroupEntity>.toModelMuscleGroupList(): List<MuscleGroupModel> {
    return this.map { muscleGroupEntity ->
        MuscleGroupModel(
            muscleGroupId = muscleGroupEntity.muscleGroupId,
            name = muscleGroupEntity.name,
            image = muscleGroupEntity.image
        )
    }
}
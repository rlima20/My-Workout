package com.example.myworkout.domain.mapper

import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingEntity

fun TrainingModel.toEntity(): TrainingEntity{
    return TrainingEntity(
        trainingId = this.trainingId,
        status = this.status,
        dayOfWeek = this.dayOfWeek
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

fun MuscleSubGroupEntity.toModel(): MuscleSubGroupModel {
    return MuscleSubGroupModel(
        id = this.muscleSubGroupId,
        name = this.name
    )
}

fun MuscleSubGroupModel.toEntity(): MuscleSubGroupEntity {
    return MuscleSubGroupEntity(
        muscleSubGroupId = this.id,
        name = this.name
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
            dayOfWeek = trainingModel.dayOfWeek // Descomente se precisar incluir
        )
    }
}

fun List<TrainingEntity>.toModelList(): List<TrainingModel> {
    return this.map { trainingEntity ->
        TrainingModel(
            trainingId = trainingEntity.trainingId,
            status = trainingEntity.status,
            dayOfWeek = trainingEntity.dayOfWeek,
            trainingName = trainingEntity.trainingName
        )
    }
}
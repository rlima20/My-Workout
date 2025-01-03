package com.example.myworkout.utils

import com.example.myworkout.data.model.Status

fun setStatus(
    isTrainingChecked: Boolean,
    trainingStatus: Status,
    firstStatus: Status
) = if (isTrainingChecked) {
    Status.ACHIEVED
} else {
    when (trainingStatus) {
        Status.MISSED -> { firstStatus }
        Status.EMPTY -> { Status.EMPTY }
        else -> { Status.PENDING }
    }
}
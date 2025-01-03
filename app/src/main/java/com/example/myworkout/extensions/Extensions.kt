package com.example.myworkout.extensions

import androidx.compose.runtime.Composable
import com.example.myworkout.R
import com.example.myworkout.data.model.Status

@Composable
fun Status.setBackGroundColor(): Int =
    when (this) {
        Status.PENDING -> R.color.pending
        Status.ACHIEVED -> R.color.achieved
        Status.MISSED -> R.color.missed
        Status.EMPTY -> R.color.empty
    }

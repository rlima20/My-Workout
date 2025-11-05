package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.runtime.Composable
import com.example.myworkout.R

sealed class Action {
    abstract val title: Int
    abstract val onConfirm: () -> Unit
    abstract val content: @Composable () -> Unit

    data class Edit(
        override val onConfirm: () -> Unit,
        override val content: @Composable () -> Unit = {},
        override val title: Int = R.string.delete_group
    ) : Action()

    data class Delete(
        override val onConfirm: () -> Unit,
        override val content: @Composable () -> Unit = {},
        override val title: Int = R.string.delete_group
    ) : Action()
}

@Composable
fun ActionDialog(
    action: Action?,
    onDismiss: () -> Unit
) {
    if (action != null) {
        CustomDialog(
            title = action.title,
            content = { action.content() },
            onDismissRequest = onDismiss,
            onConfirmation = action.onConfirm
        )
    }
}
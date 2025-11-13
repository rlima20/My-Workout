package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.runtime.Composable
import com.example.myworkout.R

sealed class Action {
    abstract val title: Int
    abstract val message: Int?
    abstract val onConfirm: () -> Unit
    abstract val content: @Composable () -> Unit

    data class Edit(
        override val title: Int = R.string.delete_group,
        override val message: Int? = null,
        override val onConfirm: () -> Unit,
        override val content: @Composable () -> Unit = {},
    ) : Action()

    data class Delete(
        override val title: Int = R.string.delete_group,
        override val message: Int? = null,
        override val onConfirm: () -> Unit,
        override val content: @Composable () -> Unit = {},
    ) : Action()

    data class Restore(
        override val title: Int,
        override val message: Int?,
        override val onConfirm: () -> Unit,
        override val content: @Composable (() -> Unit)
    ): Action()

    data class Achieve(
        override val title: Int,
        override val message: Int?,
        override val onConfirm: () -> Unit,
        override val content: @Composable (() -> Unit)
    ): Action()

    data class Skip(
        override val title: Int,
        override val message: Int?,
        override val onConfirm: () -> Unit,
        override val content: @Composable (() -> Unit)
    ): Action()
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
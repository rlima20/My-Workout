package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.myworkout.R

@Composable
fun AlertDialog(
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector? = null,
    confirmButtonText: String,
    cancelButtonText: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        icon = {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = stringResource(R.string.warning_icon)
                )
            }
        },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = { TextButton(onClick = { onConfirmation() }) { Text(confirmButtonText) } },
        dismissButton = { TextButton(onClick = { onDismissRequest() }) { Text(cancelButtonText) } }
    )
}
package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

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
        icon = { if (icon != null) { Icon(icon, contentDescription = "Warning Icon") } },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = { TextButton(onClick = { onConfirmation() }) { Text(confirmButtonText) } },
        dismissButton = { TextButton(onClick = { onDismissRequest() }) { Text(cancelButtonText) } }
    )
}
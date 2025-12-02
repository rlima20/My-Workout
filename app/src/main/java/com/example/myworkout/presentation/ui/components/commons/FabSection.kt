package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myworkout.R

@Composable
fun FabSection(
    enabled: Boolean = false,
    buttonName: String = stringResource(R.string.next),
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        ExtendedFab(
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Default.ArrowForward,
            text = buttonName,
            onClick = { onClick() }
        )
    }
}
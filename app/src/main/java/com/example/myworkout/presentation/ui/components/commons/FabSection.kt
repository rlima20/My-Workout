package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myworkout.R

@Composable
fun FabSection(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    buttonName: String = stringResource(R.string.next),
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    if (enabled) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = modifier.fillMaxWidth()
        ) {
            ExtendedFab(
                modifier = Modifier.fillMaxWidth(),
                icon = icon,
                text = buttonName,
                onClick = { onClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FabSectionPreview() {
    FabSection(
        buttonName = stringResource(R.string.next),
        onClick = {},
        icon = null
    )
}
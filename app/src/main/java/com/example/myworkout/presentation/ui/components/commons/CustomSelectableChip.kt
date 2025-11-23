package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

/**
 * Custom selectable chip to replace FilterChip so we can reliably handle long press.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomSelectableChip(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onDoubledClick: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val backgroundColor =
        if (selected) colorResource(id = R.color.button_color) else colorResource(id = R.color.empty)
    val contentColor =
        if (selected) colorResource(id = R.color.white) else colorResource(id = R.color.text_color)

    Surface(
        modifier = modifier
            .height(42.dp)
            .defaultMinSize(minWidth = 64.dp)
            .clip(RoundedCornerShape(20.dp))
            .combinedClickable(
                enabled = enabled,
                onClick = { onClick() },
                onLongClick = { onLongClick() },
                onDoubleClick = { onDoubledClick() },
                interactionSource = interactionSource,
                indication = ripple(),
                role = Role.Button
            ),
        color = backgroundColor,
        tonalElevation = 0.dp,
        contentColor = contentColor,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
            )
        }
    }
}
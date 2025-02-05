package com.example.myworkout.presentation.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

@Composable
internal fun TopBar(
    title: String,
    isHomeScreen: Boolean = true,
    onNavigateToHomeScreen: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.height(70.dp),
        backgroundColor = Color(0xFF6B757E),
        navigationIcon = { ShowBackIcon(isHomeScreen) { onNavigateToHomeScreen() } },
        title = { TopAppBarText(isHomeScreen, title) },
    )
}

@Composable
private fun TopAppBarText(
    isHomeScreen: Boolean,
    title: String
) {
    Text(
        modifier = Modifier.offset(x = setHorizontalPosition(isHomeScreen)),
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = colorResource(R.color.title_top_bar_color),
        text = title
    )
}

fun setHorizontalPosition(isHomeScreen: Boolean): Dp =
    if (isHomeScreen) (-48).dp else (-8).dp

@Composable
private fun ShowBackIcon(
    isHomeScreen: Boolean,
    onNavigateToHomeScreen: () -> Unit
) {
    if (!isHomeScreen) {
        IconButton(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(20.dp),
            onClick = { onNavigateToHomeScreen() }
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.icon_back),
                contentDescription = null,
            )
        }
    }
}

package com.example.myworkout.presentation.ui.components.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    isHomeScreen: Boolean,
    onPopBackStack: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                width = 0.1.dp, // metade de 1.dp
                color = colorResource(R.color.bottom_bar_color),
                shape = RectangleShape // sem cantos arredondados
            )
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.title_color)
                )
            },
            navigationIcon = {
                if (!isHomeScreen) {
                    IconButton(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(20.dp),
                        onClick = onPopBackStack
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_back),
                            contentDescription = null,
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorResource(R.color.top_bar_color)
            ),
        )
    }
}

private fun setHorizontalPosition(isHomeScreen: Boolean): Dp =
    if (isHomeScreen) (-48).dp else (-8).dp

@Composable
private fun ShowBackIcon(
    isHomeScreen: Boolean,
    onClick: () -> Unit
) {
    if (!isHomeScreen) {
        IconButton(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(20.dp),
            onClick = { onClick() }
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.icon_back),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun TopAppBarPreview() {
    TopBar(
        title = "Home Screen",
        isHomeScreen = true,
        onPopBackStack = {}
    )
}
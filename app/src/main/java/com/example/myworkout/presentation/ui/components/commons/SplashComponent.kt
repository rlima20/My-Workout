package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myworkout.R

@Composable
fun SplashComponent() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.splash_background_color)),
        color = colorResource(id = R.color.splash_background_color),
    ) {
        Box(
            contentAlignment = androidx.compose.ui.Alignment.Center,
            modifier = Modifier.size(600.dp),
        ) {
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(344.dp)
                    .background(color = colorResource(id = R.color.splash_background_color)),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
            )
        }
    }
}
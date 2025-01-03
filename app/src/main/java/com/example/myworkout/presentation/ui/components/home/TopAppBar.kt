package com.example.myworkout.presentation.ui.components.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.myworkout.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun TopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarColors(
            containerColor = colorResource(R.color.top_bar_color),
            scrolledContainerColor = colorResource(R.color.top_bar_color),
            navigationIconContentColor = colorResource(R.color.top_bar_color),
            titleContentColor = colorResource(R.color.top_bar_color),
            actionIconContentColor = colorResource(R.color.top_bar_color)
        ),
        modifier = Modifier.padding(top = 8.dp),
        title = {
            Text(
                color = colorResource(R.color.title_top_bar_color),
                text = "Home Screen"
            )
        })
}

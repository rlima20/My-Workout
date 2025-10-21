package com.example.myworkout.presentation.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

@Composable
fun ErrorStateComponent(
    message: String? = stringResource(R.string.something_went_wrong),
    onButtonClicked: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        Text(
            text = stringResource(R.string.error),
            color = colorResource(id = R.color.missed),
            fontSize = 22.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "$message já existe",
            color = colorResource(id = R.color.black),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
        )

        Button(
            enabled = true,
            onClick = { onButtonClicked() },
        ) {
            Text(
                stringResource(R.string.try_again),
            )
        }
    }
}

@Preview
@Composable
fun ErrorStateComponentPreview() {
    ErrorStateComponent()
}

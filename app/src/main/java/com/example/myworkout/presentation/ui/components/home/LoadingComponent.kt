package com.example.myworkout.presentation.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.R

@Composable
fun LoadingComponent(text: String = EMPTY_STRING) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            color = colorResource(R.color.black),
        )
        Text(
            modifier = Modifier
                .width(200.dp)
                .padding(top = 16.dp),
            text = text,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun LoadingComponentPreview() {
    LoadingComponent("Exemplo de um texto a ser apresentado")
}

private const val EMPTY_STRING = ""
package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    hintEnabled: Boolean,
    hintText: String,
    hintColor: Color = colorResource(R.color.missed),
    enabled: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = onClick,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.button_color)
            )
        ) {
            Text(text)
            if (hintEnabled) {
                Text(
                    modifier = Modifier.offset(x = -(60.dp), y = 5.dp),
                    maxLines = 1,
                    color = hintColor,
                    fontSize = 16.sp,
                    text = hintText
                )
            }
        }
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    PrimaryButton(
        text = "Salvar",
        enabled = false,
        hintText = "Selecione um grupo",
        hintEnabled = true
    ) { }
}
package com.example.myworkout.presentation.ui.components.trainingcard

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.example.myworkout.R

@Composable
fun LabelTrainingCard(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = 1
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        textAlign = textAlign,
        maxLines = maxLines
    )
}

@Composable
@Preview
private fun LabelTrainingCardPreview() {
    LabelTrainingCard(stringResource(R.string.monday))
}
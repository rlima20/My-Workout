package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

@Composable
fun ScrollableTextCard(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "Digite aqui...",
    minHeight: Dp = 150.dp,
    onFocus: (focus: Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }

    Column {
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.title_color),
            maxLines = 1,
            text = stringResource(R.string.my_notes)
        )

        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minHeight)
                    .padding(12.dp)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState -> onFocus(focusState.isFocused) },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(
                                text = hint,
                                color = colorResource(R.color.title_color)
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }
    }
}
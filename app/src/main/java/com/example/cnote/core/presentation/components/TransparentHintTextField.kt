package com.example.cnote.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    backgroundColor: Color = Color.Transparent,
    testTag: String = ""
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .testTag(testTag),
        textStyle = textStyle,
        singleLine = singleLine,
        placeholder = {
            Text(
                text = hint,
                style = textStyle.copy(color = textStyle.color.copy(alpha = 0.5f))
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = backgroundColor,
            focusedIndicatorColor = backgroundColor,
            unfocusedIndicatorColor = backgroundColor,
            cursorColor = textStyle.color
        )
    )
}

package com.example.cnote.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
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
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = backgroundColor,
            unfocusedIndicatorColor = backgroundColor,
            cursorColor = textStyle.color,
            disabledContainerColor = backgroundColor,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor
        )
    )
}

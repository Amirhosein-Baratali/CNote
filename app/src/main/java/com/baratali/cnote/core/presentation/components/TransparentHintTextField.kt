package com.baratali.cnote.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import com.baratali.cnote.ui.theme.CNoteTheme

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
    val contentColor = Color.Black
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
            CustomText(
                text = hint,
                style = textStyle.copy(Color.Unspecified),
                color = Color.Unspecified
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = backgroundColor,
            unfocusedIndicatorColor = backgroundColor,
            cursorColor = contentColor,
            disabledContainerColor = backgroundColor,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            focusedPlaceholderColor = contentColor,
            unfocusedPlaceholderColor = contentColor.copy(0.8f),
        )
    )
}

@LightAndDarkPreview
@Composable
private fun TransparentHintTextFieldPreview() {
    CNoteTheme {
        TransparentHintTextField(
            text = "",
            hint = "Hint",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}

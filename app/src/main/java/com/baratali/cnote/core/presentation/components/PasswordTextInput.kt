package com.baratali.cnote.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun PasswordTextInput(
    modifier: Modifier = Modifier,
    password: String,
    hasError: Boolean = false,
    onPasswordChanged: (String) -> Unit,
    labelText: String,
    errorText: String? = null,
    maxLength: Int = 20
) {
    var revealPassword by remember {
        mutableStateOf(false)
    }

    val iconsColor = colorScheme.onSurface

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = password,
        onValueChange = { newText ->
            if (newText.length <= maxLength) {
                onPasswordChanged(newText)
            }
        },
        visualTransformation = if (revealPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_locked_password),
                contentDescription = "password visible",
                tint = iconsColor
            )
        },
        trailingIcon = {
            if (password.isNotEmpty()) {
                Row(
                    Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            revealPassword = !revealPassword
                        },
                        painter =
                        if (revealPassword)
                            painterResource(id = R.drawable.ic_visibility)
                        else
                            painterResource(id = R.drawable.ic_visibility_off),
                        contentDescription =
                        if (revealPassword)
                            "password visible"
                        else
                            "password invisible"
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        modifier = Modifier.clickable {
                            onPasswordChanged("")
                        },
                        painter = painterResource(id = R.drawable.ic_clear),
                        contentDescription = "clear password"
                    )
                }
            }
        },
        singleLine = true,
        isError = hasError,
        shape = RoundedCornerShape(size = 18.dp),
        supportingText = errorText?.takeIf { hasError && password.isNotEmpty() }?.let {
            { CustomText(text = errorText, color = Color.Unspecified) }
        },
        colors = OutlinedTextFieldDefaults.colors(
            errorSupportingTextColor = colorScheme.error
        ),
        label = { CustomText(text = labelText) },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.Center,
            textDirection = TextDirection.Ltr,
        )
    )
}

@LightAndDarkPreview
@Composable
fun PasswordTextInputPreview() {
    CNoteTheme {
        PasswordTextInput(
            password = "",
            onPasswordChanged = {},
            labelText = "Password",
            errorText = "password must match"
        )
    }
}

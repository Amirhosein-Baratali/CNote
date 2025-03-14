package com.baratali.cnote.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ObfuscatedText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    obfuscate: Boolean
) {
    CustomText(
        modifier = modifier
            .fillMaxWidth()
            .then(conditionalBlur(obfuscate)),
        text = text,
        style = style,
        color = Color.Black,
        maxLines = 10,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis
    )
}

private fun conditionalBlur(obfuscate: Boolean): Modifier =
    if (obfuscate)
        Modifier.blur(8.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
    else
        Modifier
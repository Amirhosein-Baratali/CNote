package com.baratali.cnote.core.presentation.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import com.baratali.cnote.feature_note.presentation.util.isRtlLanguage

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = TextAlign.Right,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    letterSpacing: TextUnit = TextUnit.Unspecified
) {
    val layoutDirection = if (text.isRtlLanguage()) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow,
            letterSpacing = letterSpacing
        )
    }
}

@Composable
fun CustomText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = TextAlign.Right,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    letterSpacing: TextUnit = TextUnit.Unspecified
) {
    val layoutDirection =
        if (text.text.isRtlLanguage()) LayoutDirection.Rtl else LayoutDirection.Ltr
    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow,
            letterSpacing = letterSpacing
        )
    }
}

@LightAndDarkPreview
@Composable
private fun PreviewCustomText() {
    CustomText(text = "Preview")
}
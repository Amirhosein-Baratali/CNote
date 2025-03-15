package com.baratali.cnote.settings.presentation.settings_screen.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.settings.data.data_store.dto.DarkMode
import com.baratali.cnote.ui.theme.CNoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DarkModeSetting(
    modifier: Modifier = Modifier,
    selectedMode: DarkMode,
    onModeChange: (DarkMode) -> Unit
) {
    val options = listOf(
        DarkMode.SystemDefault to stringResource(R.string.system_default),
        DarkMode.Dark to stringResource(R.string.dark),
        DarkMode.Light to stringResource(R.string.light)
    )

    var rowSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    val index = options.indexOfFirst { it.first == selectedMode }

    val rowWidthDp = with(density) { rowSize.width.toDp() }
    val segmentWidthDp = if (rowWidthDp > 0.dp) rowWidthDp / 3 else 0.dp
    val targetOffsetXDp = segmentWidthDp * index
    val animatedOffsetXDp by animateDpAsState(targetValue = targetOffsetXDp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorScheme.outlineVariant)
            .padding(8.dp)
    ) {
        if (rowSize != IntSize.Zero) {
            Box(
                modifier = Modifier
                    .offset(x = animatedOffsetXDp, y = 0.dp)
                    .width(segmentWidthDp)
                    .height(with(density) { rowSize.height.toDp() })
                    .background(
                        color = colorScheme.surfaceTint.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { rowSize = it },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEach { (mode, label) ->
                val isSelected = mode == selectedMode
                val targetColor =
                    if (isSelected) Color.White else colorScheme.onSurface
                val animatedColor by animateColorAsState(
                    targetValue = targetColor,
                    animationSpec = tween(durationMillis = 300)
                )
                CompositionLocalProvider(LocalRippleConfiguration provides null) {
                    TextButton(
                        onClick = { onModeChange(mode) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent),
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        CustomText(
                            text = label,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = animatedColor
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DarkModeSettingsPreview() {
    CNoteTheme {
        DarkModeSetting(
            selectedMode = DarkMode.SystemDefault,
            onModeChange = {}
        )
    }
}
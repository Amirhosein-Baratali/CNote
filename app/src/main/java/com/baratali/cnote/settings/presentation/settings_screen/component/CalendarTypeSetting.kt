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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
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
import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.DatePickerType
import com.baratali.cnote.ui.theme.CNoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTypeSetting(
    modifier: Modifier = Modifier,
    selectedType: DatePickerType,
    onTypeChange: (DatePickerType) -> Unit
) {
    val options = listOf(
        DatePickerType.GEORGIAN to stringResource(R.string.gregorian),
        DatePickerType.JALALI to stringResource(R.string.jalali)
    )

    var rowSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    val index = options.indexOfFirst { it.first == selectedType }
    val rowWidthDp = with(density) { rowSize.width.toDp() }
    val segmentWidthDp =
        if (rowWidthDp > 0.dp) (rowWidthDp - 80.dp) / 2 else 0.dp // Adjust for label width
    val targetOffsetXDp = 80.dp + (segmentWidthDp * index) // Offset to account for label
    val animatedOffsetXDp by animateDpAsState(
        targetValue = targetOffsetXDp,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorScheme.outlineVariant)
            .padding(start = 8.dp)
            .padding(vertical = 6.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        CustomText(
            text = stringResource(R.string.calendar_type),
            color = colorScheme.onBackground,
            modifier = Modifier
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .onSizeChanged { rowSize = it },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { idx, (type, label) ->
                val isSelected = type == selectedType
                val targetColor = if (isSelected) colorScheme.onPrimary else colorScheme.onSurface
                val animatedColor by animateColorAsState(
                    targetValue = targetColor,
                    animationSpec = tween(durationMillis = 300)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .padding(horizontal = 4.dp)
                        .background(
                            color = if (isSelected) colorScheme.surfaceTint.copy(alpha = 0.5f) else Color.Transparent,
                            shape = RoundedCornerShape(6.dp)
                        )
                ) {
                    if (rowSize != IntSize.Zero && idx == index) {
                        Box(
                            modifier = Modifier
                                .offset(x = animatedOffsetXDp - 80.dp, y = 0.dp)
                                .background(
                                    color = colorScheme.surfaceTint.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(6.dp)
                                )
                        )
                    }

                    CompositionLocalProvider(LocalRippleConfiguration provides null) {
                        TextButton(
                            onClick = { onTypeChange(type) },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent),
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            CustomText(
                                text = label,
                                textAlign = TextAlign.Center,
                                color = animatedColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DatePickerTypeSettingPreview() {
    CNoteTheme {
        DatePickerTypeSetting(
            selectedType = DatePickerType.GEORGIAN,
            onTypeChange = {}
        )
    }
}
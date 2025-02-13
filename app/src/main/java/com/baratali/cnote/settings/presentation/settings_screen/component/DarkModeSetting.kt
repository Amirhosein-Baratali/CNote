package com.baratali.cnote.settings.presentation.settings_screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.settings.data.data_store.dto.DarkMode
import com.baratali.cnote.ui.theme.CNoteTheme
import com.baratali.cnote.ui.theme.spacing

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

    var expanded by remember { mutableStateOf(false) }
    var boxWidth by remember { mutableIntStateOf(0) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorScheme.outlineVariant)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomText(
            text = stringResource(R.string.dark_mode),
            color = colorScheme.onBackground
        )

        Spacer(Modifier.width(MaterialTheme.spacing.xLarge))

        Box(
            modifier = Modifier.onGloballyPositioned {
                boxWidth = it.size.width
            }
        ) {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        boxWidth = coordinates.size.width
                    },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = colorScheme.surfaceVariant,
                    contentColor = colorScheme.onSurfaceVariant
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                CustomText(
                    text = options.find { it.first == selectedMode }?.second ?: "",
                    color = Color.Unspecified
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(4.dp),
                        color = colorScheme.surfaceVariant
                    )
                    .width(with(LocalDensity.current) { boxWidth.toDp() })
            ) {
                options.forEach { (mode, label) ->
                    val isSelected = mode == selectedMode
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (isSelected) colorScheme.primary.copy(alpha = 0.5f)
                                else Color.Transparent
                            )
                            .padding(horizontal = 8.dp),
                        text = {
                            Row(
                                modifier= Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CustomText(
                                    label,
                                    color = if (isSelected) colorScheme.onPrimary else colorScheme.onSurface
                                )
                            }
                        },
                        onClick = {
                            onModeChange(mode)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DarkModeSettingsPreview() {
    CNoteTheme {
        DarkModeSetting(selectedMode = DarkMode.SystemDefault, onModeChange = {})
    }
}
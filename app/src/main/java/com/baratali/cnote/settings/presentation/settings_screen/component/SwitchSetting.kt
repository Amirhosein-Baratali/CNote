package com.baratali.cnote.settings.presentation.settings_screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.core.presentation.components.LightAndDarkPreview
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun SwitchSetting(
    modifier: Modifier = Modifier,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val containerColor = colorScheme.outlineVariant
    val contentColor = colorScheme.onBackground

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .padding(horizontal = 16.dp)
    ) {
        CustomText(
            text = title,
            color = contentColor
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = colorScheme.outlineVariant,
                checkedTrackColor = colorScheme.surfaceTint,
                uncheckedBorderColor = contentColor,
                checkedBorderColor = contentColor,
                uncheckedThumbColor = colorScheme.onSurfaceVariant,
                uncheckedTrackColor = colorScheme.surfaceVariant
            )
        )
    }
}

@LightAndDarkPreview
@Composable
private fun PreviewSwitchSetting() {
    CNoteTheme {
        SwitchSetting(title = "Notifications", isChecked = true, onCheckedChange = {})
    }
}

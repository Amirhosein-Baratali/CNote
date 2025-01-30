package com.baratali.cnote.settings.presentation.settings_screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.LightAndDarkPreview
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun SwitchDarkMode(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    SwitchSetting(
        title = stringResource(R.string.dark_mode),
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}

@LightAndDarkPreview
@Composable
private fun SwitchDarkModePreview() {
    CNoteTheme {
        SwitchDarkMode(isChecked = true, onCheckedChange = {})
    }
}
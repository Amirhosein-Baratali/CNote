package com.baratali.cnote.settings.presentation.menu_item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText

@Composable
fun SettingsMenuItem(onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val deleteCompletedText = stringResource(R.string.settings)
                Icon(
                    painter = painterResource(R.drawable.ic_setting_outlined),
                    contentDescription = deleteCompletedText,
                    modifier = Modifier.size(24.dp)
                )
                CustomText(
                    text = deleteCompletedText,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        onClick = onClick
    )
}

@Preview
@Composable
fun SettingsMenuItemPreview() {
    SettingsMenuItem {}
}

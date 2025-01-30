package com.example.cnote.settings.presentation.menu_item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cnote.R
import com.example.cnote.core.presentation.components.CustomText

@Composable
fun SettingsMenuItem(onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val deleteCompletedText = stringResource(R.string.settings)
                Icon(
                    imageVector = Icons.Outlined.Settings,
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
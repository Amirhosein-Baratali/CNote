package com.example.cnote.settings.presentation.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cnote.R
import com.example.cnote.core.presentation.components.CustomText
import com.example.cnote.core.presentation.components.LightAndDarkPreview
import com.example.cnote.settings.presentation.settings_screen.component.SwitchDarkMode
import com.example.cnote.settings.presentation.settings_screen.component.SwitchNotification
import com.example.cnote.ui.theme.CNoteTheme

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SettingsContent(
    state: SettingState,
    onEvent: (SettingsEvent) -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorScheme.primaryContainer.copy(0.5f))
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                .background(color = colorScheme.primary)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(30.dp))
            HeaderSection()
            Spacer(Modifier.height(30.dp))
            ElevatedCard(
                modifier = Modifier.fillMaxHeight(), shape = RoundedCornerShape(
                    topStart = 12.dp, topEnd = 12.dp, bottomStart = 0.dp, bottomEnd = 0.dp
                ), colors = CardDefaults.elevatedCardColors(
                    containerColor = colorScheme.primaryContainer
                )
            ) {
                SwitchDarkMode(isChecked = state.isDark ?: isSystemInDarkTheme(),
                    onCheckedChange = { onEvent(SettingsEvent.DarkModeClicked(it)) })

                SwitchNotification(isChecked = state.notificationsEnabled,
                    onCheckedChange = { onEvent(SettingsEvent.NotificationsToggled(it)) })
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = null,
            tint = colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxHeight()
                .size(45.dp),
        )
        Spacer(Modifier.width(8.dp))
        CustomText(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.displaySmall,
            color = colorScheme.onPrimary
        )
    }
}

@LightAndDarkPreview
@Composable
private fun SettingsScreenPreview() {
    CNoteTheme {
        SettingsContent(state = SettingState(), onEvent = {})
    }
}
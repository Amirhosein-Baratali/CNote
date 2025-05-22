package com.baratali.cnote.settings.presentation.settings_screen

import SetOrChangePasswordItem
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.core.presentation.components.LightAndDarkPreview
import com.baratali.cnote.settings.presentation.settings_screen.component.DarkModeSetting
import com.baratali.cnote.settings.presentation.settings_screen.component.DatePickerTypeSetting
import com.baratali.cnote.settings.presentation.settings_screen.component.SwitchNotification
import com.baratali.cnote.settings.presentation.util.SettingScreens
import com.baratali.cnote.ui.theme.CNoteTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SettingsViewModel.UIEvent.NavigateToPasswordScreen -> {
                    navController.navigate(SettingScreens.Password(event.mode))
                }
            }
        }
    }
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
    val containerColor = colorScheme.secondaryContainer

    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorScheme.tertiary)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                .background(color = colorScheme.secondary)
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
                modifier = Modifier.fillMaxHeight(),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = containerColor
                )
            ) {
                DarkModeSetting(
                    modifier = Modifier.padding(4.dp),
                    selectedMode = state.darkMode,
                    onModeChange = { onEvent(SettingsEvent.DarkModeChanged(it)) }
                )

                SwitchNotification(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp),
                    isChecked = state.notificationsEnabled,
                    onCheckedChange = { onEvent(SettingsEvent.NotificationsToggled(it)) }
                )

                SetOrChangePasswordItem(
                    modifier = Modifier.padding(4.dp),
                    passwordSet = state.passwordSet,
                    onClick = { onEvent(SettingsEvent.SetOrChangePasswordClicked) }
                )

                DatePickerTypeSetting(
                    modifier = Modifier.padding(4.dp),
                    selectedType = state.datePickerType,
                    onTypeChange = { onEvent(SettingsEvent.UpdateDatePickerType(it)) }
                )
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
            painter = painterResource(R.drawable.ic_setting_outlined),
            contentDescription = null,
            tint = colorScheme.onSecondary,
            modifier = Modifier
                .fillMaxHeight()
                .size(45.dp),
        )
        Spacer(Modifier.width(8.dp))
        CustomText(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.displaySmall,
            color = colorScheme.onSecondary
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
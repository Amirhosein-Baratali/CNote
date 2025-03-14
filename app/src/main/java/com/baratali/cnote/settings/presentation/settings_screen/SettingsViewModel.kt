package com.baratali.cnote.settings.presentation.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import com.baratali.cnote.settings.presentation.util.PasswordMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<UIEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.getSettings().collect { settings ->
                _state.update {
                    it.copy(
                        darkMode = settings.darkMode,
                        notificationsEnabled = settings.notificationEnabled,
                        passwordSet = settings.passwordHash != null
                    )
                }
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.DarkModeChanged -> {
                viewModelScope.launch {
                    dataStoreRepository.updateDarkMode(event.darkMode)
                }.invokeOnCompletion {
                    _state.update { it.copy(darkMode = event.darkMode) }
                }
            }

            is SettingsEvent.NotificationsToggled -> {
                viewModelScope.launch {
                    dataStoreRepository.updateNotificationsEnabled(event.enabled)
                }.invokeOnCompletion {
                    _state.update { it.copy(notificationsEnabled = event.enabled) }
                }
            }

            SettingsEvent.SetOrChangePasswordClicked -> {
                val passwordMode: PasswordMode =
                    if (state.value.passwordSet) PasswordMode.CHANGE_PASSWORD
                    else PasswordMode.SET_PASSWORD

                viewModelScope.launch {
                    _eventFlow.send(
                        UIEvent.NavigateToPasswordScreen(passwordMode)
                    )
                }
            }
        }
    }

    sealed class UIEvent {
        data class NavigateToPasswordScreen(val mode: PasswordMode) : UIEvent()
    }
}
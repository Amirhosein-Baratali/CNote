package com.baratali.cnote.settings.presentation.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import com.baratali.cnote.settings.presentation.settings_screen.SettingsViewModel.UIEvent.NavigateToPasswordScreen
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
                        passwordSet = settings.passwordHash != null,
                        datePickerType = settings.datePickerType
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
                        NavigateToPasswordScreen(passwordMode)
                    )
                }
            }

            is SettingsEvent.UpdateDatePickerType -> {
                viewModelScope.launch {
                    dataStoreRepository.updateDatePickerType(event.type)
                }.invokeOnCompletion {
                    _state.update { it.copy(datePickerType = event.type) }
                }
            }
        }
    }

    sealed class UIEvent {
        data class NavigateToPasswordScreen(val mode: PasswordMode) : UIEvent()
    }
}
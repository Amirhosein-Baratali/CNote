package com.baratali.cnote.settings.presentation.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.getSettings().collect { timeSettings ->
                _state.update {
                    it.copy(
                        darkMode = timeSettings.darkMode,
                        notificationsEnabled = timeSettings.notificationEnabled
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
        }
    }
}
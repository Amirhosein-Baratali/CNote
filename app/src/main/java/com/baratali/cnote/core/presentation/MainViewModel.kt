package com.baratali.cnote.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baratali.cnote.settings.data.data_store.dto.DarkMode
import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _darkMode = MutableStateFlow(DarkMode.SystemDefault)
    val darkMode = _darkMode.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.getSettings().collect { setting ->
                _darkMode.update { setting.darkMode }
            }
        }
    }
}
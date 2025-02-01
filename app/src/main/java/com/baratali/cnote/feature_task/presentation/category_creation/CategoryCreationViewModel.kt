package com.baratali.cnote.feature_task.presentation.category_creation

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import com.baratali.cnote.core.presentation.BaseViewModel
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.feature_task.domain.use_case.categories.CategoryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryCreationViewModel @Inject constructor(
    private val useCases: CategoryUseCases
) : BaseViewModel() {

    private val _state = MutableStateFlow(CategoryCreationState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<UIEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun onEvent(event: CategoryCreationEvent) {
        when (event) {
            is CategoryCreationEvent.NameChanged -> {
                _state.update { it.copy(name = event.name) }
            }

            is CategoryCreationEvent.IconSelected -> {
                _state.update { it.copy(selectedIcon = event.icon) }
            }

            is CategoryCreationEvent.ColorSelected -> {
                _state.update { it.copy(selectedColor = event.color) }
            }

            CategoryCreationEvent.SaveCategory -> {
                saveCategory()
            }

            CategoryCreationEvent.Dismiss -> {
                viewModelScope.launch { _eventFlow.send(UIEvent.NavigateUp) }
            }
        }
    }

    private fun saveCategory() {
        val currentState = state.value
        if (currentState.name.isBlank()) return

        viewModelScope.launch {
            try {
                useCases.addCategory(
                    TaskCategory(
                        name = currentState.name,
                        icon = currentState.selectedIcon,
                        color = currentState.selectedColor.color.toArgb().toLong()
                    )
                )
                _eventFlow.send(UIEvent.NavigateUp)
            } catch (e: Exception) {
                showSnackbar(e.message ?: "Error occurred")
            }
        }
    }

    sealed class UIEvent {
        object NavigateUp : UIEvent()
    }
}

package com.example.cnote.feature_task.presentation.add_edit_task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cnote.feature_task.domain.model.InvalidTaskException
import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.feature_task.domain.use_case.TaskUseCases
import com.example.cnote.feature_task.presentation.util.TaskScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var getTaskJob: Job? = null
    private var saveTaskJob: Job? = null

    private val _state = MutableStateFlow(AddEditTaskState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private var currentTaskId: Int? = null

    init {
        savedStateHandle.toRoute<TaskScreens.AddEditTask>().taskId?.let {
            currentTaskId = it
            getTask(it)
        }
    }

    private fun getTask(id: Int) {
        getTaskJob?.cancel()
        getTaskJob = viewModelScope.launch {
            taskUseCases.getTask(id)?.also { task ->
                currentTaskId = task.id
                _state.update {
                    it.copy(name = task.name, description = task.description)
                }
            }
        }
    }

    private fun saveTask() {
        saveTaskJob?.cancel()
        saveTaskJob = viewModelScope.launch {
            try {
                taskUseCases.addTask(
                    state.value.run {
                        Task(
                            name = name,
                            description = description,
                            timeCreated = System.currentTimeMillis(),
                            completed = true,
                            importance = true,
                            id = currentTaskId,
                            date = date
                        )
                    }
                )
                _eventFlow.send(UiEvent.SaveTask)
            } catch (e: InvalidTaskException) {
                _eventFlow.send(
                    UiEvent.ShowError(
                        message = e.message ?: "Couldn't save task"
                    )
                )
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.NameChanged -> {
                _state.update { it.copy(name = event.name) }
            }

            is AddEditTaskEvent.DescriptionChanged -> {
                _state.update { it.copy(description = event.description) }
            }

            is AddEditTaskEvent.SaveTask -> {
                saveTask()
            }

            AddEditTaskEvent.Dismiss -> {
                viewModelScope.launch {
                    _eventFlow.send(UiEvent.Dismiss)
                }
            }

            AddEditTaskEvent.DateClicked -> {
                _state.update { it.copy(showDatePicker = true) }
            }

            AddEditTaskEvent.DateDismissed -> _state.update { it.copy(showDatePicker = false) }
            is AddEditTaskEvent.DateSelected -> _state.update { it.copy(date = event.date) }
        }
    }

    sealed class UiEvent {
        data class ShowError(val message: String) : UiEvent()
        object SaveTask : UiEvent()
        object Dismiss : UiEvent()
    }

    override fun onCleared() {
        saveTaskJob?.cancel()
        getTaskJob?.cancel()
        super.onCleared()
    }
}
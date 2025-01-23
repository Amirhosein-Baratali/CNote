package com.example.cnote.feature_task.presentation.add_edit_task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cnote.R
import com.example.cnote.core.presentation.BaseViewModel
import com.example.cnote.core.presentation.components.snackbar.SnackbarType
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
) : BaseViewModel() {

    private var getTaskJob: Job? = null
    private var saveTaskJob: Job? = null

    private val _state = MutableStateFlow(AddEditTaskState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<UIEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private var currentTaskId: Int? = null
    private var currentTask: Task? = null

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
                currentTask = task
                _state.update {
                    it.copy(
                        name = task.name,
                        description = task.description,
                        date = task.date
                    )
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
                            completed = currentTask?.completed ?: false,
                            importance = currentTask?.importance ?: false,
                            id = currentTaskId,
                            date = date
                        )
                    }
                )
            } catch (e: InvalidTaskException) {
                e.message?.let {
                    showSnackbar(message = it, snackbarType = SnackbarType.ERROR)
                } ?: showSnackbar(
                    messageId = R.string.cant_save_note,
                    snackbarType = SnackbarType.ERROR
                )
            } finally {
                _eventFlow.send(UIEvent.NavigateUp)
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
                    _eventFlow.send(UIEvent.NavigateUp)
                }
            }

            AddEditTaskEvent.DateClicked -> {
                _state.update { it.copy(showDatePicker = true) }
            }

            AddEditTaskEvent.DateDismissed -> _state.update { it.copy(showDatePicker = false) }
            is AddEditTaskEvent.DateSelected -> _state.update { it.copy(date = event.date) }
        }
    }

    sealed class UIEvent {
        object NavigateUp : UIEvent()
    }

    override fun onCleared() {
        saveTaskJob?.cancel()
        getTaskJob?.cancel()
        super.onCleared()
    }
}
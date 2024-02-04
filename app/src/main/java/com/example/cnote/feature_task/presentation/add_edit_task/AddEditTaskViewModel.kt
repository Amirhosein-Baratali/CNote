package com.example.cnote.feature_task.presentation.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cnote.core.presentation.TextFieldState
import com.example.cnote.feature_note.domain.model.InvalidNoteException
import com.example.cnote.feature_task.domain.model.InvalidTaskException
import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.feature_task.domain.use_case.TaskUseCases
import com.example.cnote.feature_task.presentation.util.TaskScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskName = mutableStateOf(
        TextFieldState(
            hint = "Enter name..."
        )
    )
    val taskName: State<TextFieldState> = _taskName

    private val _taskDescription = mutableStateOf(
        TextFieldState(
            hint = "Enter some description if you want..."
        )
    )
    val taskDescription: State<TextFieldState> = _taskDescription

    private val _isImportantState = mutableStateOf(false)
    val isImportantState: State<Boolean> = _isImportantState

    private val _isCompletedState = mutableStateOf(false)
    val isCompletedState: State<Boolean> = _isCompletedState
    
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Int? = null

    init {
        savedStateHandle.get<Int>(TaskScreens.ARG_Task_ID)?.let { taskId ->
            if (taskId != -1) {
                viewModelScope.launch {
                    taskUseCases.getTask(taskId)?.also { task ->
                        currentTaskId = task.id
                        _taskName.value = taskName.value.copy(
                            text = task.name,
                            isHintVisible = false
                        )
                        _taskDescription.value = _taskDescription.value.copy(
                            text = task.description,
                            isHintVisible = task.description.isBlank()
                        )

                        _isCompletedState.value = task.completed
                        _isImportantState.value = task.importance
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.EnteredName -> {
                _taskName.value = taskName.value.copy(
                    text = event.value
                )
            }

            is AddEditTaskEvent.ChangeNameFocus -> {
                _taskName.value = taskName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            taskName.value.text.isBlank()
                )
            }

            is AddEditTaskEvent.EnteredDescription -> {
                _taskDescription.value = _taskDescription.value.copy(
                    text = event.value
                )
            }

            is AddEditTaskEvent.ChangeDescriptionFocus -> {
                _taskDescription.value = _taskDescription.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _taskDescription.value.text.isBlank()
                )
            }

            is AddEditTaskEvent.SaveTask -> {
                viewModelScope.launch {
                    try {
                        taskUseCases.addTask(
                            Task(
                                name = taskName.value.text,
                                description = taskDescription.value.text,
                                timeStamp = System.currentTimeMillis(),
                                completed = isCompletedState.value,
                                importance = isImportantState.value,
                                id = currentTaskId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTask)
                    } catch (e: InvalidTaskException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save task"
                            )
                        )
                    }
                }
            }

            AddEditTaskEvent.ToggleCompletion -> {
                _isCompletedState.value = !isCompletedState.value
            }
            AddEditTaskEvent.ToggleImportance -> {
                _isImportantState.value = !isImportantState.value
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveTask : UiEvent()
    }
}
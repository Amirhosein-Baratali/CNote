package com.example.cnote.feature_task.presentation.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cnote.feature_task.domain.model.InvalidTaskException
import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.feature_task.domain.use_case.TaskUseCases
import com.example.cnote.feature_task.presentation.util.TaskScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var getTaskJob: Job? = null
    private var saveTaskJob: Job? = null

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _taskName = mutableStateOf("")
    val taskName: State<String> = _taskName

    private val _taskDescription = mutableStateOf("")
    val taskDescription: State<String> = _taskDescription

    private val _isImportantState = mutableStateOf(false)
    val isImportantState: State<Boolean> = _isImportantState

    private val _isCompletedState = mutableStateOf(false)
    val isCompletedState: State<Boolean> = _isCompletedState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Int? = null

    init {
        savedStateHandle.run {
            get<Int>(TaskScreens.ARG_TASK_ID)?.takeIf { it != -1 }
                ?.let { taskId -> getTask(taskId) }
            get<String>(TaskScreens.ARG_TITLE)?.let { title ->
                _title.value = title
            }
        }
    }

    private fun getTask(id: Int) {
        getTaskJob?.cancel()
        getTaskJob = viewModelScope.launch {
            taskUseCases.getTask(id)?.also { task ->
                currentTaskId = task.id
                _taskName.value = task.name
                _taskDescription.value = task.description

                _isCompletedState.value = task.completed
                _isImportantState.value = task.importance
            }
        }
    }

    private fun saveTask() {
        saveTaskJob?.cancel()
        saveTaskJob = viewModelScope.launch {
            try {
                taskUseCases.addTask(
                    Task(
                        name = taskName.value,
                        description = taskDescription.value,
                        timeStamp = System.currentTimeMillis(),
                        completed = isCompletedState.value,
                        importance = isImportantState.value,
                        id = currentTaskId
                    )
                )
                _eventFlow.emit(UiEvent.SaveTask)
            } catch (e: InvalidTaskException) {
                _eventFlow.emit(
                    UiEvent.ShowError(
                        message = e.message ?: "Couldn't save task"
                    )
                )
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.EnteredName -> {
                _taskName.value = event.value
            }

            is AddEditTaskEvent.EnteredDescription -> {
                _taskDescription.value = event.value
            }

            is AddEditTaskEvent.SaveTask -> {
                saveTask()
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
        data class ShowError(val message: String) : UiEvent()
        object SaveTask : UiEvent()
    }

    override fun onCleared() {
        saveTaskJob?.cancel()
        getTaskJob?.cancel()
        super.onCleared()
    }
}
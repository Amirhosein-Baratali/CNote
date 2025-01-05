package com.example.cnote.feature_task.presentation.tasks

import androidx.lifecycle.viewModelScope
import com.example.cnote.R
import com.example.cnote.core.domain.util.Order
import com.example.cnote.core.presentation.BaseViewModel
import com.example.cnote.core.presentation.components.UiText
import com.example.cnote.core.presentation.components.snackbar.SnackbarAction
import com.example.cnote.feature_task.domain.use_case.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : BaseViewModel() {

    private val _searchText = MutableStateFlow("")

    private val _state = MutableStateFlow(TasksState())
    val state = _searchText
        .combine(_state) { text, state ->
            state.copy(
                tasks = state.tasks.filter {
                    it.name.contains(text) || it.description.contains(text)
                }
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TasksState()
        )

    private var getTasksJob: Job? = null

    init {
        viewModelScope.launch {
            getTasks(taskUseCases.retrieveTaskOrder())
        }
    }

    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.Sort -> {
                val taskOrder = event.taskOrder
                viewModelScope.launch {
                    taskUseCases.storeTaskOrder(taskOrder)
                }
                getTasks(taskOrder)
            }

            is TasksEvent.DeleteTask -> {
                viewModelScope.launch {
                    val task = event.task
                    taskUseCases.deleteTask(task)
                    showUndoSnackbar {
                        taskUseCases.addTask(task)
                    }
                }
            }

            is TasksEvent.UpdateTask -> {
                viewModelScope.launch {
                    taskUseCases.updateTask(event.task)
                    state.collectLatest { getTasks(it.taskOrder) }
                }
            }

            is TasksEvent.DeleteCompletedTasks -> {
                viewModelScope.launch {
                    taskUseCases.deleteCompletedTasks()
                }
            }

            is TasksEvent.OnSearchQueryChanged -> _searchText.update { event.query }
        }
    }

    private fun showUndoSnackbar(onUndo: suspend () -> Unit = {}) {
        showSnackbar(
            messageId = R.string.task_deleted,
            action = SnackbarAction(
                name = UiText.StringResource(R.string.undo),
                action = onUndo
            )
        )
    }

    private fun getTasks(taskOrder: Order) {
        getTasksJob?.cancel()
        getTasksJob = taskUseCases.getTasks(taskOrder).onEach { tasks ->
            _state.update {
                it.copy(
                    tasks = tasks,
                    taskOrder = taskOrder
                )
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        getTasksJob?.cancel()
        super.onCleared()
    }
}
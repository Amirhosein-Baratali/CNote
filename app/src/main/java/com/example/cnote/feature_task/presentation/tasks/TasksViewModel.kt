package com.example.cnote.feature_task.presentation.tasks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cnote.core.domain.util.OrderType
import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.feature_task.domain.use_case.DeleteCompletedTasks
import com.example.cnote.feature_task.domain.use_case.TaskUseCases
import com.example.cnote.feature_task.domain.util.TaskOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TasksState())
    val state: State<TasksState> = _state

    private var getTasksJob: Job? = null

    private var recentlyDeletedTask: Task? = null

    init {
        getTasks(TaskOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.Order -> {
                if (state.value.taskOrder::class == event.taskOrder::class && state.value.taskOrder.orderType == event.taskOrder.orderType) return
                getTasks(event.taskOrder)
            }

            is TasksEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskUseCases.deleteTask(event.task)
                    recentlyDeletedTask = event.task
                }
            }

            TasksEvent.RestoreTask -> {
                viewModelScope.launch {
                    taskUseCases.addTask(recentlyDeletedTask ?: return@launch)
                    recentlyDeletedTask = null
                }
            }

            TasksEvent.ToggleOrderSection -> {
                _state.value =
                    state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }

            is TasksEvent.UpdateTask -> {
                viewModelScope.launch {
                    taskUseCases.updateTask(event.task)
                }
                getTasks(state.value.taskOrder)
            }

            is TasksEvent.DeleteCompletedTasks ->{
                viewModelScope.launch {
                    taskUseCases.deleteCompletedTasks()
                }
            }
        }
    }

    private fun getTasks(taskOrder: TaskOrder) {
        getTasksJob?.cancel()
        getTasksJob = taskUseCases.getTasks(taskOrder).onEach { tasks ->
            _state.value = state.value.copy(
                tasks = tasks, taskOrder = taskOrder
            )
        }.launchIn(viewModelScope)
    }
}
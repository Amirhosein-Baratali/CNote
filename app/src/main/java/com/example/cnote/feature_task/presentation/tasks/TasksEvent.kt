package com.example.cnote.feature_task.presentation.tasks

import com.example.cnote.core.domain.util.Order
import com.example.cnote.feature_task.domain.model.Task

sealed class TasksEvent {
    data class Sort(val taskOrder: Order) : TasksEvent()
    data class DeleteTask(val task: Task) : TasksEvent()
    data class UpdateTask(val task: Task) : TasksEvent()
    data class OnSearchQueryChanged(val query: String): TasksEvent()
    object RestoreTask : TasksEvent()
    object DeleteCompletedTasks : TasksEvent()
}
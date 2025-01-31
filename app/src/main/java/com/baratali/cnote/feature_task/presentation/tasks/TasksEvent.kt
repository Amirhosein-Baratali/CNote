package com.baratali.cnote.feature_task.presentation.tasks

import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.feature_task.data.data_source.model.Task

sealed class TasksEvent {
    data class Sort(val taskOrder: Order) : TasksEvent()
    data class DeleteTask(val task: Task) : TasksEvent()
    data class UpdateTask(val task: Task) : TasksEvent()
    data class OnSearchQueryChanged(val query: String) : TasksEvent()
    object DeleteCompletedTasks : TasksEvent()
    object SettingsClicked: TasksEvent()
}
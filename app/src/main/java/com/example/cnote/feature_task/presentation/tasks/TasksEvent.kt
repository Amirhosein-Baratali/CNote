package com.example.cnote.feature_task.presentation.tasks

import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.feature_task.domain.util.TaskOrder

sealed class TasksEvent {
    data class Order(val taskOrder: TaskOrder): TasksEvent()
    data class DeleteTask(val task:Task): TasksEvent()
    data class UpdateTask(val task:Task): TasksEvent()
    object ToggleOrderSection: TasksEvent()
    object RestoreTask: TasksEvent()
    object DeleteCompletedTasks: TasksEvent()
}
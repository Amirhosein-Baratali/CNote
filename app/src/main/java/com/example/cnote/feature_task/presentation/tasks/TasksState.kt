package com.example.cnote.feature_task.presentation.tasks

import com.example.cnote.core.domain.util.Order
import com.example.cnote.core.domain.util.OrderType
import com.example.cnote.feature_task.domain.model.Task

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val taskOrder: Order = Order.Date(OrderType.Descending)
) {
    val hasCompletedTask = tasks.map { it.completed }.contains(true)
}

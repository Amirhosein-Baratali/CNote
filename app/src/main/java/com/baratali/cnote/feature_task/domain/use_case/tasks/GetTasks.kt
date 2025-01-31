package com.baratali.cnote.feature_task.domain.use_case.tasks

import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.core.domain.util.OrderType
import com.baratali.cnote.feature_task.data.data_source.model.TaskWithCategory
import com.baratali.cnote.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasks(
    private val repository: TaskRepository
) {
    operator fun invoke(
        taskOrder: Order = Order.Date(OrderType.Descending)
    ): Flow<List<TaskWithCategory>> {
        return repository.getTasksWithCategory().map { tasks ->
            when (taskOrder.orderType) {
                is OrderType.Ascending -> {
                    when (taskOrder) {
                        is Order.Name -> tasks.sortedBy { it.task.name }
                        is Order.Date -> tasks.sortedBy { it.task.timeCreated }
                        is Order.Priority -> tasks.sortedBy { it.task.priority.value }
                    }
                }

                is OrderType.Descending -> {
                    when (taskOrder) {
                        is Order.Name -> tasks.sortedByDescending { it.task.name }
                        is Order.Date -> tasks.sortedByDescending { it.task.timeCreated }
                        is Order.Priority -> tasks.sortedByDescending { it.task.priority.value }
                    }
                }
            }
        }
    }
}
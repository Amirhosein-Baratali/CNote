package com.baratali.cnote.feature_task.domain.use_case

import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.core.domain.util.OrderType
import com.baratali.cnote.feature_task.domain.model.Task
import com.baratali.cnote.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasks(
    private val repository: TaskRepository
) {
    operator fun invoke(
        taskOrder: Order = Order.Date(OrderType.Descending)
    ): Flow<List<Task>> {
        return repository.getTasks().map { tasks ->
            when (taskOrder.orderType) {
                is OrderType.Ascending -> {
                    when (taskOrder) {
                        is Order.Name -> tasks.sortedBy { it.name }
                        is Order.Date -> tasks.sortedBy { it.timeCreated }
                        is Order.Priority -> tasks.sortedBy { it.priority.value }
                    }
                }

                is OrderType.Descending -> {
                    when (taskOrder) {
                        is Order.Name -> tasks.sortedByDescending { it.name }
                        is Order.Date -> tasks.sortedByDescending { it.timeCreated }
                        is Order.Priority -> tasks.sortedByDescending { it.priority.value }
                    }
                }
            }
        }
    }
}
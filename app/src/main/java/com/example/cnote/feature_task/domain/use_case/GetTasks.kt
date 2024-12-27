package com.example.cnote.feature_task.domain.use_case

import com.example.cnote.core.domain.util.Order
import com.example.cnote.core.domain.util.OrderType
import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.feature_task.domain.repository.TaskRepository
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
                    }
                }

                is OrderType.Descending -> {
                    when (taskOrder) {
                        is Order.Name -> tasks.sortedByDescending { it.name }
                        is Order.Date -> tasks.sortedByDescending { it.timeCreated }
                    }
                }
            }
        }.map { sortedNotes -> sortedNotes.sortedByDescending { it.importance } }
    }
}
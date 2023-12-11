package com.example.cnote.feature_task.domain.use_case

import com.example.cnote.core.domain.util.OrderType
import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.feature_task.domain.repository.TaskRepository
import com.example.cnote.feature_task.domain.util.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasks(
    private val repository:TaskRepository
) {

    suspend operator fun invoke(
        taskOrder: TaskOrder = TaskOrder.Date(OrderType.Descending)
    ): Flow<List<Task>> {
        return repository.getTasks().map { tasks ->
            when (taskOrder.orderType) {
                is OrderType.Ascending -> {
                    when (taskOrder) {
                        is TaskOrder.Name -> tasks.sortedBy { it.name }
                        is TaskOrder.Date -> tasks.sortedBy { it.timeStamp }
                    }
                }
                is OrderType.Descending ->{
                    when (taskOrder) {
                        is TaskOrder.Name -> tasks.sortedByDescending { it.name }
                        is TaskOrder.Date -> tasks.sortedByDescending { it.timeStamp }
                    }
                }
            }
        }.map { sortedNotes -> sortedNotes.sortedByDescending { it.importance } }
    }
}
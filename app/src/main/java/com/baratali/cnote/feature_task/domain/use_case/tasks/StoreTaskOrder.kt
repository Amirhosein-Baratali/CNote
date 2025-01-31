package com.baratali.cnote.feature_task.domain.use_case.tasks

import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.feature_task.domain.repository.TaskRepository

class StoreTaskOrder(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskOrder: Order) {
        repository.saveOrder(taskOrder)
    }
}
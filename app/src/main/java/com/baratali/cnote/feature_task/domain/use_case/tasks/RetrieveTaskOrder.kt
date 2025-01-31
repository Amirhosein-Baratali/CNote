package com.baratali.cnote.feature_task.domain.use_case.tasks

import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.feature_task.domain.repository.TaskRepository

class RetrieveTaskOrder(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): Order {
        return repository.getSavedOrder()
    }
}
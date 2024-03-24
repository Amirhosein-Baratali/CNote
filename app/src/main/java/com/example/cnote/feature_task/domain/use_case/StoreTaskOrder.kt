package com.example.cnote.feature_task.domain.use_case

import com.example.cnote.core.domain.util.Order
import com.example.cnote.feature_task.domain.repository.TaskRepository

class StoreTaskOrder(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskOrder: Order) {
        repository.saveOrder(taskOrder)
    }
}
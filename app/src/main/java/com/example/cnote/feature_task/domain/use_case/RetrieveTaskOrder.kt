package com.example.cnote.feature_task.domain.use_case

import com.example.cnote.core.domain.util.Order
import com.example.cnote.feature_task.domain.repository.TaskRepository

class RetrieveTaskOrder(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): Order {
        return repository.getSavedOrder()
    }
}
package com.baratali.cnote.feature_task.domain.use_case

import com.baratali.cnote.feature_task.domain.repository.TaskRepository

class DeleteCompletedTasks(
    private val repository: TaskRepository
) {
    suspend operator fun invoke() {
        repository.deleteCompletedTasks()
    }
}
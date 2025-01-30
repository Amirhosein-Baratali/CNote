package com.baratali.cnote.feature_task.domain.use_case

import com.baratali.cnote.feature_task.domain.model.Task
import com.baratali.cnote.feature_task.domain.repository.TaskRepository

class GetTask(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(id: Int): Task? {
        return repository.getTaskById(id)
    }
}
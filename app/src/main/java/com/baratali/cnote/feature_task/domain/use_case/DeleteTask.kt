package com.baratali.cnote.feature_task.domain.use_case

import com.baratali.cnote.feature_task.domain.model.Task
import com.baratali.cnote.feature_task.domain.repository.TaskRepository

class DeleteTask(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}
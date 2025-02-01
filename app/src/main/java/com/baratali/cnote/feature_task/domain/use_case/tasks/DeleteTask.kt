package com.baratali.cnote.feature_task.domain.use_case.tasks

import com.baratali.cnote.feature_task.data.data_source.model.Task
import com.baratali.cnote.feature_task.domain.repository.TaskRepository

class DeleteTask(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}
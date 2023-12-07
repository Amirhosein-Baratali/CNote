package com.example.cnote.feature_task.domain.use_case

import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.feature_task.domain.repository.TaskRepository

class DeleteTask(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}
package com.example.cnote.feature_task.domain.use_case

import com.example.cnote.feature_task.domain.model.InvalidTaskException
import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.feature_task.domain.repository.TaskRepository

class AddTask(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        if (task.name.isBlank()) {
            throw InvalidTaskException("Task Name Cannot Be Empty")
        }
        repository.insertTask(task)
    }
}
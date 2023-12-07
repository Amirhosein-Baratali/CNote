package com.example.cnote.feature_task.domain.use_case

import com.example.cnote.feature_task.domain.repository.TaskRepository

class GetTask(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(id:Int) {
        repository.getTaskById(id)
    }
}
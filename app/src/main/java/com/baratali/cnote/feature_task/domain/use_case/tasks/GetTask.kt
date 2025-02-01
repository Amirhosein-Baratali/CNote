package com.baratali.cnote.feature_task.domain.use_case.tasks

import com.baratali.cnote.feature_task.data.data_source.model.TaskWithCategory
import com.baratali.cnote.feature_task.domain.repository.TaskRepository

class GetTask(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(id: Int): TaskWithCategory? {
        return repository.getTaskWithCategoryById(id)
    }
}
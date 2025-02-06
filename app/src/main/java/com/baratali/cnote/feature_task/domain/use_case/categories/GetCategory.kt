package com.baratali.cnote.feature_task.domain.use_case.categories

import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class GetCategory @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int?): TaskCategory? {
        return id?.let { repository.getCategoryById(id) }
    }
}
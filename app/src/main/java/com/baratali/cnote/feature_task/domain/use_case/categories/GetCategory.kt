package com.baratali.cnote.feature_task.domain.use_case.categories

import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategory @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int): TaskCategory? {
        return repository.getCategoryById(id)
    }
}

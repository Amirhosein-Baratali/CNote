package com.baratali.cnote.feature_task.domain.use_case.categories

import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<TaskCategory>> {
        return repository.getCategories()
    }
}

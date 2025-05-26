package com.baratali.cnote.feature_task.domain.repository

import com.baratali.cnote.core.domain.repository.OrderRepository
import com.baratali.cnote.feature_task.data.data_source.model.Task
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.feature_task.data.data_source.model.TaskWithCategory
import kotlinx.coroutines.flow.Flow

interface TaskRepository : OrderRepository {
    fun getTasksWithCategory(): Flow<List<TaskWithCategory>>
    suspend fun getTaskWithCategoryById(id: Int): TaskWithCategory?
    suspend fun insertTask(task: Task): Long
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteCompletedTasks()
    fun getCategories(): Flow<List<TaskCategory>>
    suspend fun getCategoryById(id: Int): TaskCategory?
    suspend fun insertCategory(taskCategory: TaskCategory)
    suspend fun deleteCategory(taskCategory: TaskCategory)
}
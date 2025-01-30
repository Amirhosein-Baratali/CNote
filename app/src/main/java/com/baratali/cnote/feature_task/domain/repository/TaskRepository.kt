package com.baratali.cnote.feature_task.domain.repository

import com.baratali.cnote.core.domain.repository.OrderRepository
import com.baratali.cnote.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository : OrderRepository {

    fun getTasks(): Flow<List<Task>>

    suspend fun getTaskById(id: Int): Task?

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteCompletedTasks()
}
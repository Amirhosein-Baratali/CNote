package com.example.cnote.feature_task.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cnote.core.domain.util.Order
import com.example.cnote.core.util.getStringValue
import com.example.cnote.feature_task.data.data_source.TaskDao
import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val dao: TaskDao,
    private val dataStorePreferences: DataStore<Preferences>
) : TaskRepository {

    private companion object {
        val KEY_TASK_ORDER = stringPreferencesKey(name = "task_order")
    }

    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }

    override suspend fun deleteCompletedTasks() {
        dao.deleteCompletedTasks()
    }

    override suspend fun saveOrder(order: Order) {
        val taskOrderName = order.toStringValue()
        dataStorePreferences.edit { preferences ->
            preferences[KEY_TASK_ORDER] = taskOrderName
        }
    }

    override suspend fun getSavedOrder(): Order {
        val taskOrderName: String? = dataStorePreferences.getStringValue(KEY_TASK_ORDER)
        return Order.fromString(taskOrderName)
    }
}
package com.baratali.cnote.feature_task.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.core.util.getStringValue
import com.baratali.cnote.feature_task.data.data_source.TaskDao
import com.baratali.cnote.feature_task.data.data_source.model.Task
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.feature_task.data.data_source.model.TaskWithCategory
import com.baratali.cnote.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val dao: TaskDao,
    private val dataStorePreferences: DataStore<Preferences>
) : TaskRepository {

    private companion object {
        val KEY_TASK_ORDER = stringPreferencesKey(name = "task_order")
    }

    override fun getTasksWithCategory(): Flow<List<TaskWithCategory>> = dao.getTasksWithCategory()

    override suspend fun getTaskWithCategoryById(id: Int): TaskWithCategory? =
        dao.getTaskWithCategoryById(id)

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

    override fun getCategories(): Flow<List<TaskCategory>> = dao.getCategories()

    override suspend fun getCategoryById(id: Int): TaskCategory? {
        return dao.getCategoryById(id)
    }

    override suspend fun insertCategory(taskCategory: TaskCategory) =
        dao.insertCategory(taskCategory)

    override suspend fun deleteCategory(taskCategory: TaskCategory) =
        dao.deleteCategory(taskCategory)


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
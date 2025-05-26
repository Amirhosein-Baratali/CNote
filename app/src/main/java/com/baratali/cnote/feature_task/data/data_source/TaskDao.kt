package com.baratali.cnote.feature_task.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.baratali.cnote.feature_task.data.data_source.model.Task
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.feature_task.data.data_source.model.TaskWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Transaction
    @Query("SELECT * FROM task")
    fun getTasksWithCategory(): Flow<List<TaskWithCategory>>

    @Transaction
    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskWithCategoryById(id: Int): TaskWithCategory?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("delete from task where completed = 1")
    suspend fun deleteCompletedTasks()

    @Query("SELECT * FROM taskcategory")
    fun getCategories(): Flow<List<TaskCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(taskCategory: TaskCategory)

    @Query("SELECT * FROM taskcategory WHERE id = :id")
    suspend fun getCategoryById(id: Int): TaskCategory?

    @Delete
    suspend fun deleteCategory(taskCategory: TaskCategory)
}
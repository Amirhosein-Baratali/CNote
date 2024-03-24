package com.example.cnote.feature_task.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cnote.feature_task.domain.model.Task

@Database(
    entities = [Task::class],
    version = 1
)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

    companion object {
        const val DATABASE_NAME = "tasks_db"
    }
}
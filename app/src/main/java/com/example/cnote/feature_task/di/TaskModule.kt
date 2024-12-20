package com.example.cnote.feature_task.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.cnote.feature_task.data.data_source.TaskDatabase
import com.example.cnote.feature_task.data.repository.TaskRepositoryImpl
import com.example.cnote.feature_task.domain.repository.TaskRepository
import com.example.cnote.feature_task.domain.use_case.AddTask
import com.example.cnote.feature_task.domain.use_case.DeleteCompletedTasks
import com.example.cnote.feature_task.domain.use_case.DeleteTask
import com.example.cnote.feature_task.domain.use_case.GetTask
import com.example.cnote.feature_task.domain.use_case.GetTasks
import com.example.cnote.feature_task.domain.use_case.RetrieveTaskOrder
import com.example.cnote.feature_task.domain.use_case.StoreTaskOrder
import com.example.cnote.feature_task.domain.use_case.TaskUseCases
import com.example.cnote.feature_task.domain.use_case.UpdateTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase = Room.databaseBuilder(
        app, TaskDatabase::class.java, TaskDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideTaskRepository(
        db: TaskDatabase,
        dataStore: DataStore<Preferences>
    ): TaskRepository = TaskRepositoryImpl(db.taskDao, dataStore)

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases =
        TaskUseCases(
            getTasks = GetTasks(repository),
            addTask = AddTask(repository),
            getTask = GetTask(repository),
            deleteTask = DeleteTask(repository),
            updateTask = UpdateTask(repository),
            deleteCompletedTasks = DeleteCompletedTasks(repository),
            storeTaskOrder = StoreTaskOrder(repository),
            retrieveTaskOrder = RetrieveTaskOrder(repository)
        )
}
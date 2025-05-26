package com.baratali.cnote.feature_task.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.baratali.cnote.feature_task.data.data_source.TaskDatabase
import com.baratali.cnote.feature_task.data.repository.TaskRepositoryImpl
import com.baratali.cnote.feature_task.domain.repository.TaskRepository
import com.baratali.cnote.feature_task.domain.use_case.categories.AddCategory
import com.baratali.cnote.feature_task.domain.use_case.categories.CategoryUseCases
import com.baratali.cnote.feature_task.domain.use_case.categories.DeleteCategory
import com.baratali.cnote.feature_task.domain.use_case.categories.GetCategories
import com.baratali.cnote.feature_task.domain.use_case.categories.GetCategory
import com.baratali.cnote.feature_task.domain.use_case.notification.ScheduleTaskNotificationUseCase
import com.baratali.cnote.feature_task.domain.use_case.tasks.AddTask
import com.baratali.cnote.feature_task.domain.use_case.tasks.DeleteCompletedTasks
import com.baratali.cnote.feature_task.domain.use_case.tasks.DeleteTask
import com.baratali.cnote.feature_task.domain.use_case.tasks.GetTask
import com.baratali.cnote.feature_task.domain.use_case.tasks.GetTasks
import com.baratali.cnote.feature_task.domain.use_case.tasks.RetrieveTaskOrder
import com.baratali.cnote.feature_task.domain.use_case.tasks.StoreTaskOrder
import com.baratali.cnote.feature_task.domain.use_case.tasks.TaskUseCases
import com.baratali.cnote.feature_task.domain.use_case.tasks.UpdateTask
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
    fun provideTaskDatabase(
        app: Application,
        callback: TaskDatabase.Callback
    ): TaskDatabase =
        Room
            .databaseBuilder(
                app,
                TaskDatabase::class.java,
                TaskDatabase.DATABASE_NAME
            )
            .addMigrations(
                TaskDatabase.MIGRATION_1_2,
                TaskDatabase.MIGRATION_2_3,
            )
            .addCallback(callback)
            .build()

    @Provides
    @Singleton
    fun provideTaskRepository(
        db: TaskDatabase,
        dataStore: DataStore<Preferences>
    ): TaskRepository = TaskRepositoryImpl(db.taskDao, dataStore)

    @Provides
    @Singleton
    fun provideTaskUseCases(
        repository: TaskRepository,
        scheduleTaskNotificationUseCase: ScheduleTaskNotificationUseCase
    ): TaskUseCases =
        TaskUseCases(
            getTasks = GetTasks(repository),
            addTask = AddTask(repository, scheduleTaskNotificationUseCase),
            getTaskWithCategory = GetTask(repository),
            deleteTask = DeleteTask(repository),
            updateTask = UpdateTask(repository),
            deleteCompletedTasks = DeleteCompletedTasks(repository),
            storeTaskOrder = StoreTaskOrder(repository),
            retrieveTaskOrder = RetrieveTaskOrder(repository)
        )

    @Provides
    @Singleton
    fun provideCategoryUseCases(
        repository: TaskRepository
    ): CategoryUseCases =
        CategoryUseCases(
            getCategories = GetCategories(repository),
            getCategory = GetCategory(repository),
            addCategory = AddCategory(repository),
            deleteCategory = DeleteCategory(repository)
        )
}
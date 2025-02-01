package com.baratali.cnote.feature_task.domain.use_case.tasks

data class TaskUseCases(
    val getTasks: GetTasks,
    val addTask: AddTask,
    val getTaskWithCategory: GetTask,
    val deleteTask: DeleteTask,
    val updateTask: UpdateTask,
    val deleteCompletedTasks: DeleteCompletedTasks,
    val storeTaskOrder: StoreTaskOrder,
    val retrieveTaskOrder: RetrieveTaskOrder
)

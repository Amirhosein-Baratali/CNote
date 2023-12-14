package com.example.cnote.feature_task.domain.use_case

data class TaskUseCases(
    val getTasks: GetTasks,
    val addTask: AddTask,
    val getTask: GetTask,
    val deleteTask: DeleteTask,
    val updateTask: UpdateTask,
    val deleteCompletedTasks: DeleteCompletedTasks
)

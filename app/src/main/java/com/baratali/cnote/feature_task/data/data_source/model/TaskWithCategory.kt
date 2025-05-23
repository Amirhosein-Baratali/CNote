package com.baratali.cnote.feature_task.data.data_source.model

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithCategory(
    @Embedded val task: Task,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: TaskCategory?
) {
    fun matchesSearchQuery(query: String): Boolean {
        val taskMatches = task.matchWithSearchQuery(query)
        val categoryMatches = category?.name?.contains(query, ignoreCase = true) == true
        return taskMatches || categoryMatches
    }
}

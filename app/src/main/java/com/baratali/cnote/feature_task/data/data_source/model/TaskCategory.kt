package com.baratali.cnote.feature_task.data.data_source.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val icon: CategoryIcon, // Store icon name or reference
    val color: Long // Store color as a Long (use Color.toArgb())
){
    companion object {
        val sampleData =   TaskCategory(
            name = "Work",
            icon = CategoryIcon.UNIVERSITY,
            color = Color(0xFFFFA07A).toArgb().toLong()
        )
    }
}
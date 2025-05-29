package com.baratali.cnote.feature_task.data.data_source.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val icon: CategoryIcon,
    val color: Long
) {
    companion object {
        val sampleData = TaskCategory(
            name = "Work",
            icon = CategoryIcon.UNIVERSITY,
            color = Color(0xFFFFA07A).toArgb().toLong()
        )
        val sampleCategories = listOf(
            TaskCategory(
                name = "Personal",
                icon = CategoryIcon.SOCIAL,
                color = Color(0xFF87CEEB).toArgb().toLong()
            ),
            TaskCategory(
                name = "Health",
                icon = CategoryIcon.HEALTH,
                color = Color(0xFF98FB98).toArgb().toLong()
            ),
            TaskCategory(
                name = "Fitness",
                icon = CategoryIcon.SPORT,
                color = Color(0xFFFF6347).toArgb().toLong()
            ),
            TaskCategory(
                name = "Travel",
                icon = CategoryIcon.TRAVEL,
                color = Color(0xFF9370DB).toArgb().toLong()
            ),
            TaskCategory(
                name = "Food",
                icon = CategoryIcon.FOOD,
                color = Color(0xFFFFD700).toArgb().toLong()
            ),
            TaskCategory(
                name = "Study",
                icon = CategoryIcon.UNIVERSITY,
                color = Color(0xFF4682B4).toArgb().toLong()
            ),
            TaskCategory(
                name = "Hobbies",
                icon = CategoryIcon.MUSIC,
                color = Color(0xFFFF69B4).toArgb().toLong()
            ),
            TaskCategory(
                name = "Home",
                icon = CategoryIcon.HOME,
                color = Color(0xFFDEB887).toArgb().toLong()
            ),
            TaskCategory(
                name = "Finance",
                icon = CategoryIcon.MONEY,
                color = Color(0xFF32CD32).toArgb().toLong()
            )
        )
    }
}
package com.baratali.cnote.feature_task.domain.model

import androidx.compose.ui.graphics.Color

enum class TaskPriority(val value: Int, val color: Color) {
    LOW(1, Color(0xFF4CAF50)),
    MEDIUM_LOW(2, Color(0xFF8BC34A)),
    MEDIUM(3, Color(0xFFC2AB2D)),
    MEDIUM_HIGH(4, Color(0xFFFF9800)),
    HIGH(5, Color(0xFFF44336));

    companion object {
        fun fromValue(value: Int): TaskPriority {
            return values().firstOrNull { it.value == value } ?: MEDIUM
        }
    }
}
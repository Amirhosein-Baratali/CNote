package com.example.cnote.core.presentation.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Notes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cnote.feature_note.presentation.util.NoteScreens
import com.example.cnote.feature_task.presentation.util.TaskScreens

sealed class BottomNavItem(
    var title:String,
    var direction:String,
    var icon: ImageVector,
    var selected:Boolean = false,
) {

    object Notes: BottomNavItem(
        title = "Notes",
        direction = NoteScreens.Notes.route,
        icon = Icons.Default.Notes,
        selected = true
    )

    object Tasks: BottomNavItem(
        title = "Tasks",
        direction = TaskScreens.Tasks.route,
        icon = Icons.Default.Checklist
    )
}
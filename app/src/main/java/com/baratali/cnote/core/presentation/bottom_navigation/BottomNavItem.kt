package com.baratali.cnote.core.presentation.bottom_navigation

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val destination: Any
)
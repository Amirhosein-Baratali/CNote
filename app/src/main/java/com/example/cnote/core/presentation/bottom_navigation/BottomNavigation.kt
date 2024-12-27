package com.example.cnote.core.presentation.bottom_navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cnote.core.presentation.components.CustomText
import com.example.cnote.feature_note.presentation.util.NoteScreens
import com.example.cnote.feature_task.presentation.util.TaskScreens
import com.example.cnote.ui.theme.CNoteTheme

@Composable
fun BottomNavigation(navController: NavController) {

    val items = listOf(
        BottomNavItem(
            title = "Notes",
            selectedIcon = Icons.Filled.NoteAlt,
            unselectedIcon = Icons.Outlined.NoteAlt,
            destination = NoteScreens.Notes
        ),
        BottomNavItem(
            title = "Tasks",
            selectedIcon = Icons.Default.Checklist,
            unselectedIcon = Icons.Outlined.Checklist,
            destination = TaskScreens.Tasks
        )
    )
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.clip(MaterialTheme.shapes.extraSmall),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.destination)
                    Log.d("TTT", "BottomNavigation: $currentRoute")
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                ),
                label = {
                    CustomText(
                        text = item.title,
                        color = Color.Unspecified
                    )
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun BottomNavigationPreview() {
    CNoteTheme {
        BottomNavigation(navController = rememberNavController())
    }
}
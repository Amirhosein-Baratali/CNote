package com.example.cnote.core.presentation.bottom_navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cnote.ui.theme.CNoteTheme

@Composable
fun BottomNavigation(navController: NavController) {

    val items = listOf(
        BottomNavItem.Notes,
        BottomNavItem.Tasks
    )

    val contentColor = MaterialTheme.colorScheme.primaryContainer

    BottomAppBar(
//        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        contentColor = contentColor
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.selected,
                onClick = {
                    if (!item.selected) {
                        navController.navigate(item.direction)
                    }
                },
                /* colors = NavigationBarItemDefaults.colors(
                     selectedIconColor = contentColor,
                     selectedTextColor = contentColor,
                     unselectedIconColor = contentColor.copy(0.4f),
                     unselectedTextColor = contentColor.copy(0.4f)
                 ),*/
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                }
            )
        }
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        items.forEach { item ->
            item.selected = item.direction == destination.route
        }
    }
}

@Preview
@Composable
fun BottomNavigationPreview() {
    CNoteTheme {
        BottomNavigation(navController = rememberNavController())
    }
}
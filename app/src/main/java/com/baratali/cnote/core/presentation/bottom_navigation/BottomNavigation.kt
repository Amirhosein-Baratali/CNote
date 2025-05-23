package com.baratali.cnote.core.presentation.bottom_navigation

import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.feature_note.presentation.util.NoteScreens
import com.baratali.cnote.feature_task.presentation.util.TaskScreens
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun BottomNavigation(navController: NavController) {

    val items = listOf(
        BottomNavItem(
            title = stringResource(R.string.tasks),
            selectedIcon = painterResource(R.drawable.ic_check_list_fill),
            unselectedIcon = painterResource(R.drawable.ic_check_list_outlined),
            destination = TaskScreens.Tasks
        ),
        BottomNavItem(
            title = stringResource(R.string.notes),
            selectedIcon = painterResource(R.drawable.ic_book_fill),
            unselectedIcon = painterResource(R.drawable.ic_book_outlined),
            destination = NoteScreens.Notes
        )
    )
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        modifier = Modifier.clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        items.forEachIndexed { index, item ->
            val selected = selectedItemIndex == index
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (selectedItemIndex == index) return@NavigationBarItem
                    navController.navigate(item.destination)
                    selectedItemIndex = index
                },
                icon = {
                    Icon(
                        painter = if (selected) item.selectedIcon else item.unselectedIcon,
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
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            TaskScreens.TaskList.javaClass.canonicalName -> selectedItemIndex =
                items.indexOfFirst { it.destination == TaskScreens.Tasks }

            NoteScreens.NoteList.javaClass.canonicalName -> selectedItemIndex =
                items.indexOfFirst { it.destination == NoteScreens.Notes }
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
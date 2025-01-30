package com.baratali.cnote.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.baratali.cnote.feature_note.presentation.util.NoteScreens
import com.baratali.cnote.feature_note.presentation.util.notesNavigation
import com.baratali.cnote.feature_task.presentation.util.tasksNavigation
import com.baratali.cnote.settings.presentation.util.settingsNavigation
import com.baratali.cnote.ui.theme.CNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()

            CNoteTheme(
                darkTheme = viewModel.isDark.collectAsStateWithLifecycle().value
                    ?: isSystemInDarkTheme()
            ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NoteScreens.Notes
                ) {
                    notesNavigation(navController)
                    tasksNavigation(navController)
                    settingsNavigation(navController)
                }
            }
        }
    }
}
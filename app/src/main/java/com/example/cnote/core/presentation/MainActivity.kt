package com.example.cnote.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.cnote.feature_note.presentation.util.NoteScreens
import com.example.cnote.feature_note.presentation.util.notesNavigation
import com.example.cnote.feature_task.presentation.util.tasksNavigation
import com.example.cnote.ui.theme.CNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CNoteTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NoteScreens.Notes.route
                    ) {
                        notesNavigation(navController)
                        tasksNavigation(navController)
                    }
                }
            }
        }
    }
}
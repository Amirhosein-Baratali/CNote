package com.example.cnote.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.cnote.core.presentation.bottom_navigation.BottomNavigation
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
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigation(navController) }
                ) { padding ->
                    Surface(modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)) {
                        NavHost(
                            navController = navController,
                            startDestination = NoteScreens.Notes
                        ) {
                            notesNavigation(navController)
                            tasksNavigation(navController)
                        }
                    }
                }
            }
        }
    }
}
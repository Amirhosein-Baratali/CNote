package com.example.cnote.feature_note.presentation.util

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cnote.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.cnote.feature_note.presentation.notes.NotesScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.NotesNavigation(navController: NavController) {
    composable(route = NoteScreens.Notes.route) {
        NotesScreen(navController = navController)
    }
    composable(
        route = NoteScreens.AddEditNote.route +
                "?noteId={noteId}&noteColor={noteColor}",
        arguments = listOf(
            navArgument(
                name = "noteId"
            ) {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument(
                name = "noteColor"
            ) {
                type = NavType.IntType
                defaultValue = -1
            },
        )
    ) {
        val color = it.arguments?.getInt("noteColor") ?: -1
        AddEditNoteScreen(
            navController = navController,
            noteColor = color
        )
    }
}


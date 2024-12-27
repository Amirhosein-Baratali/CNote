package com.example.cnote.feature_note.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.cnote.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.cnote.feature_note.presentation.notes.NotesScreen

fun NavGraphBuilder.notesNavigation(navController: NavController) {
    composable<NoteScreens.Notes> {
        NotesScreen(navController = navController)
    }
    composable<NoteScreens.AddEditNote> {
        val color = it.toRoute<NoteScreens.AddEditNote>().noteColor
        AddEditNoteScreen(
            navController = navController,
            noteColor = color
        )
    }
}


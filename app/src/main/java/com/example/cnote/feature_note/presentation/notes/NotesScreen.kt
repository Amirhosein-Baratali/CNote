package com.example.cnote.feature_note.presentation.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cnote.R
import com.example.cnote.core.presentation.TopBar
import com.example.cnote.core.presentation.bottom_navigation.BottomNavigation
import com.example.cnote.core.presentation.components.FloatingAddButton
import com.example.cnote.feature_note.presentation.notes.components.NoteItem
import com.example.cnote.feature_note.presentation.util.NoteScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState(NotesState())

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            FloatingAddButton(
                onClick = { navController.navigate(NoteScreens.AddEditNote.route) },
                stringResource(R.string.add_note),
            )
        },
        topBar = {
            TopBar(
                stringResource(R.string.notes),
                order = state.noteOrder,
                onOrderChange = { viewModel.onEvent(NotesEvent.Sort(it)) },
                onSearchQueryChange = { viewModel.onEvent(NotesEvent.OnSearchQueryChanged(it)) }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { BottomNavigation(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(state.notes) { note ->
                NoteItem(
                    note = note,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                NoteScreens.AddEditNote.route +
                                        "?${NoteScreens.ARG_NOTE_ID}=${note.id}" +
                                        "&${NoteScreens.ARG_NOTE_COLOR}=${note.color}"
                            )
                        },
                    onDeleteClick = {
                        viewModel.onEvent(NotesEvent.DeleteNote(note))
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Note deleted",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Short
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(NotesEvent.RestoreNote)
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
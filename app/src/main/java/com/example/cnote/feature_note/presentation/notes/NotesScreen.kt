package com.example.cnote.feature_note.presentation.notes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cnote.R
import com.example.cnote.core.presentation.TopBar
import com.example.cnote.core.presentation.components.FloatingAddButton
import com.example.cnote.core.presentation.components.LightAndDarkPreview
import com.example.cnote.core.presentation.components.snackbar.CustomScaffold
import com.example.cnote.feature_note.presentation.notes.components.NoteItem
import com.example.cnote.feature_note.presentation.util.NoteScreens
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NotesViewModel.UIEvent.NavigateToEditNote -> {
                    navController.navigate(NoteScreens.AddEditNote(event.note.id, event.note.color))
                }

                NotesViewModel.UIEvent.NavigateToAddNote -> {
                    navController.navigate(NoteScreens.AddEditNote(null, null))
                }
            }
        }
    }
    CustomScaffold(
        navController = navController,
        floatingActionButton = {
            FloatingAddButton(
                onClick = { viewModel.onEvent(NotesEvent.AddButtonCLicked) },
                contentDescription = stringResource(R.string.add_note),
            )
        },
        topBar = {
            TopBar(
                stringResource(R.string.notes),
                order = state.noteOrder,
                onOrderChange = { viewModel.onEvent(NotesEvent.Sort(it)) },
                onSearchQueryChange = { viewModel.onEvent(NotesEvent.OnSearchQueryChanged(it)) }
            )
        }
    ) {
        NotesScreenContent(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun NotesScreenContent(
    modifier: Modifier = Modifier,
    state: NotesState,
    onEvent: (NotesEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        items(state.notes) { note ->
            NoteItem(
                note = note,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                onClick = { onEvent(NotesEvent.OnNoteClicked(note)) },
                onDeleteClick = { onEvent(NotesEvent.DeleteNote(note)) }
            )
        }
    }
}

@LightAndDarkPreview
@Composable
private fun NotesScreenPreview() {
    NotesScreenContent(
        state = NotesState(),
        onEvent = {}
    )
}
package com.baratali.cnote.feature_note.presentation.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.TopBar
import com.baratali.cnote.core.presentation.components.FloatingAddButton
import com.baratali.cnote.core.presentation.components.LightAndDarkPreview
import com.baratali.cnote.core.presentation.components.snackbar.CustomScaffold
import com.baratali.cnote.feature_note.presentation.notes.components.NoteItem
import com.baratali.cnote.feature_note.presentation.util.NoteScreens
import com.baratali.cnote.settings.presentation.util.SettingScreens
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
                    navController.navigate(NoteScreens.AddEditNote(event.note.id))
                }

                NotesViewModel.UIEvent.NavigateToAddNote -> {
                    navController.navigate(NoteScreens.AddEditNote())
                }

                NotesViewModel.UIEvent.NavigateToSettings -> {
                    navController.navigate(SettingScreens.Settings)
                }

                is NotesViewModel.UIEvent.NavigateToPassword -> {
                    navController.navigate(
                        SettingScreens.Password(
                            mode = event.mode,
                            noteId = event.noteId
                        )
                    )
                }
            }
        }
    }

    NotesScreenContent(
        navController = navController,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun NotesScreenContent(
    navController: NavController,
    state: NotesState,
    onEvent: (NotesEvent) -> Unit
) {
    var moreMenuExpanded by remember { mutableStateOf(false) }

    CustomScaffold(
        navController = navController,
        floatingActionButton = {
            FloatingAddButton(
                onClick = { onEvent(NotesEvent.AddButtonCLicked) },
                contentDescription = stringResource(R.string.add_note),
            )
        },
        topBar = {
            TopBar(
                stringResource(R.string.notes),
                order = state.noteOrder,
                onOrderChange = { onEvent(NotesEvent.Sort(it)) },
                settingsClicked = {
                    onEvent(NotesEvent.SettingsClicked)
                    moreMenuExpanded = false
                },
                moreExpanded = moreMenuExpanded,
                onMoreExpandedChange = { moreMenuExpanded = it },
                onSearchQueryChange = { onEvent(NotesEvent.OnSearchQueryChanged(it)) }
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.notes) { note ->
                NoteItem(
                    note = note,
                    modifier = Modifier.aspectRatio(1.1f),
                    onClick = { onEvent(NotesEvent.OnNoteClicked(note)) },
                    onDeleteClick = { onEvent(NotesEvent.DeleteNote(note)) },
                    onLockClick = { onEvent(NotesEvent.LockNote(note)) }
                )
            }
        }
    }
}

@LightAndDarkPreview
@Composable
private fun NotesScreenPreview() {
    NotesScreenContent(
        navController = rememberNavController(),
        state = NotesState(),
        onEvent = {}
    )
}
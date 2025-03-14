package com.baratali.cnote.feature_note.presentation.add_edit_note

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.TransparentHintTextField
import com.baratali.cnote.core.presentation.components.snackbar.CustomScaffold
import com.baratali.cnote.core.util.TestTags
import com.baratali.cnote.feature_note.domain.model.Note
import com.baratali.cnote.feature_note.presentation.add_edit_note.components.ConfirmExitDialog
import com.baratali.cnote.ui.theme.CNoteTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val state by viewModel.noteState.collectAsStateWithLifecycle()

    AddEditNoteScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        navController = navController
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.NavigateUp -> {
                    navController.navigateUp()
                }
            }
        }
    }

}

@Composable
fun AddEditNoteScreenContent(
    navController: NavController,
    state: NoteState,
    onEvent: (AddEditNoteEvent) -> Unit
) {
    val noteBackgroundAnimatable = remember(state.color) {
        Animatable(
            Color(state.color)
        )
    }
    val scope = rememberCoroutineScope()

    CustomScaffold(
        navController = navController,
        showBottomBar = false,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(AddEditNoteEvent.SaveNote) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_clipboard_tick),
                    contentDescription = stringResource(R.string.save_note)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (state.color == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.title,
                hint = stringResource(id = R.string.title),
                onValueChange = {
                    onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                contentColor = Color.Black,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                testTag = TestTags.NOTE_TITLE_TEXT_FIELD
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                modifier = Modifier.weight(1f),
                text = state.content,
                hint = stringResource(id = R.string.content),
                contentColor = Color.Black,
                onValueChange = { onEvent(AddEditNoteEvent.EnteredContent(it)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                testTag = TestTags.NOTE_CONTENT_TEXT_FIELD
            )
        }
        if (state.showExitDialog) {
            ConfirmExitDialog(
                onConfirmExit = { onEvent(AddEditNoteEvent.SaveNote) },
                onDismissRequest = { onEvent(AddEditNoteEvent.ExitDialogDismissed) },
                onDiscardChanges = { navController.navigateUp() }
            )
        }
        BackHandler {
            onEvent(AddEditNoteEvent.BackButtonClicked)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddEditNote() {
    CNoteTheme {
        AddEditNoteScreenContent(
            navController = rememberNavController(),
            state = NoteState(),
            onEvent = {}
        )
    }
}
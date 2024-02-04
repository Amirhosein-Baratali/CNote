package com.example.cnote.feature_task.presentation.add_edit_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.IncompleteCircle
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cnote.R
import com.example.cnote.core.presentation.TextFieldState
import com.example.cnote.core.presentation.components.TransparentHintTextField
import com.example.cnote.ui.theme.CNoteTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditTaskScreen(
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    AddEditTaskScreenContent(
        onEvent = viewModel::onEvent,
        name = viewModel.taskName.value,
        desc = viewModel.taskDescription.value,
        isImportant = viewModel.isImportantState.value,
        isCompleted = viewModel.isCompletedState.value,
        snackbarHostState = snackbarHostState
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditTaskViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditTaskViewModel.UiEvent.SaveTask -> {
                    navController.navigateUp()
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreenContent(
    onEvent: (AddEditTaskEvent) -> Unit,
    name: TextFieldState,
    desc: TextFieldState,
    isImportant: Boolean,
    isCompleted: Boolean,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(AddEditTaskEvent.SaveTask)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.save_task)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TransparentHintTextField(
                text = name.text,
                hint = name.hint,
                onValueChange = {
                    onEvent(AddEditTaskEvent.EnteredName(it))
                },
                onFocusChange = {
                    onEvent(AddEditTaskEvent.ChangeNameFocus(it))
                },
                isHintVisible = name.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isImportant,
                    onCheckedChange = {
                        onEvent(AddEditTaskEvent.ToggleImportance)
                    }
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.important_task),
                    style = MaterialTheme.typography.bodyMedium
                )
                if (isImportant) {
                    Icon(
                        painter = painterResource(id = R.drawable.exclamation_mark),
                        tint = Color.Red,
                        contentDescription = "This task is important"
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isCompleted,
                    onCheckedChange = {
                        onEvent(AddEditTaskEvent.ToggleCompletion)
                    }
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.task_completed),
                    style = MaterialTheme.typography.bodyMedium
                )

                Icon(
                    imageVector = if (isCompleted) Icons.Default.DoneOutline
                    else Icons.Default.IncompleteCircle,
                    contentDescription = null,
                    tint = if (isCompleted) Color.Green else Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = desc.text,
                hint = desc.hint,
                onValueChange = {
                    onEvent(AddEditTaskEvent.EnteredDescription(it))
                },
                onFocusChange = {
                    onEvent(AddEditTaskEvent.ChangeDescriptionFocus(it))
                },
                isHintVisible = desc.isHintVisible,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun AddEditTaskScreenPreview() {
    CNoteTheme {
        AddEditTaskScreenContent(
            onEvent = {},
            name = TextFieldState(text = "Some Task Name"),
            desc = TextFieldState(hint = "enter desc..."),
            isImportant = false,
            isCompleted = false,
            snackbarHostState = SnackbarHostState()
        )
    }
}
package com.example.cnote.feature_task.presentation.add_edit_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cnote.R
import com.example.cnote.core.presentation.components.CustomText
import com.example.cnote.core.util.showShortToast
import com.example.cnote.ui.theme.CNoteTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskBottomSheet(
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditTaskViewModel.UiEvent.ShowError -> {
                    context.showShortToast(event.message)
                }

                is AddEditTaskViewModel.UiEvent.SaveTask -> {
                    navController.navigateUp()
                }

                AddEditTaskViewModel.UiEvent.Dismiss -> {
                    navController.popBackStack()
                }
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { viewModel.onEvent(AddEditTaskEvent.Dismiss) },
        sheetState = sheetState,
        dragHandle = {}
    ) {
        AddEditTaskContent(
            state = state,
            onEvent = viewModel::onEvent
        )

    }
}

@Composable
fun AddEditTaskContent(
    state: AddEditTaskState,
    onEvent: (AddEditTaskEvent) -> Unit
) {
    val textFieldStyle =
        MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        val focusRequester = FocusRequester()
        OutlinedTextField(
            value = state.name,
            onValueChange = { onEvent(AddEditTaskEvent.EnteredName(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            placeholder = {
                CustomText(text = stringResource(R.string.name))
            },
            keyboardActions = KeyboardActions(
                onNext = { focusRequester.requestFocus() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = state.description,
            onValueChange = { onEvent(AddEditTaskEvent.EnteredDescription(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .focusRequester(focusRequester),
            placeholder = {
                CustomText(text = stringResource(R.string.description))
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = "Timer Icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Category,
                    contentDescription = "Category Icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Flag,
                    contentDescription = "Flag Icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { onEvent(AddEditTaskEvent.SaveTask) }) {
                Icon(
                    imageVector = Icons.Outlined.Save,
                    contentDescription = stringResource(R.string.save_task),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun AddEditTaskBottomSheetPreview(modifier: Modifier = Modifier) {
    CNoteTheme {
        AddEditTaskContent(
            state = AddEditTaskState(),
            onEvent = {}
        )
    }
}
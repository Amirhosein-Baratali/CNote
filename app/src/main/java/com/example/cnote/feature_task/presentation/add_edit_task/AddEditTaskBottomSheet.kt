package com.example.cnote.feature_task.presentation.add_edit_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cnote.R
import com.example.cnote.core.presentation.components.CustomText
import com.example.cnote.core.presentation.components.snackbar.CustomScaffold
import com.example.cnote.feature_task.presentation.add_edit_task.component.CustomDatePicker
import com.example.cnote.ui.theme.CNoteTheme
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime

@Composable
fun AddEditTaskBottomSheet(
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddEditTaskViewModel.UIEvent.NavigateUp -> {
                    navController.popBackStack()
                }
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    CustomScaffold(
        navController = navController,
        showBottomBar = false,
        containerColor = Color.Transparent
    ) {
        AddEditTaskContent(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskContent(
    state: AddEditTaskState,
    onEvent: (AddEditTaskEvent) -> Unit
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        skipHiddenState = false
    )
    ModalBottomSheet(
        onDismissRequest = { onEvent(AddEditTaskEvent.Dismiss) },
        sheetState = sheetState,
        dragHandle = {},
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        tonalElevation = 8.dp,
        scrimColor = Color.Transparent
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            val focusRequester = FocusRequester()
            OutlinedTextField(
                value = state.name,
                onValueChange = { onEvent(AddEditTaskEvent.NameChanged(it)) },
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
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
            )

            OutlinedTextField(
                value = state.description,
                onValueChange = { onEvent(AddEditTaskEvent.DescriptionChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .focusRequester(focusRequester),
                placeholder = {
                    CustomText(text = stringResource(R.string.description))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { onEvent(AddEditTaskEvent.DateClicked) }) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
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
    if (state.showDatePicker) {
        CustomDatePicker(
            defaultDateTime = state.date ?: LocalDateTime.now(),
            onDateTimeSelected = { onEvent(AddEditTaskEvent.DateSelected(it)) },
            onDismiss = { (onEvent(AddEditTaskEvent.DateDismissed)) }
        )
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
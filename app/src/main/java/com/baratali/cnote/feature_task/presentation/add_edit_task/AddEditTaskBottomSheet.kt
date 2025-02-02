package com.baratali.cnote.feature_task.presentation.add_edit_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.core.presentation.components.snackbar.CustomScaffold
import com.baratali.cnote.core.util.showShortToast
import com.baratali.cnote.feature_task.presentation.add_edit_task.component.CustomDatePicker
import com.baratali.cnote.feature_task.presentation.add_edit_task.component.PriorityBottomSheet
import com.baratali.cnote.feature_task.presentation.util.TaskScreens
import com.baratali.cnote.ui.theme.CNoteTheme
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime

@Composable
fun AddEditTaskBottomSheet(
    navController: NavController,
    selectedCategoryId: Int? = null,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val generalSavingError = stringResource(R.string.cant_save_task)
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(selectedCategoryId) {
        selectedCategoryId?.let { viewModel.updateSelectedCategory(it) }
    }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddEditTaskViewModel.UIEvent.NavigateUp -> {
                    navController.popBackStack()
                }

                is AddEditTaskViewModel.UIEvent.ShowError -> {
                    val errorMessage = event.message ?: generalSavingError
                    context.showShortToast(errorMessage)
                }

                AddEditTaskViewModel.UIEvent.NavigateToCategoryPicker -> {
                    navController.navigate(TaskScreens.Categories(state.selectedCategory?.id))
                }
            }
        }
    }

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
    var showPrioritySheet by remember { mutableStateOf(false) }

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
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    focusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
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
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    focusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { onEvent(AddEditTaskEvent.DateClicked) }) {
                    val hasDate = state.date != null
                    Icon(
                        painter = if (hasDate) painterResource(R.drawable.ic_calendar_fill)
                        else painterResource(R.drawable.ic_calendar),
                        contentDescription = "Timer Icon",
                        tint = if (hasDate) MaterialTheme.colorScheme.surfaceTint
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { onEvent(AddEditTaskEvent.CategoryClicked) }) {

                    if (state.selectedCategory?.icon?.imageVector != null) {//FIXME replace image vector
                        Icon(
                            imageVector = state.selectedCategory.icon.imageVector,
                            contentDescription = "Category Icon",
                            tint = state.selectedCategory.color.let { Color(it) }
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_category),
                            contentDescription = "Category Icon",
                            tint = state.selectedCategory?.color?.let { Color(it) }
                                ?: MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                IconButton(onClick = { showPrioritySheet = true }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_flag),
                        contentDescription = "Flag Icon",
                        tint = state.priority.color
                    )
                }
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = { onEvent(AddEditTaskEvent.SaveTask) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_clipboard_tick),
                        contentDescription = stringResource(R.string.save_task)
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
    if (showPrioritySheet) {
        PriorityBottomSheet(
            selectedPriority = state.priority,
            onPrioritySelected = {
                onEvent(AddEditTaskEvent.PrioritySelected(it))
                showPrioritySheet = false
            },
            onDismiss = { showPrioritySheet = false }
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
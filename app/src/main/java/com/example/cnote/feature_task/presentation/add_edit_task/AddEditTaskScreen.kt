package com.example.cnote.feature_task.presentation.add_edit_task

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cnote.core.presentation.TextFieldState
import com.example.cnote.core.util.showShortToast
import com.example.cnote.feature_task.presentation.add_edit_task.components.AddEditTaskDialog
import com.example.cnote.ui.theme.CNoteTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditTaskScreen(
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    AddEditTaskDialog(
        modifier = Modifier,
        title = viewModel.title.value,
        onEvent = viewModel::onEvent,
        name = viewModel.taskName.value,
        desc = viewModel.taskDescription.value,
        isImportant = viewModel.isImportantState.value,
        isCompleted = viewModel.isCompletedState.value,
        onDismiss = { navController.navigateUp() }
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditTaskViewModel.UiEvent.ShowError -> {
                    context.showShortToast(event.message)
                }

                is AddEditTaskViewModel.UiEvent.SaveTask -> {
                    navController.navigateUp()
                }
            }
        }
    }

}


@Preview
@Composable
fun AddEditTaskScreenPreview() {
    CNoteTheme {
        AddEditTaskDialog(
            title = "Add Task",
            onEvent = {},
            name = TextFieldState(),
            desc = TextFieldState(),
            isImportant = true,
            isCompleted = false,
            onDismiss = {}
        )
    }
}
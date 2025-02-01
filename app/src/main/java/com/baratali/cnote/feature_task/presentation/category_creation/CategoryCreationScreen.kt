package com.baratali.cnote.feature_task.presentation.category_creation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.feature_task.data.data_source.model.CategoryColor
import com.baratali.cnote.feature_task.data.data_source.model.CategoryIcon
import com.baratali.cnote.ui.theme.CNoteTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CategoryCreationScreen(
    navController: NavController,
    viewModel: CategoryCreationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                CategoryCreationViewModel.UIEvent.NavigateUp -> {
                    navController.popBackStack()
                }
            }
        }
    }
    CategoryCreationContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun CategoryCreationContent(
    state: CategoryCreationState,
    onEvent: (CategoryCreationEvent) -> Unit
) {
    val selectedIcon = state.selectedIcon
    val selectedColor = state.selectedColor
    AlertDialog(
        onDismissRequest = { onEvent(CategoryCreationEvent.Dismiss) },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(CategoryCreationEvent.SaveCategory)
                },
                enabled = state.inputsValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                CustomText(stringResource(R.string.create))
            }
        },
        dismissButton = {
            TextButton(onClick = { onEvent(CategoryCreationEvent.Dismiss) }) {
                CustomText(stringResource(R.string.cancel))
            }
        },
        title = { CustomText("Create New Category") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { onEvent(CategoryCreationEvent.NameChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { CustomText(stringResource(R.string.enter_category_name)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomText("Choose Icon", style = MaterialTheme.typography.bodyMedium)

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(CategoryIcon.values()) { icon ->
                        Icon(
                            imageVector = icon.imageVector,
                            contentDescription = icon.name,
                            tint = if (icon == selectedIcon) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    if (icon == selectedIcon) MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.3f
                                    ) else Color.Transparent
                                )
                                .clickable { onEvent(CategoryCreationEvent.IconSelected(icon)) }
                                .padding(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                CustomText("Choose Color", style = MaterialTheme.typography.bodyMedium)

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(CategoryColor.values()) { color ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color.color)
                                .border(
                                    width = if (color == selectedColor) 4.dp else 0.dp,
                                    color = if (color == selectedColor) MaterialTheme.colorScheme.onSurface else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable { onEvent(CategoryCreationEvent.ColorSelected(color)) }
                        )
                    }
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun CategoryCreationPreview() {
    CNoteTheme {
        CategoryCreationContent(
            state = CategoryCreationState(),
            onEvent = {}
        )
    }
}
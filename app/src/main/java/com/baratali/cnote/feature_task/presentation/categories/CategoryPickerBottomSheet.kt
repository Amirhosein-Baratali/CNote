package com.baratali.cnote.feature_task.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.feature_task.presentation.categories.component.AddCategoryItem
import com.baratali.cnote.feature_task.presentation.categories.component.CategoryItem
import com.baratali.cnote.feature_task.presentation.categories.component.NoCategoryItem
import com.baratali.cnote.feature_task.presentation.util.TaskScreens
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CategoryPickerBottomSheet(
    navController: NavController,
    viewModel: CategoryPickerViewmodel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                CategoryPickerViewmodel.UIEvent.NavigateToCategoryCreation -> {
                    navController.navigate(TaskScreens.CategoryCreation)
                }

                CategoryPickerViewmodel.UIEvent.NavigateUp -> {
                    navController.popBackStack()
                }

                CategoryPickerViewmodel.UIEvent.NavigateUpWithResult -> {
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        set(
                            key = TaskScreens.KEY_SELECTED_CATEGORY_ID,
                            value = state.selectedCategoryId
                        )
                        val currentRefreshCount = get(TaskScreens.KEY_REFRESH_COUNT) ?: 0
                        set(
                            key = TaskScreens.KEY_REFRESH_COUNT,
                            value = currentRefreshCount + 1
                        )
                    }
                    navController.popBackStack()
                }
            }
        }
    }
    CategoryPickerBottomSheetContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPickerBottomSheetContent(
    state: CategoryPickerState,
    onEvent: (CategoryPickerEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = { onEvent(CategoryPickerEvent.Dismiss) },
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        tonalElevation = 8.dp,
        scrimColor = Color.Black.copy(alpha = 0.3f),
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomText(
                    text = "Choose Category",
                    style = MaterialTheme.typography.titleMedium
                )

                IconButton(
                    onClick = {
                        if (state.isEditMode) {
                            onEvent(CategoryPickerEvent.ExitEditMode)
                        } else {
                            onEvent(CategoryPickerEvent.EnterEditMode)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            if (state.isEditMode) R.drawable.ic_close_square
                            else R.drawable.ic_edit
                        ),
                        contentDescription =
                        if (state.isEditMode) "Cancel Edit Mode"
                        else "Edit Categories",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    NoCategoryItem(
                        isSelected = state.selectedCategoryId == null,
                        onClick = { onEvent(CategoryPickerEvent.NoCategorySelected) })
                }
                items(state.categories) { category ->
                    val isSelected = category.id == state.selectedCategoryId
                    CategoryItem(
                        category = category,
                        isSelected = isSelected,
                        isEditMode = state.isEditMode,
                        onClick = { onEvent(CategoryPickerEvent.CategorySelected(category)) },
                        onLongClick = { onEvent(CategoryPickerEvent.EnterEditMode) },
                        onDelete = {
                            onEvent(
                                CategoryPickerEvent.DeleteCategory(
                                    category,
                                    isSelected
                                )
                            )
                        }
                    )
                }
                item {
                    AddCategoryItem(
                        onClick = { onEvent(CategoryPickerEvent.AddNewCategory) }
                    )
                }
            }
        }
    }
}
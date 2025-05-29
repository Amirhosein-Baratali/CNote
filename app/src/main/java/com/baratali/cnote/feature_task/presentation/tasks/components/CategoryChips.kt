package com.baratali.cnote.feature_task.presentation.tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.feature_task.data.data_source.model.CategoryIcon
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun CategoryChips(
    modifier: Modifier = Modifier,
    categories: List<TaskCategory>,
    onSelectedCategoryChanged: (TaskCategory?) -> Unit,
    selectedCategory: TaskCategory?
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            CategoryChipItem(
                category = TaskCategory(
                    name = stringResource(R.string.all),
                    icon = CategoryIcon.DEFAULT,
                    color = colorScheme.primaryContainer.toArgb().toLong()
                ),
                isSelected = selectedCategory == null,
                onClick = { onSelectedCategoryChanged(null) },
            )
        }
        items(categories) { category ->
            CategoryChipItem(
                category = category,
                isSelected = selectedCategory == category,
                onClick = { onSelectedCategoryChanged(category) }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CategoryChipsPreview() {
    CNoteTheme {
        val categories = TaskCategory.sampleCategories
        CategoryChips(
            categories = categories,
            selectedCategory = categories[1],
            onSelectedCategoryChanged = {}
        )
    }
}
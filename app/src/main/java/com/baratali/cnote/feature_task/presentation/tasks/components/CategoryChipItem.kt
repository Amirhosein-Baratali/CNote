package com.baratali.cnote.feature_task.presentation.tasks.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.baratali.cnote.core.presentation.components.LightAndDarkPreview
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun CategoryChipItem(
    modifier: Modifier = Modifier,
    category: TaskCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(if (isSelected) 1.05f else 1f, label = "chip_scale")
    val categoryColor = Color(category.color)

    Row(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .scale(scale)
            .clip(MaterialTheme.shapes.medium)
            .background(
                color = if (isSelected) categoryColor.copy(alpha = 0.2f)
                else MaterialTheme.colorScheme.surface
            )
            .border(
                width = 1.dp,
                color = if (isSelected) categoryColor else MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        category.icon.iconRes?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = category.name,
                tint = if (isSelected) categoryColor else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) categoryColor else MaterialTheme.colorScheme.onSurface
        )
    }
}

@LightAndDarkPreview
@Composable
private fun CategoryChipItemPreview() {
    CNoteTheme {
        CategoryChipItem(
            category = TaskCategory.sampleData,
            isSelected = isSystemInDarkTheme(),
            onClick = {}
        )
    }
}
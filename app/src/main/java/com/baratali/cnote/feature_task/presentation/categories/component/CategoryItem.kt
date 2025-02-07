package com.baratali.cnote.feature_task.presentation.categories.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.core.presentation.components.LightAndDarkPreview
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.ui.theme.CNoteTheme
import com.baratali.cnote.ui.theme.typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItem(
    category: TaskCategory,
    isSelected: Boolean,
    isEditMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onDelete: () -> Unit
) {
    val containerColor = Color(category.color)
    val contentColor = Color.Black

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor.copy(alpha = if (isSelected) 0.7f else 0.3f))
            .combinedClickable(
                onClick = { if (!isEditMode) onClick() },
                onLongClick = onLongClick
            )
            .padding(12.dp)
            .size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .scale(if (isSelected) 1.3f else 1f),
                imageVector = category.icon.imageVector,
                contentDescription = category.name,
                tint = contentColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            CustomText(
                text = category.name,
                style =
                if (isSelected) typography.bodyMedium.copy(fontWeight = FontWeight.Black)
                else typography.bodySmall,
                textAlign = TextAlign.Center,
                color = contentColor
            )
        }

        if (isEditMode && isSelected.not()) {
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_trash),
                    contentDescription = "Delete",
                    tint = colorScheme.primary
                )
            }
        }
    }
}

@LightAndDarkPreview
@Composable
private fun CategoryItemPreview() {
    CNoteTheme {
        CategoryItem(
            category = TaskCategory.sampleData,
            isSelected = true,
            onClick = {},
            onLongClick = {},
            onDelete = {},
            isEditMode = false
        )
    }
}
package com.baratali.cnote.feature_task.presentation.tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.core.presentation.components.RoundedCornerCheckbox
import com.baratali.cnote.feature_task.data.data_source.model.Task
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.feature_task.data.data_source.model.TaskWithCategory
import com.baratali.cnote.ui.theme.CNoteTheme
import com.baratali.cnote.ui.theme.spacing
import java.time.LocalDateTime

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    taskWithCategory: TaskWithCategory,
    onTaskClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCheckClick: (isChecked: Boolean) -> Unit
) {
    val containerColor = MaterialTheme.colorScheme.primaryContainer
    val contentColor = MaterialTheme.colorScheme.onPrimaryContainer

    val nameText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                textDecoration =
                if (taskWithCategory.task.completed) TextDecoration.LineThrough
                else TextDecoration.None,
                color = contentColor
            )
        ) {
            append(taskWithCategory.task.name)
        }
    }

    ElevatedCard(
        modifier = modifier,
        onClick = { onTaskClick() },
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.xxSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedCornerCheckbox(
                modifier = Modifier.padding(12.dp),
                checkedColor = contentColor,
                uncheckedColor = containerColor,
                isChecked = taskWithCategory.task.completed,
                onValueChange = { onCheckClick(it) },
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = MaterialTheme.spacing.small)
            ) {
                CustomText(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    text = nameText,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start
                )
                taskWithCategory.task.formattedDate?.let {
                    CustomText(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = it
                    )
                }
            }
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.xxSmall),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_flag),
                        contentDescription = "Task priority",
                        tint = taskWithCategory.task.priority.color
                    )
                    IconButton(
                        onClick = onDeleteClick
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_trash),
                            contentDescription = "Delete task",
                            tint = contentColor
                        )
                    }
                }
                taskWithCategory.category?.let { CategoryLabel(category = it) }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun TaskItemPreview() {
    CNoteTheme {
        TaskItem(
            taskWithCategory = TaskWithCategory(
                task = Task(
                    name = "تست",
                    description = "Something",
                    completed = false,
                    date = LocalDateTime.now()
                ),
                category = TaskCategory.sampleData
            ),
            onDeleteClick = {},
            onCheckClick = {},
            onTaskClick = {}
        )
    }
}
package com.baratali.cnote.feature_task.presentation.tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.feature_task.domain.model.Task
import com.baratali.cnote.ui.theme.CNoteTheme
import com.baratali.cnote.ui.theme.spacing
import java.time.LocalDateTime

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
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
                if (task.completed) TextDecoration.LineThrough
                else TextDecoration.None,
                color = contentColor
            )
        ) {
            append(task.name)
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
            Checkbox(
                checked = task.completed,
                onCheckedChange = onCheckClick,
                colors = CheckboxDefaults.colors(
                    uncheckedColor = contentColor,
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = MaterialTheme.spacing.small),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CustomText(
                    text = nameText,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start
                )
                task.formattedDate?.let { date ->
                    Spacer(Modifier.height(MaterialTheme.spacing.small))
                    Row {
                        CustomText(date)
                    }
                }
            }
            Icon(
                imageVector = Icons.Outlined.Flag,
                contentDescription = "Task priority",
                tint = task.priority.color
            )
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete task",
                    tint = contentColor
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun TaskItemPreview() {
    CNoteTheme {
        TaskItem(
            task = Task(
                name = "Sample Task to show a large name what should i do to have you",
                description = "Something",
                completed = false,
                date = LocalDateTime.now()
            ),
            onDeleteClick = {},
            onCheckClick = {},
            onTaskClick = {}
        )
    }
}
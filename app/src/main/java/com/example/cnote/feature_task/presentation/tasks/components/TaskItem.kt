package com.example.cnote.feature_task.presentation.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cnote.R
import com.example.cnote.feature_task.domain.model.Task
import com.example.cnote.ui.theme.CNoteTheme
import com.example.cnote.ui.theme.spacing

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onDeleteClick: () -> Unit,
    onCheckClick: (isChecked: Boolean) -> Unit
) {
    val completed = task.completed

    val taskNameDecoration = if (completed) TextDecoration.LineThrough else TextDecoration.None

    val bgColor = MaterialTheme.colorScheme.surfaceVariant
    val contentColor = MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = bgColor, shape = MaterialTheme.shapes.medium)
            .padding(
                vertical = MaterialTheme.spacing.medium,
                horizontal = MaterialTheme.spacing.medium
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundCheckbox(
            checked = completed,
            onCheckedChange = onCheckClick,
            circleColor = contentColor
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = AnnotatedString.Builder().apply {
                withStyle(
                    style = SpanStyle(
                        textDecoration = taskNameDecoration,
                        color = contentColor
                    )
                ) {
                    append(task.name)
                }
            }.toAnnotatedString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (task.importance) {
            Icon(
                painter = painterResource(id = R.drawable.exclamation_mark),
                tint = Color.Red,
                contentDescription = "This task is important",
                modifier = Modifier.size(24.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete task",
            tint = contentColor,
            modifier = Modifier
                .clickable { onDeleteClick() }
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    CNoteTheme {
        TaskItem(task = Task(
            name = "Sample Task to show a large name what should i do to have you",
            description = "Something",
            completed = false,
            importance = true
        ), onDeleteClick = {}, onCheckClick = {})
    }
}
package com.example.cnote.feature_task.presentation.add_edit_task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.IncompleteCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cnote.R
import com.example.cnote.core.presentation.components.TransparentHintTextField
import com.example.cnote.feature_task.presentation.add_edit_task.AddEditTaskEvent
import com.example.cnote.ui.theme.spacing

@Composable
fun AddEditTaskDialog(
    modifier: Modifier = Modifier,
    title: String,
    onEvent: (AddEditTaskEvent) -> Unit,
    name: String,
    desc: String,
    isImportant: Boolean,
    isCompleted: Boolean,
    onDismiss: () -> Unit,
) {
    val textFieldStyle =
        MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.small
                )
            ) {
                Button(
                    onClick = {
                        onEvent(AddEditTaskEvent.SaveTask)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = stringResource(id = R.string.save_task))
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                TransparentHintTextField(
                    text = name,
                    hint = stringResource(id = R.string.task_name),
                    onValueChange = {
                        onEvent(AddEditTaskEvent.EnteredName(it))
                    },
                    singleLine = true,
                    textStyle = textFieldStyle,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isImportant,
                        onCheckedChange = {
                            onEvent(AddEditTaskEvent.ToggleImportance)
                        },
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.important),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (isImportant) {
                        Icon(
                            painter = painterResource(id = R.drawable.exclamation_mark),
                            tint = Color.Red,
                            contentDescription = "This task is important"
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isCompleted,
                        onCheckedChange = {
                            onEvent(AddEditTaskEvent.ToggleCompletion)
                        }
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.completed),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Icon(
                        imageVector = if (isCompleted) Icons.Default.DoneOutline
                        else Icons.Default.IncompleteCircle,
                        contentDescription = null,
                        tint = if (isCompleted) Color.Green else Color.Gray
                    )
                }
                TransparentHintTextField(
                    modifier = Modifier.height(MaterialTheme.spacing.xLarge),
                    text = desc,
                    hint = stringResource(id = R.string.description),
                    onValueChange = {
                        onEvent(AddEditTaskEvent.EnteredDescription(it))
                    },
                    textStyle = textFieldStyle
                )
            }
        },
        shape = MaterialTheme.shapes.medium,
        containerColor = MaterialTheme.colorScheme.surface
    )
}

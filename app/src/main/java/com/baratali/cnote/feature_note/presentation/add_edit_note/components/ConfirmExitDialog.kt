package com.baratali.cnote.feature_note.presentation.add_edit_note.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.baratali.cnote.R

@Composable
fun ConfirmExitDialog(
    onConfirmExit: () -> Unit,
    onDismissRequest: () -> Unit,
    onDiscardChanges: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.exit_check_title)) },
        text = { Text(stringResource(R.string.exit_check_message)) },
        confirmButton = {
            TextButton(onClick = {
                onDismissRequest()
                onConfirmExit()
            }) {
                Text(stringResource(R.string.exit_check_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
                onDiscardChanges()
            }) {
                Text(stringResource(R.string.exit_check_discard))
            }
        }
    )
}
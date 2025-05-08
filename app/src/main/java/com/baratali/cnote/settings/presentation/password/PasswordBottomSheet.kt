package com.baratali.cnote.settings.presentation.password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.biometric.BiometricPrompt
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.core.presentation.components.PasswordTextInput
import com.baratali.cnote.feature_note.presentation.util.NoteScreens
import com.baratali.cnote.ui.theme.CNoteTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PasswordBottomSheet(
    navController: NavController,
    viewModel: PasswordViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PasswordViewModel.UIEvent.NavigateBack -> {
                    navController.popBackStack()
                }

                is PasswordViewModel.UIEvent.NavigateToNoteDetails -> {
                    navController.navigate(NoteScreens.AddEditNote(event.noteId))
                }
            }
        }
    }

    PasswordBottomSheetContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBottomSheetContent(
    state: PasswordState,
    onEvent: (PasswordEvent) -> Unit
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        skipHiddenState = false
    )
    val focusRequester = remember { FocusRequester() }
    var textFieldLoaded by remember { mutableStateOf(false) }
    ModalBottomSheet(
        onDismissRequest = { onEvent(PasswordEvent.Dismiss) },
        sheetState = sheetState,
        dragHandle = {},
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        tonalElevation = 8.dp,
        scrimColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            val title = stringResource(
                id = if (state.showRepeatPassword) R.string.enter_new_password
                else R.string.enter_password
            )

            CustomText(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            PasswordTextInput(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onGloballyPositioned {
                        if (!textFieldLoaded) {
                            focusRequester.requestFocus()
                            textFieldLoaded = true
                        }
                    },
                password = state.password,
                hasError = state.passwordHasError,
                onPasswordChanged = { onEvent(PasswordEvent.PasswordChanged(it)) },
                labelText = stringResource(R.string.password),
                errorText = stringResource(R.string.incorrect_password)
            )
            if (state.showRepeatPassword) {
                PasswordTextInput(
                    password = state.repeatPassword,
                    onPasswordChanged = { onEvent(PasswordEvent.RepeatPasswordChanged(it)) },
                    labelText = stringResource(R.string.repeat_password),
                    hasError = state.repeatPasswordHasError,
                    errorText = stringResource(R.string.password_must_match)
                )
            }
            Spacer(Modifier.height(8.dp))
            IconButton(
                onClick = { onEvent(PasswordEvent.PasswordConfirmed) },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                enabled = state.buttonEnabled,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_clipboard_tick),
                    contentDescription = stringResource(R.string.confirm)
                )
            }
            if (state.showBiometric) {
                Spacer(modifier = Modifier.height(12.dp))
                BiometricPrompt(
                    onSuccess = { onEvent(PasswordEvent.BiometricSuccess) }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PasswordBottomSheetPreview() {
    CNoteTheme {
        PasswordBottomSheetContent(
            state = PasswordState(),
            onEvent = {}
        )
    }
}
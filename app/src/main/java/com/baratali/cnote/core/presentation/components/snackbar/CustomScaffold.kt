package com.baratali.cnote.core.presentation.components.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.baratali.cnote.core.presentation.bottom_navigation.BottomNavigation
import kotlinx.coroutines.launch

@Composable
fun CustomScaffold(
    navController: NavController,
    snackbarDuration: SnackbarDuration = SnackbarDuration.Short,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    showBottomBar: Boolean = true,
    containerColor: Color = colorScheme.background,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var snackbarType by remember { mutableStateOf(SnackbarType.NORMAL) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val snackbarContainerColor by rememberUpdatedState(
        when (snackbarType) {
            SnackbarType.ERROR -> colorScheme.errorContainer
            SnackbarType.NORMAL -> colorScheme.primary
        }
    )
    val snackbarContentColor by rememberUpdatedState(
        when (snackbarType) {
            SnackbarType.ERROR -> colorScheme.onErrorContainer
            SnackbarType.NORMAL -> colorScheme.onPrimary
        }
    )
    val snackbarAlignment by rememberUpdatedState(
        when (snackbarType) {
            SnackbarType.ERROR -> Alignment.TopCenter
            SnackbarType.NORMAL -> Alignment.BottomCenter
        }
    )
    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        snackbarType = event.snackbarType
        val message = event.message.asString(context)
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            if (message.isNotEmpty()) {
                val result = snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = event.action?.name?.asString(context),
                    duration = snackbarDuration
                )
                if (result == SnackbarResult.ActionPerformed) {
                    event.action?.action?.invoke()
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) BottomNavigation(navController)
        },
        containerColor = containerColor,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Popup(
                        alignment = snackbarAlignment,
                        onDismissRequest = {
                            data.dismiss()
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = snackbarAlignment
                        ) {
                            Snackbar(
                                snackbarData = data,
                                containerColor = snackbarContainerColor,
                                contentColor = snackbarContentColor,
                                actionColor = snackbarContentColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }
                    /*Snackbar(
                        snackbarData = data,
                        containerColor = snackbarContainerColor,
                        contentColor = snackbarContentColor,
                        actionColor = snackbarContentColor,
                        shape = RoundedCornerShape(12.dp)
                    )*/
                }
            )
        },
        topBar = topBar,
        floatingActionButton = floatingActionButton
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}
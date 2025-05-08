package com.baratali.cnote.feature_note.presentation.add_edit_note.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.baratali.cnote.feature_note.presentation.add_edit_note.AddEditNoteViewModel

@Composable
fun AutoSaveHandler(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: AddEditNoteViewModel
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            Log.d("AutoSaveHandler", "On ? $event")
//            if (event == Lifecycle.Event.ON_STOP || event == Lifecycle.Event.ON_PAUSE) {
//                viewModel.autoSaveIfNeeded()
//            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
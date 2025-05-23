package com.baratali.cnote.feature_note.presentation

import android.content.Context
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.baratali.cnote.feature_note.domain.model.InvalidNoteException
import com.baratali.cnote.feature_note.domain.model.Note
import com.baratali.cnote.feature_note.domain.use_case.NoteUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SaveNoteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val noteUseCases: NoteUseCases
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val id = inputData.getInt(KEY_ID, -1).takeIf { it != -1 }
            val title = inputData.getString(KEY_TITLE) ?: ""
            val content = inputData.getString(KEY_CONTENT) ?: ""
            val color = inputData.getInt(KEY_COLOR, Note.noteColors.first().toArgb())
            val timestamp = inputData.getLong(KEY_TIMESTAMP, System.currentTimeMillis())

            val note = Note(
                id = id,
                title = title,
                content = content,
                timestamp = timestamp,
                color = color
            )
            val noteId = noteUseCases.addNote(note)
            Result.success(
                Data.Builder()
                    .putLong(KEY_SAVED_NOTE_ID, noteId)
                    .build()
            )
        } catch (_: InvalidNoteException) {
            Result.failure()
        } catch (_: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val KEY_ID = "note_id"
        const val KEY_TITLE = "note_title"
        const val KEY_CONTENT = "note_content"
        const val KEY_COLOR = "note_color"
        const val KEY_TIMESTAMP = "note_timestamp"
        const val KEY_SAVED_NOTE_ID = "saved_note_id"
    }
}
package com.example.cnote.feature_note.domain.use_case

import com.example.cnote.core.domain.util.Order
import com.example.cnote.core.domain.util.OrderType
import com.example.cnote.feature_note.domain.model.Note
import com.example.cnote.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: Order = Order.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().catch { it.printStackTrace() }.map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is Order.Name -> notes.sortedBy { it.title.lowercase() }
                        is Order.Date -> notes.sortedBy { it.timestamp }
//                        is Order.Color -> notes.sortedBy { it.color }
                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is Order.Name -> notes.sortedByDescending { it.title.lowercase() }
                        is Order.Date -> notes.sortedByDescending { it.timestamp }
//                        is Order.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}
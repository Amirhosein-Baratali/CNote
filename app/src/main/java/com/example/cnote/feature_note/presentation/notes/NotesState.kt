package com.example.cnote.feature_note.presentation.notes

import com.example.cnote.core.domain.util.Order
import com.example.cnote.core.domain.util.OrderType
import com.example.cnote.feature_note.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: Order = Order.Date(OrderType.Descending)
)

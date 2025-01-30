package com.baratali.cnote.feature_note.presentation.notes

import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.core.domain.util.OrderType
import com.baratali.cnote.feature_note.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: Order = Order.Date(OrderType.Descending)
)

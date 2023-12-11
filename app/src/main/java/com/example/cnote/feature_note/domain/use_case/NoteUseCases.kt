package com.example.cnote.feature_note.domain.use_case

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote,
    val storeNoteOrder: StoreNoteOrder,
    val retrieveNoteOrder: RetrieveNoteOrder
)

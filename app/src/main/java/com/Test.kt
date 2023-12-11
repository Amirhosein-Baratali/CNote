package com

import com.example.cnote.core.domain.util.OrderType
import com.example.cnote.feature_note.domain.util.NoteOrder
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
fun main() {
    // Create an object of NoteOrder
    val noteOrder = NoteOrder.Title(OrderType.Ascending)

    // Serialize the object to JSON
    val jsonString = Json.encodeToString(noteOrder)
    println("Serialized: $jsonString")

    // Deserialize the JSON back to NoteOrder
    val deserializedNoteOrder = Json.decodeFromString<NoteOrder>(jsonString)
    println("Deserialized: $deserializedNoteOrder")

}

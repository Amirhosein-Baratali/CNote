package com.example.cnote.feature_note.domain.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class NoteOrderDeserializer : JsonDeserializer<NoteOrder> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NoteOrder? {
        val jsonObject = json?.asJsonObject
        val orderType = jsonObject?.get("orderType")?.asString
        return when (orderType) {
            "Title" -> context?.deserialize(jsonObject, NoteOrder.Title::class.java)
            "Date" -> context?.deserialize(jsonObject, NoteOrder.Date::class.java)
            "Color" -> context?.deserialize(jsonObject, NoteOrder.Color::class.java)
            else -> null
        }
    }
}
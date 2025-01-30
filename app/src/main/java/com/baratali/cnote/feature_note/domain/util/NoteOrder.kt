package com.baratali.cnote.feature_note.domain.util

import com.baratali.cnote.core.domain.util.OrderType

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)

    fun copy(orderType: OrderType): NoteOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }

    fun getName(orderType: OrderType): String {
        when (orderType) {
            is OrderType.Ascending -> {
                return when (this) {
                    is Color -> "Color_Asc"
                    is Date -> "Date_Asc"
                    is Title -> "Title_Asc"
                }
            }

            is OrderType.Descending -> {
                return when (this) {
                    is Color -> "Color_Dsc"
                    is Date -> "Date_Dsc"
                    is Title -> "Title_Dsc"
                }
            }
        }
    }

    companion object {
        fun fromName(name: String?): NoteOrder {
            return when (name) {
                "Color_Asc" -> {
                    Color(OrderType.Ascending)
                }

                "Date_Asc" -> {
                    Date(OrderType.Ascending)
                }

                "Title_Asc" -> {
                    Title(OrderType.Ascending)

                }

                "Color_Dsc" -> {
                    Color(OrderType.Descending)

                }

                "Date_Dsc" -> {
                    Date(OrderType.Descending)

                }

                "Title_Dsc" -> {
                    Title(OrderType.Descending)

                }

                else -> Date(OrderType.Ascending)
            }
        }
    }
}

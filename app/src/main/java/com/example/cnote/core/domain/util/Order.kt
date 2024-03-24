package com.example.cnote.core.domain.util

sealed class Order(val orderType: OrderType) {
    class Name(orderType: OrderType) : Order(orderType)
    class Date(orderType: OrderType) : Order(orderType)

    fun toStringValue(): String {
        return "${this.javaClass.simpleName}_${orderType.javaClass.simpleName}"
    }

    companion object {
        fun fromString(value: String?): Order =
            value?.run {
                val parts = split("_")
                val orderType = when (parts[1]) {
                    "Ascending" -> OrderType.Ascending
                    else -> OrderType.Descending
                }
                when (parts[0]) {
                    "Name" -> Name(orderType)
                    else -> Date(orderType)
                }
            } ?: Date(OrderType.default)
    }
}

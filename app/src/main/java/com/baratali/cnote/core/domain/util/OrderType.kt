package com.baratali.cnote.core.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()

    fun changeOrderType(): OrderType {
        return when (this) {
            Ascending -> Descending
            Descending -> Ascending
        }
    }
}

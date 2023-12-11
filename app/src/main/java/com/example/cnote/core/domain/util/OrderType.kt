package com.example.cnote.core.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}

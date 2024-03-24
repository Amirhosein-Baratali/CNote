package com.example.cnote.feature_task.domain.util

import com.example.cnote.core.domain.util.OrderType

sealed class TaskOrder(val orderType: OrderType) {
    class Name(orderType: OrderType) : TaskOrder(orderType)
    class Date(orderType: OrderType) : TaskOrder(orderType)

    fun copy(orderType: OrderType): TaskOrder {
        return when (this) {
            is Name -> Name(orderType)
            is Date -> Date(orderType)
        }
    }
}

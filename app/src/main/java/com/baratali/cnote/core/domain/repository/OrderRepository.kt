package com.baratali.cnote.core.domain.repository

import com.baratali.cnote.core.domain.util.Order

interface OrderRepository {
    suspend fun saveOrder(order: Order)

    suspend fun getSavedOrder(): Order
}
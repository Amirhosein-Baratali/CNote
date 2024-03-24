package com.example.cnote.core.domain.repository

import com.example.cnote.core.domain.util.Order

interface OrderRepository {
    suspend fun saveOrder(order: Order)

    suspend fun getSavedOrder(): Order
}
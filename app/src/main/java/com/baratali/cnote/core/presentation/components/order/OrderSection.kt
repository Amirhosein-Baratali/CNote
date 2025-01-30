package com.baratali.cnote.core.presentation.components.order

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.baratali.cnote.R
import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.core.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    order: Order = Order.Date(OrderType.Descending),
    onOrderChange: (Order) -> Unit,
    expanded: Boolean,
    showPriorityOption: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = { onExpandChange(false) }
    ) {
        val isDateSelected = order is Order.Date
        val isTitleSelected = order is Order.Name
        val isPrioritySelected = order is Order.Priority // New

        val orderByDate = {
            val newOrder = if (isDateSelected) {
                Order.Date(order.orderType.changeOrderType())
            } else Order.defaultDateOrder()
            onOrderChange(newOrder)
            onExpandChange(false)
        }

        val orderByTitle = {
            val newOrder = if (isTitleSelected) {
                Order.Name(order.orderType.changeOrderType())
            } else Order.defaultNameOrder()
            onOrderChange(newOrder)
            onExpandChange(false)
        }

        val orderByPriority = {
            val newOrder = if (isPrioritySelected) {
                Order.Priority(order.orderType.changeOrderType())
            } else Order.defaultPriorityOrder()
            onOrderChange(newOrder)
            onExpandChange(false)
        }

        val dateText = stringResource(R.string.sort_by_date)
        val titleText = stringResource(R.string.sort_by_title)
        val priorityText = stringResource(R.string.sort_by_priority)

        DropdownMenuItem(
            onClick = orderByDate,
            text = {
                OrderMenuItem(
                    icon = Icons.Outlined.DateRange,
                    text = dateText,
                    isSelected = isDateSelected,
                    orderType = order.orderType
                )
            }
        )

        DropdownMenuItem(
            onClick = orderByTitle,
            text = {
                OrderMenuItem(
                    icon = Icons.Outlined.Title,
                    text = titleText,
                    isSelected = isTitleSelected,
                    orderType = order.orderType
                )
            }
        )

        if (showPriorityOption)
            DropdownMenuItem(
                onClick = orderByPriority,
                text = {
                    OrderMenuItem(
                        icon = Icons.Outlined.Flag,
                        text = priorityText,
                        isSelected = isPrioritySelected,
                        orderType = order.orderType
                    )
                }
            )
    }
}
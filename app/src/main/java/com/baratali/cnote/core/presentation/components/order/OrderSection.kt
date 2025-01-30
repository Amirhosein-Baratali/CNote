package com.baratali.cnote.core.presentation.components.order

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.baratali.cnote.R
import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.core.domain.util.OrderType
import com.baratali.cnote.core.util.TestTags

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    order: Order = Order.Date(OrderType.Descending),
    onOrderChange: (Order) -> Unit,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    DropdownMenu(
        modifier = modifier.testTag(TestTags.ORDER_SECTION),
        expanded = expanded,
        onDismissRequest = { onExpandChange(false) }
    ) {
        val isDateSelected = order is Order.Date
        val isTitleSelected = order is Order.Name

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

        val dateText = stringResource(R.string.sort_by_date)
        val titleText = stringResource(R.string.sort_by_title)

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
            modifier = Modifier.testTag(TestTags.ORDER_BY_NAME),
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
    }
}
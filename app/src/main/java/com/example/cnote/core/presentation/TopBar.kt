package com.example.cnote.core.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.cnote.R
import com.example.cnote.core.domain.util.Order
import com.example.cnote.core.domain.util.OrderType
import com.example.cnote.core.presentation.components.OrderSection

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    title: String,
    order: Order = Order.Date(OrderType.Descending),
    onOrderChange: (Order) -> Unit,
    extraActions: @Composable RowScope.() -> Unit = {},
) {
    var sortMenuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = title) },
        actions = {
            extraActions()
            IconButton(onClick = { sortMenuExpanded = !sortMenuExpanded }) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = stringResource(R.string.sort)
                )
            }
            OrderSection(
                order = order,
                onOrderChange = onOrderChange,
                expanded = sortMenuExpanded,
                onExpandChange = { sortMenuExpanded = it }
            )
        }
    )
}

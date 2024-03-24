package com.example.cnote.core.presentation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.cnote.R
import com.example.cnote.core.domain.util.Order
import com.example.cnote.core.domain.util.OrderType
import com.example.cnote.core.presentation.components.order.OrderSection
import com.example.cnote.ui.theme.CNoteTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    title: String,
    order: Order = Order.Date(OrderType.Descending),
    onOrderChange: (Order) -> Unit,
    showMoreIcon: Boolean = false,
    dropMenuItems: @Composable ColumnScope.() -> Unit = {},
    moreExpanded: Boolean = false,
    onMoreExpandedChange: (Boolean) -> Unit = {},
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
            if (showMoreIcon)
                IconButton(onClick = { onMoreExpandedChange(!moreExpanded)}) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.more)
                    )
                }
            DropdownMenu(
                expanded = moreExpanded,
                onDismissRequest = { onMoreExpandedChange(false) },
                content = dropMenuItems
            )
        }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    CNoteTheme {
        TopBar(title = "title", onOrderChange = {})
    }
}

package com.baratali.cnote.core.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.baratali.cnote.R
import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.core.domain.util.OrderType
import com.baratali.cnote.core.presentation.components.TransparentHintTextField
import com.baratali.cnote.core.presentation.components.order.OrderSection
import com.baratali.cnote.settings.presentation.menu_item.SettingsMenuItem
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    title: String,
    order: Order = Order.Date(OrderType.Descending),
    onOrderChange: (Order) -> Unit,
    showMoreIcon: Boolean = true,
    dropMenuItems: @Composable ColumnScope.() -> Unit = {},
    moreExpanded: Boolean = false,
    onMoreExpandedChange: (Boolean) -> Unit = {},
    extraActions: @Composable RowScope.() -> Unit = {},
    onSearchQueryChange: (String) -> Unit = {},
    showPriorityOption: Boolean = false,
    settingsClicked: () -> Unit = {}
) {
    var sortMenuExpanded by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    TopAppBar(
        title = {
            if (isSearching) {
                TransparentHintTextField(
                    text = searchQuery,
                    hint = stringResource(R.string.search),
                    onValueChange = {
                        searchQuery = it
                        onSearchQueryChange(it)
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )
                LaunchedEffect(isSearching) {
                    if (isSearching) focusRequester.requestFocus()
                }
            } else {
                Text(text = title)
            }
        },
        navigationIcon = {
            if (isSearching) {
                IconButton(onClick = {
                    isSearching = false
                    searchQuery = ""
                    onSearchQueryChange("")
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_square_left),
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            extraActions()

            if (!isSearching) {
                IconButton(onClick = {
                    isSearching = true
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }

            IconButton(onClick = { sortMenuExpanded = !sortMenuExpanded }) {
                Icon(
                    painter = painterResource(R.drawable.ic_sort),
                    contentDescription = stringResource(R.string.sort)
                )
            }

            OrderSection(
                order = order,
                onOrderChange = onOrderChange,
                expanded = sortMenuExpanded,
                showPriorityOption = showPriorityOption,
                onExpandChange = { sortMenuExpanded = it }
            )

            if (showMoreIcon)
                IconButton(onClick = { onMoreExpandedChange(!moreExpanded) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_more_square),
                        contentDescription = stringResource(R.string.more)
                    )
                }

            DropdownMenu(
                expanded = moreExpanded,
                onDismissRequest = { onMoreExpandedChange(false) },
                content = {
                    Column {
                        SettingsMenuItem(
                            onClick = settingsClicked
                        )
                        dropMenuItems()
                    }
                }
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

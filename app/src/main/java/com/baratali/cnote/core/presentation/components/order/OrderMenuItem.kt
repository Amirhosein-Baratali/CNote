package com.baratali.cnote.core.presentation.components.order

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.baratali.cnote.core.domain.util.OrderType

@Composable
fun OrderMenuItem(
    icon: ImageVector,
    text: String,
    isSelected: Boolean,
    orderType: OrderType
) {
    val selectedColor = if (isSystemInDarkTheme()) Color(0xFF0077B6) else Color.Blue
    val color = if (isSelected) selectedColor else LocalContentColor.current
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = orderType.toString(),
            modifier = Modifier.size(24.dp),
            tint = color
        )
        Text(
            text = text,
            color = color,
            modifier = Modifier.padding(start = 8.dp)
        )
        if (isSelected) {
            Icon(
                imageVector = if (orderType is OrderType.Ascending) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                contentDescription = orderType.toString(),
                tint = color,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

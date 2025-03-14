package com.baratali.cnote.core.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R

@Composable
fun RoundedCornerCheckbox(
    isChecked: Boolean,
    label: String? = null,
    modifier: Modifier = Modifier,
    size: Float = 24f,
    checkedColor: Color = colorScheme.onBackground,
    uncheckedColor: Color = colorScheme.primary,
    onValueChange: (Boolean) -> Unit
) {
    val checkboxColor: Color by animateColorAsState(
        targetValue = if (isChecked) checkedColor else uncheckedColor,
        label = ""
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .heightIn(48.dp)
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onValueChange,
                interactionSource = null,
                indication = null
            )
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(color = checkboxColor, shape = CircleShape)
                .border(width = 1.5.dp, color = checkedColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isChecked) {
                Icon(
                    painter = painterResource(R.drawable.ic_check_rounded),
                    contentDescription = null,
                    tint = uncheckedColor
                )
            }
        }
        label?.let {
            CustomText(
                text = it,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
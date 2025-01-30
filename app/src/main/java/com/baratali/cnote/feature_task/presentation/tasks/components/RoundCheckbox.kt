package com.baratali.cnote.feature_task.presentation.tasks.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    circleColor: Color = Color.Gray
) {
    var size by remember { mutableStateOf(24.dp) }
    val compatibleGreen = if (isSystemInDarkTheme()) Color.Green else Color(0xFF006400)
    LaunchedEffect(checked) {
        size = if (checked) 28.dp else 24.dp
    }

    val radius = size / 2

    Canvas(
        modifier = modifier
            .size(size)
            .clickable(onClick = { onCheckedChange(!checked) })
    ) {
        drawCircle(
            color = circleColor,
            center = Offset(radius.toPx(), radius.toPx()),
            radius = radius.toPx(),
            style = Stroke(width = 4.dp.toPx())
        )

        if (checked) {
            drawGreenCheck(
                centerX = radius.toPx(),
                centerY = radius.toPx(),
                radius = radius.toPx() - 4,
                checkColor = compatibleGreen
            )
        }
    }
}

private fun DrawScope.drawGreenCheck(
    centerX: Float,
    centerY: Float,
    radius: Float,
    checkColor: Color = Color.Green
) {
    val path = Path()
    path.moveTo(centerX - radius / 2, centerY)
    path.lineTo(centerX - radius / 8, centerY + radius / 2)
    path.lineTo(centerX + radius / 2, centerY - radius / 2)

    drawPath(
        path = path,
        color = checkColor,
        style = Stroke(width = radius / 5)
    )
}

@Composable
@Preview(showBackground = true)
fun RoundCheckboxExample() {
    var checked by remember { mutableStateOf(true) }

    RoundCheckbox(
        checked = checked,
        onCheckedChange = { newChecked ->
            checked = newChecked
        },
        modifier = Modifier.padding(16.dp)
    )
}

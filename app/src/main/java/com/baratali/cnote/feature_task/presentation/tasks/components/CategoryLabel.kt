package com.baratali.cnote.feature_task.presentation.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.core.presentation.components.LightAndDarkPreview
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun CategoryLabel(
    modifier: Modifier = Modifier,
    category: TaskCategory
) {
    val containerColor = Color(category.color)
    val contentColor = Color.Black
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(containerColor, shape = RoundedCornerShape(6.dp))
            .padding(8.dp)
    ) {
        category.icon.iconRes?.let {
            Icon(
                painter = painterResource(it),
                contentDescription = category.name,
                tint = contentColor
            )
        } ?: category.icon.imageVector?.let {
            Icon(
                imageVector = it,
                contentDescription = category.name,
                tint = contentColor
            )
        }
        Spacer(Modifier.width(4.dp))
        CustomText(
            text = category.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = contentColor
        )
    }

}

@LightAndDarkPreview
@Composable
private fun CategoryLabelPreview() {
    CNoteTheme {
        CategoryLabel(
            category = TaskCategory.sampleData
        )
    }
}
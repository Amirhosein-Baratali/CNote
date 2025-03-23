package com.baratali.cnote.feature_note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.ObfuscatedText
import com.baratali.cnote.core.util.TestTags
import com.baratali.cnote.feature_note.domain.model.Note
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onLockClick: () -> Unit
) {
    val contentColor = Color.Black
    ElevatedCard(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorScheme.background
        )
    ) {
        Box(
            modifier = Modifier.testTag(TestTags.NOTE_ITEM),
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val clipPath = Path().apply {
                    lineTo(size.width - cutCornerSize.toPx(), 0f)
                    lineTo(size.width, cutCornerSize.toPx())
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                clipPath(clipPath) {
                    drawRoundRect(
                        color = Color(note.color),
                        size = size,
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                    drawRoundRect(
                        color = Color(
                            ColorUtils.blendARGB(note.color, 0x000000, 0.2f)
                        ),
                        topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                        size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(end = 32.dp)
            ) {
                ObfuscatedText(
                    text = note.title,
                    style = typography.titleSmall,
                    obfuscate = note.locked
                )
                Spacer(Modifier.height(8.dp))
                ObfuscatedText(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = note.content,
                    obfuscate = note.locked
                )
                Spacer(Modifier.height(16.dp))
            }
            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onLockClick,
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (note.locked) R.drawable.ic_lock else R.drawable.ic_opened_lock
                        ),
                        contentDescription = "Delete note",
                        tint = contentColor
                    )
                }
                IconButton(
                    onClick = onDeleteClick,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_trash),
                        contentDescription = "Delete note",
                        tint = contentColor
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun NoteItemPreview() {
    CNoteTheme {
        NoteItem(
            note = Note(
                id = 1,
                title = "Title",
                content = "Content",
                timestamp = System.currentTimeMillis(),
                color = 1,
            ),
            onClick = {},
            onDeleteClick = {},
            onLockClick = {}
        )
    }
}
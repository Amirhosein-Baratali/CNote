import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun SetOrChangePasswordItem(
    modifier: Modifier = Modifier,
    passwordSet: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorScheme.outlineVariant)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_locked_password),
            contentDescription = null,
            tint = colorScheme.onBackground,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.width(12.dp))

        CustomText(
            text = if (passwordSet) stringResource(R.string.change_password) else stringResource(R.string.set_password),
            color = colorScheme.onBackground
        )
    }
}

@PreviewLightDark
@Composable
private fun SetOrChangePasswordItemPreview() {
    CNoteTheme {
        SetOrChangePasswordItem(
            passwordSet = true,
            onClick = {}
        )
    }
}
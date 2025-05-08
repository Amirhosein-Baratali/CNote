package com.baratali.cnote.core.presentation.biometric

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun BiometricPrompt(modifier: Modifier = Modifier, onSuccess: () -> Unit) {
    val biometricAvailable = getAvailableBiometricType(LocalContext.current)
    if (biometricAvailable != BiometricType.NONE) {
        val biometricText = when (biometricAvailable) {
            BiometricType.FINGERPRINT -> stringResource(R.string.or_use_your_fingerprint)
            BiometricType.FACE -> stringResource(R.string.or_use_your_face)
            BiometricType.BOTH -> stringResource(R.string.or_use_your_fingerprint_face)
            else -> ""
        }
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(
                modifier = Modifier.fillMaxWidth(),
                text = biometricText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            BiometricIcon(
                modifier = Modifier.fillMaxWidth(),
                showBiometricPromptInitialValue = true,
                onSuccess = onSuccess
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun BiometricPromptPreview() {
    CNoteTheme {
        BiometricPrompt {}
    }
}
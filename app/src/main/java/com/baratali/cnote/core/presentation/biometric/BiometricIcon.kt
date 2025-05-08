package com.baratali.cnote.core.presentation.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.LightAndDarkPreview
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun BiometricIcon(
    modifier: Modifier = Modifier,
    showBiometricPromptInitialValue: Boolean = false,
    iconSize: Dp = 64.dp,
    onSuccess: () -> Unit
) {
    var showBiometricPrompt by remember { mutableStateOf(showBiometricPromptInitialValue) }

    IconButton(
        modifier = modifier.size(iconSize),
        onClick = { showBiometricPrompt = true }
    ) {
        if (getAvailableBiometricType(LocalContext.current) == BiometricType.FACE) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_face_id),
                contentDescription = "Face ID",
                tint = Color.Gray,
            )
        } else {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_fingerprint),
                contentDescription = "Fingerprint",
                tint = Color.Gray,
            )
        }
    }

    if (showBiometricPrompt) {
        BiometricDialog(
            onFailure = { error ->
                showBiometricPrompt = false
            },
            onCancel = {
                showBiometricPrompt = false
            },
            onSuccess = {
                showBiometricPrompt = false
                onSuccess()
            }
        )
    }
}

fun getAvailableBiometricType(context: Context): BiometricType {
    val biometricManager = BiometricManager.from(context)
    val canAuthenticate = biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
    )

    return when (canAuthenticate) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            val hasFingerprint =
                biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
            val hasFace =
                biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS

            when {
                hasFingerprint && hasFace -> BiometricType.BOTH
                hasFingerprint -> BiometricType.FINGERPRINT
                hasFace -> BiometricType.FACE
                else -> BiometricType.NONE
            }
        }

        else -> BiometricType.NONE
    }
}

enum class BiometricType {
    NONE,
    FINGERPRINT,
    FACE,
    BOTH
}

@LightAndDarkPreview
@Composable
private fun FingerPrintIconPreview() {
    CNoteTheme {
        BiometricIcon {}
    }
}
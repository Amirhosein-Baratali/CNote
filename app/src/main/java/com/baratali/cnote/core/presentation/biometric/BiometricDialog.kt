package com.baratali.cnote.core.presentation.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.baratali.cnote.core.util.unwrapContextToActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BiometricDialog(
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity =
        context.unwrapContextToActivity() ?: return onFailure("Unable to get FragmentActivity")

    val scope = rememberCoroutineScope()

    val biometricManager = BiometricManager.from(context)
    val canAuthenticate = biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
    )

    if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.Main) {
                showBiometricPrompt(
                    activity,
                    onSuccess = onSuccess,
                    onFailure = onFailure,
                    onCancel = onCancel
                )
            }
        }
    } else {
        onFailure("Biometric authentication not supported")
    }
}

fun showBiometricPrompt(
    context: Context,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit,
    onCancel: () -> Unit
) {
    val executor = ContextCompat.getMainExecutor(context)
    val biometricPrompt = BiometricPrompt(
        context as? FragmentActivity ?: return onFailure("Unable to get FragmentActivity"),
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onFailure("Authentication failed")
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON || errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                    onCancel()
                } else {
                    onFailure(errString.toString())
                }
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Authenticate")
        .setNegativeButtonText("Use Password")
        .setConfirmationRequired(false)
        .build()

    biometricPrompt.authenticate(promptInfo)
}



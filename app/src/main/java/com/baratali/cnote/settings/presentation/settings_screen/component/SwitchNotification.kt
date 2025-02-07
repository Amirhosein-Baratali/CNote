package com.baratali.cnote.settings.presentation.settings_screen.component

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.baratali.cnote.R

@Composable
fun SwitchNotification(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onCheckedChange(true)
        } else {
            onCheckedChange(false)
        }
    }

    SwitchSetting(
        modifier = modifier,
        title = stringResource(R.string.notifications),
        isChecked = isChecked,
        onCheckedChange = { newValue ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val notificationPermission = Manifest.permission.POST_NOTIFICATIONS
                if (newValue) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            notificationPermission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        onCheckedChange(true)
                    } else {
                        notificationPermissionLauncher.launch(notificationPermission)
                    }
                } else {
                    onCheckedChange(false)
                }
            } else {
                onCheckedChange(newValue)
            }
        }
    )
}
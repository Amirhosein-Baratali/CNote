package com.baratali.cnote.settings.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.baratali.cnote.settings.presentation.password.PasswordBottomSheet
import com.baratali.cnote.settings.presentation.settings_screen.SettingsScreen

fun NavGraphBuilder.settingsNavigation(navController: NavController) {
    navigation<SettingScreens.Settings>(startDestination = SettingScreens.SettingList) {
        composable<SettingScreens.SettingList> {
            SettingsScreen(navController)
        }
        dialog<SettingScreens.Password> {
            PasswordBottomSheet(navController)
        }
    }
}
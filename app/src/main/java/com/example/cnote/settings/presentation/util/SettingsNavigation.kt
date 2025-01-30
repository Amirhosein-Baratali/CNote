package com.example.cnote.settings.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.cnote.settings.presentation.settings_screen.SettingsScreen

fun NavGraphBuilder.settingsNavigation(navController: NavController) {
    navigation<SettingScreens.Settings>(startDestination = SettingScreens.SettingList) {
        composable<SettingScreens.SettingList> {
            SettingsScreen(navController)
        }
    }
}
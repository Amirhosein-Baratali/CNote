package com.example.cnote.feature_note.presentation.notes

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.example.cnote.R
import com.example.cnote.core.presentation.MainActivity
import com.example.cnote.core.util.TestTags
import com.example.cnote.feature_note.di.NotesModule
import com.example.cnote.feature_note.presentation.util.NoteScreens
import com.example.cnote.ui.theme.CNoteTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NotesModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            CNoteTheme {
                NavHost(
                    navController = navController,
                    startDestination = NoteScreens.Notes
                ) {
                    composable<NoteScreens.Notes> {
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun toggleSortMenu_OrderSectionExpanded() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val orderDropDown = composeRule.onNodeWithTag(TestTags.ORDER_SECTION)

        orderDropDown.assertDoesNotExist()
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort)).performClick()
        orderDropDown.assertIsDisplayed()
    }
}

package com.example.cnote.feature_note.presentation

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.example.cnote.R
import com.example.cnote.core.presentation.MainActivity
import com.example.cnote.core.util.TestTags
import com.example.cnote.feature_note.di.NotesModule
import com.example.cnote.feature_note.presentation.util.NoteScreens
import com.example.cnote.feature_note.presentation.util.notesNavigation
import com.example.cnote.feature_task.presentation.util.tasksNavigation
import com.example.cnote.ui.theme.CNoteTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NotesModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        composeRule.activity.setContent {
            CNoteTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NoteScreens.Notes
                    ) {
                        notesNavigation(navController)
                        tasksNavigation(navController)
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        // Step 1: Click on the "Add Note" button to add a new note
        composeRule.onNodeWithContentDescription(getString(R.string.add_note)).performClick()

        // Step 2: Define test data for the new note
        val testTitle = "test-title"
        val testContent = "test-content"
        val testTitleAddOn = "edited-"

        // Step 3: Enter the test title and content into the appropriate fields
        composeRule
            .onNodeWithTag(TestTags.NOTE_TITLE_TEXT_FIELD)
            .performTextInput(testTitle)
        composeRule
            .onNodeWithTag(TestTags.NOTE_CONTENT_TEXT_FIELD)
            .performTextInput(testContent)

        // Step 4: Click on the "Save" button to save the new note
        composeRule.onNodeWithContentDescription(getString(R.string.save_note)).performClick()

        // Step 5: Assert that the new note with the test title is displayed
        composeRule.onNodeWithText(testTitle).assertIsDisplayed()

        // Step 6: Click on the newly added note to edit it
        composeRule.onNodeWithText(testTitle).performClick()

        // Step 7: Verify that the title and content fields contain the original test data
        composeRule
            .onNodeWithTag(TestTags.NOTE_TITLE_TEXT_FIELD)
            .assertTextEquals(testTitle)
        composeRule
            .onNodeWithTag(TestTags.NOTE_CONTENT_TEXT_FIELD)
            .assertTextEquals(testContent)

        // Step 8: Edit the title field with additional text
        composeRule
            .onNodeWithTag(TestTags.NOTE_TITLE_TEXT_FIELD)
            .performTextInput(testTitleAddOn)

        // Step 9: Click on the "Save" button to save the edited note
        composeRule.onNodeWithContentDescription(getString(R.string.save_note)).performClick()

        // Step 10: Assert that the edited note title is displayed correctly
        composeRule.onNodeWithText(testTitleAddOn + testTitle).assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByNameDescending() {
        // Define a custom range to save as notes
        val charRange = 'a'..'d'

        // Iterate over the character range
        charRange.forEachIndexed { index, c ->
            // Click on the "Add Note" button to add a new note
            composeRule.onNodeWithContentDescription(getString(R.string.add_note)).performClick()

            // Define test data for the new note
            val testTitle = c.toString()
            val testContent = index.toString()

            // Enter the test title and content into the appropriate fields
            composeRule
                .onNodeWithTag(TestTags.NOTE_TITLE_TEXT_FIELD)
                .performTextInput(testTitle)
            composeRule
                .onNodeWithTag(TestTags.NOTE_CONTENT_TEXT_FIELD)
                .performTextInput(testContent)

            // Click on the "Save" button to save the new note
            composeRule.onNodeWithContentDescription(getString(R.string.save_note)).performClick()

            // Assert that the new note with the test title is displayed
            composeRule.onNodeWithText(testTitle).assertIsDisplayed()
        }

        // Double-click on "Sort" to toggle sorting by name descending
        composeRule.onNodeWithContentDescription(getString(R.string.sort)).performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_BY_NAME).performClick()
        composeRule.onNodeWithContentDescription(getString(R.string.sort)).performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_BY_NAME).performClick()

        // Iterate over the character range in reverse order
        charRange.reversed().forEachIndexed { index, c ->
            // Assert that the notes are ordered by name descending
            composeRule
                .onAllNodesWithTag(TestTags.NOTE_ITEM)[index]
                .assertTextContains(c.toString())
        }
    }

    private fun getString(id: Int): String {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return context.getString(id)
    }
}
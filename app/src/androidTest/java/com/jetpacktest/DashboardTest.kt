package com.jetpacktest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jetpacktest.presentation.ui.Dashboard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDashboardUIComponents() {
        composeTestRule.setContent {
            Dashboard(navController = rememberNavController())
        }

        // Check if "Settings" icon is visible
        composeTestRule.onNodeWithTag("SettingsIcon")
            .assertExists()
            .assertIsDisplayed()

        // Check if SearchBar is visible
        composeTestRule.onNodeWithTag("SearchBar")
            .assertExists()
            .assertIsDisplayed()

        // Enter text in SearchBar
        composeTestRule.onNodeWithTag("SearchBar")
            .performTextInput("test")

        // Verify input
        composeTestRule.onNodeWithTag("SearchBar")
            .assertTextContains("test")

        // Clear search input
        composeTestRule.onNodeWithTag("ClearSearchIcon")
            .performClick()

        // Verify cleared input
        composeTestRule.onNodeWithTag("SearchBar")
            .assertTextContains("")

        // Click on Settings Icon
        composeTestRule.onNodeWithTag("SettingsIcon")
            .performClick()

        // Add navigation verification if needed
    }

}
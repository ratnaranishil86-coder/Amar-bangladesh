package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @get:Rule val composeTestRule = createAndroidComposeRule<MainActivity>()

  @Test
  fun `app name matches আমার বাংলাদেশ`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("আমার বাংলাদেশ", appName)
  }

  @Test
  fun `bottom navigation switches across all five screens`() {
    composeTestRule.onNodeWithTag("home_screen_list").assertIsDisplayed()
    composeTestRule.onAllNodesWithText("আমার বাংলাদেশ").onFirst().assertIsDisplayed()
    composeTestRule.onNodeWithTag("home_hero_card").assertIsDisplayed()

    composeTestRule.onNodeWithTag("nav_districts").performClick()
    composeTestRule.onNodeWithTag("districts_screen").assertIsDisplayed()

    composeTestRule.onNodeWithTag("nav_explore").performClick()
    composeTestRule.onNodeWithTag("explore_screen").assertIsDisplayed()

    composeTestRule.onNodeWithTag("nav_saved").performClick()
    composeTestRule.onNodeWithTag("saved_screen").assertIsDisplayed()

    composeTestRule.onNodeWithTag("nav_profile").performClick()
    composeTestRule.onNodeWithTag("profile_screen").assertIsDisplayed()

    composeTestRule.onNodeWithTag("nav_home").performClick()
    composeTestRule.onNodeWithTag("home_screen_list").assertIsDisplayed()
  }
}

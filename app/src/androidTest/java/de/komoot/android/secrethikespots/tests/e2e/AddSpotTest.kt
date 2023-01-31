package de.komoot.android.secrethikespots.tests.e2e

import androidx.compose.ui.test.*
import de.komoot.android.secrethikespots.R.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.komoot.android.secrethikespots.ui.SpotListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


private const val SPOT_NAME = "Spot"

@RunWith(AndroidJUnit4::class)
class AddSpotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SpotListActivity>()

    @Test
    fun testAddSpotAndVerifyItIsAddedToList() {
        composeTestRule.onNodeWithTag("addSpot", useUnmergedTree = true)
            .performClick()

        Espresso.onView(ViewMatchers.withId(id.map))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(id.name))
            .perform(ViewActions.click())
            .perform(ViewActions.typeText(SPOT_NAME), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withText("Create"))
            .perform(ViewActions.click())
        composeTestRule.onNodeWithTag("spotName", useUnmergedTree = true)
            .assertTextContains(SPOT_NAME)
        composeTestRule.onNodeWithTag("spotImage", useUnmergedTree = true)
            .assertIsDisplayed()
    }
}
package de.komoot.android.secrethikespots.tests.e2e

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.komoot.android.secrethikespots.R.*
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

        Espresso.onView(withId(id.map))
            .check(matches(isDisplayed()))
            .perform(GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER))
        Espresso.onView(withId(id.name))
            .check(matches(isDisplayed()))
            .perform(ViewActions.typeText(SPOT_NAME), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withText("Create"))
            .perform(ViewActions.click())
        composeTestRule.onNodeWithTag("spotName", useUnmergedTree = true)
            .assertTextContains(SPOT_NAME)
        composeTestRule.onNodeWithTag("spotImage", useUnmergedTree = true)
            .assertIsDisplayed()
    }
}
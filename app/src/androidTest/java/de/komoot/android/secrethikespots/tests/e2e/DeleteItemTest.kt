package de.komoot.android.secrethikespots.tests.e2e

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.komoot.android.secrethikespots.R
import de.komoot.android.secrethikespots.data.SecretSpotDb
import de.komoot.android.secrethikespots.data.SecretSpotEntity
import de.komoot.android.secrethikespots.ui.SpotListActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val SPOT_NAME = "My Secret Spot"

@RunWith(AndroidJUnit4::class)
class DeleteItemTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SpotListActivity>()

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val secretSpot = SecretSpotEntity(
        id = (0L..1000000L).random(),
        name = SPOT_NAME,
        lat = 37.7749,
        lon = -122.4194,
        image = "secret_spot_image.jpg"
    )

    @Before
    fun setup() {
        val secretSpotDB = SecretSpotDb.getDatabase(appContext)
        val secretSpotDao = secretSpotDB.secretSpotDao()
        secretSpotDao.insert(secretSpot)
    }

    @After
    fun tearDown() {
        val secretSpotDB = SecretSpotDb.getDatabase(appContext)
        val secretSpotDao = secretSpotDB.secretSpotDao()
        secretSpotDao.delete(secretSpot.id)
    }


    @Test
    fun testDeleteItem() {
        composeTestRule.onNodeWithTag("spotName", useUnmergedTree = true).assertTextContains(
            SPOT_NAME
        )
        composeTestRule.onNodeWithTag("spotName").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithTag("deleteButton")
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("deleteButton").performClick()
        composeTestRule.onNodeWithText(SPOT_NAME)
            .assertIsNotDisplayed()
    }
}
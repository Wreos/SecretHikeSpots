package de.komoot.android.secrethikespots.tests.e2e

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.komoot.android.secrethikespots.data.SecretSpotDb
import de.komoot.android.secrethikespots.data.SecretSpotEntity
import de.komoot.android.secrethikespots.ui.SpotListActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OpenSpotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SpotListActivity>()

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val secretSpot = SecretSpotEntity(
        id = (0L..10L).random(),
        name = "test",
        lat = 37.7749,
        lon = -122.4194,
        image = "app/src/main/res/image.jpg"
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
    fun openSpotItem() {
        // Unfortunately, i was not able to implement it, as for the some reasons I am not able to tap on card which was generated via db
    }
}
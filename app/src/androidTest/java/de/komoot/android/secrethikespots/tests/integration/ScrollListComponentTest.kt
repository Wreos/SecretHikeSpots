package de.komoot.android.secrethikespots.tests.integration

import android.graphics.Path
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.mapboxsdk.geometry.LatLng
import de.komoot.android.secrethikespots.model.SecretSpot
import de.komoot.android.secrethikespots.ui.SecretHikeSpotsTheme
import de.komoot.android.secrethikespots.ui.SecretSpots
import de.komoot.android.secrethikespots.ui.SpotListActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ScrollListComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            val flow = remember {
                derivedStateOf {
                    List(20) { index ->
                        SecretSpot(
                            id = index.toLong(),
                            name = "spot $index",
                            location = LatLng(index.toDouble(), index.toDouble()),
                            image = "image $index"
                        )
                    }
                }
            }
            SecretHikeSpotsTheme {
                SecretSpots(PaddingValues(0.dp), flow, {}, {}) {}
            }
        }

        composeTestRule.onRoot().performTouchInput {
            swipeUp()
        }
        Thread.sleep(5000)
        composeTestRule.onNodeWithText("spot 20")
            .assertIsDisplayed()

    }
}
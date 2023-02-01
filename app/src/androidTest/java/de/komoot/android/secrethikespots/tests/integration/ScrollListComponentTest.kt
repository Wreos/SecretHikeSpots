package de.komoot.android.secrethikespots.tests.integration

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.mapboxsdk.geometry.LatLng
import de.komoot.android.secrethikespots.model.SecretSpot
import de.komoot.android.secrethikespots.ui.SecretHikeSpotsTheme
import de.komoot.android.secrethikespots.ui.SecretSpots
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrollListComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSpotListIsScrollable() {
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
        composeTestRule.onNodeWithText("spot 19")
            .assertIsDisplayed()
        composeTestRule.onRoot().performTouchInput {
            swipeDown()
        }
        composeTestRule.onNodeWithText("spot 1")
            .assertIsDisplayed()

    }
}
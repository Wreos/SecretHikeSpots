package de.komoot.android.secrethikespots.tests.e2e

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.komoot.android.secrethikespots.pageobjects.SpotListPage
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

class AddSpotTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(SpotListActivity::class.java)

    @Test
    fun useAppContext() {
        val activityScenario = activityRule.scenario
        activityScenario.onActivity {
            Thread.sleep(5000)
            val spotListPage = SpotListPage()
            spotListPage.clickButtonWithTestTag(spotListPage.addSpotButton)
            Thread.sleep(5000)
        }
    }
}
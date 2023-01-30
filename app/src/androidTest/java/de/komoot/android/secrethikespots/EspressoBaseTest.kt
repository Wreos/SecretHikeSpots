package de.komoot.android.secrethikespots

import android.util.Log
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry


open class EspressoBaseTest {

    private fun stringIdToResourceId(stringId: String): Int {
        return try {
            val resources = getTargetContext().resources
            resources.getIdentifier(stringId, "id", getTargetContext().packageName)
        } catch (e: Exception) {
            Log.e("EspressoBaseTest", "Error converting string id to resource id: $e")
            0
        }
    }

    private fun testTagToResourceId(tag: String): Int {
        return try {
            val resources = getTargetContext().resources
            resources.getIdentifier(tag, "testTag", getTargetContext().packageName)
        } catch (e: Exception) {
            Log.e("EspressoBaseTest", "Error converting test tag to resource id: $e")
            0
        }
    }

    fun clickButtonWithTestTag(tag: String) {
        val resourceId = testTagToResourceId(tag)
        onView(withTagKey(resourceId))
            .perform(click())
    }

    fun clickButton(stringId: String) {
        val resourceId = stringIdToResourceId(stringId)
        onView(withId(resourceId))
            .perform(click())
    }

    fun fillInWithText(stringId: String, text: String) {
        val resourceId = stringIdToResourceId(stringId)
        onView(withId(resourceId))
            .perform(click())
            .perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard())
    }

    fun checkElementContainsText(stringId: String, text: String) {
        val resourceId = stringIdToResourceId(stringId)
        onView(withId(resourceId))
            .check(matches(withText(text)))
    }
}


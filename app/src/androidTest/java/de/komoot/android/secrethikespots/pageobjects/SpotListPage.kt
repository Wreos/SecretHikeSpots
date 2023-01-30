package de.komoot.android.secrethikespots.pageobjects

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.komoot.android.secrethikespots.EspressoBaseTest

class SpotListPage : EspressoBaseTest() {

    val addSpotButton = "addSpot"
    val spotImage = "spotImage"
    val spotName = "spotName"
    val deleteButton = "deleteButton"


    fun verifySpotIsNotDisplayed(todoText: String) {
        Espresso.onView(ViewMatchers.withText(todoText)).check(ViewAssertions.doesNotExist())
    }
}
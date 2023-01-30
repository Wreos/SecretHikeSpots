package com.example.android.architecture.blueprints.todoapp

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*


open class EspressoBaseTest {

    fun clickButton(resourceId: Int) {
        onView(withId(resourceId))
            .perform(click())
    }

    fun fillInWithText(resourceId: Int, text: String) {
        onView(withId(resourceId))
            .perform(click())
            .perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard())
    }

    fun clickOnViewWithText(text: String) {
        onView(withText(text))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    fun checkElementContainsText(resourceId: Int, text: String) {
        onView(withId(resourceId))
            .check(matches(withText(text)))
    }

    fun checkElementWithTextExist(text: String) {
        onView(withText(text))
            .check(matches(withText(text)))
    }
}


package com.example.sumtest

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
//import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.Matchers.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


//import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4ClassRunner::class)
class SumActivityTest {

    //This variable will be global for all the functions created for testing
    @get:Rule val activityScenarioRule = activityScenarioRule<SumActivity>()


    @Test
    fun twoPositiveIntegerSum() {
        onView(ViewMatchers.withId(R.id.firstNumber)).perform(ViewActions.typeText("5"))
        onView(ViewMatchers.withId(R.id.secondNumber)).perform(ViewActions.typeText("7"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.cta)).perform(ViewActions.click())
        Thread.sleep(4_000)
        onView(ViewMatchers.withId(R.id.result)).check(matches(withText("12")))
    }

    @Test
    fun twoNegativeIntegerSum() {
        onView(ViewMatchers.withId(R.id.firstNumber)).perform(ViewActions.typeText("-5"))
        onView(ViewMatchers.withId(R.id.secondNumber)).perform(ViewActions.typeText("-7"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.cta)).perform(ViewActions.click())
        Thread.sleep(4_000)
        onView(ViewMatchers.withId(R.id.result)).check(matches(withText("-12")))
    }

    @Test
    fun oneIntegerMissingIntegerSum() {
        onView(ViewMatchers.withId(R.id.firstNumber)).perform(ViewActions.typeText("-5"))
        onView(ViewMatchers.withId(R.id.secondNumber)).perform(ViewActions.typeText(""))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.cta)).perform(ViewActions.click())
        Thread.sleep(4_000)
        onView(ViewMatchers.withId(R.id.error)).check(matches(allOf(withText("One or more fields are empty"))))
    }

    @Test
    fun twoDecimalSum() {
        onView(ViewMatchers.withId(R.id.firstNumber)).perform(ViewActions.typeText("-5.2"))
        onView(ViewMatchers.withId(R.id.secondNumber)).perform(ViewActions.typeText("2.4"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.cta)).perform(ViewActions.click())
        Thread.sleep(4_000)
        onView(ViewMatchers.withId(R.id.error)).check(matches(withText("Only integers are allowed")))
    }

    @Test
    fun indicatorVisibilty() {
        onView(ViewMatchers.withId(R.id.firstNumber)).perform(ViewActions.typeText("5"))
        onView(ViewMatchers.withId(R.id.secondNumber)).perform(ViewActions.typeText("7"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.cta)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.loading)).check(matches(isDisplayed()))
    }
}
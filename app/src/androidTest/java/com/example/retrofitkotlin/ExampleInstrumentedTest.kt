package com.example.retrofitkotlin

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.retrofitkotlin.ApiGetTestRecycle.MainActivity3

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
class ExampleInstrumentedTest {


    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity3>
            = ActivityScenarioRule(MainActivity3::class.java)


    @Test
    public fun LoadAllCountries() {

        //Espresso.onView(withId(R.id.aInput)).perform(ViewActions.typeText("2"));
        //Espresso.onView(withId(R.id.bInput)).perform(ViewActions.typeText("4"));
        //Espresso.onView(withId(R.id.cInput))
            //.perform(ViewActions.typeText("2"), ViewActions.closeSoftKeyboard());

        Espresso.onView(withId(R.id.button)).perform(ViewActions.click());

        Thread.sleep(1000)

        Espresso.onView(withId(R.id.countriesCount))
            .check(ViewAssertions.matches(ViewMatchers.withText("250")))

    }

    @Test
    public fun LoadAlbaniaCountry() {

        Espresso.onView(withId(R.id.button)).perform(ViewActions.click());

        Thread.sleep(1000)

        Espresso.onView(withId(R.id.list)).perform(ViewActions.click());


        Espresso.onView(withId(R.id.countryTV))
            .check(ViewAssertions.matches(ViewMatchers.withText("Albania")))

        Espresso.onView(withId(R.id.regionTV))
            .check(ViewAssertions.matches(ViewMatchers.withText("Europe")))

        Espresso.onView(withId(R.id.countsTV))
            .check(ViewAssertions.matches(ViewMatchers.withText("2886026")))

    }
}
package com.example.diceroller

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityIntentTest {

    @get:Rule
    var activityTestRule: IntentsTestRule<MainActivity>
            = IntentsTestRule(MainActivity::class.java)


    @Test fun clickOnSettingsCreatesCorrectIntentInfo() {

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText(activityTestRule.activity.getString(R.string.action_settings))).perform(click())

        intended(hasComponent(SettingsActivity::class.java.getName()))

    }
}
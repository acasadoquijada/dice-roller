package com.example.diceroller

import android.widget.ImageView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertNotEquals


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)


    fun openSettingsActivity(){
        // Open the settings activity
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText(activityRule.activity.getString(R.string.action_settings))).perform(click())

        // Click on "Dice number"

        onView(withText(activityRule.activity.getString(R.string.dice_number_title))).perform(click())
    }

    @Test fun settingOneDiceShowsOneDice() {

        openSettingsActivity()

        // Click on "1 dice"
        onView(withText(R.string.pref_one_dice_label)).perform(click())

        // Go to mainActivity
        pressBack()

        // Dice visible: dice 1

        onView(withId(R.id.dice_image)).check(matches(isDisplayed())) // dice 1

        onView(withId(R.id.dice_image2)).check(matches(not(isDisplayed()))) // dice 2

        onView(withId(R.id.dice_image3)).check(matches(not(isDisplayed()))) // dice 3

        onView(withId(R.id.dice_image4)).check(matches(not(isDisplayed()))) // dice 4
    }

    @Test fun settingTwoDiceShowsTwoDice() {

        openSettingsActivity()

        // Click on "1 dice"
        onView(withText(R.string.pref_two_dice_label)).perform(click())

        // Go to mainActivity
        pressBack()

        // Dice visible: dice 1, dice 2

        onView(withId(R.id.dice_image)).check(matches(isDisplayed())) // dice 1

        onView(withId(R.id.dice_image2)).check(matches(isDisplayed())) // dice 2

        onView(withId(R.id.dice_image3)).check(matches(not(isDisplayed()))) // dice 3

        onView(withId(R.id.dice_image4)).check(matches(not(isDisplayed()))) // dice 4
    }

    @Test fun settingThreeDiceShowsThreeDice() {

        openSettingsActivity()

        // Click on "1 dice"
        onView(withText(R.string.pref_three_dice_label)).perform(click())

        // Go to mainActivity
        pressBack()

        // Dice visible: dice 1, dice 2, dice 3

        onView(withId(R.id.dice_image)).check(matches(isDisplayed())) // dice 1

        onView(withId(R.id.dice_image2)).check(matches(isDisplayed())) // dice 2

        onView(withId(R.id.dice_image3)).check(matches(isDisplayed())) // dice 3

        onView(withId(R.id.dice_image4)).check(matches(not(isDisplayed()))) // dice 4
    }

    @Test fun settingFourDiceShowsFourDice() {

        openSettingsActivity()

        // Click on "1 dice"
        onView(withText(R.string.pref_four_dice_label)).perform(click())

        // Go to mainActivity
        pressBack()

        // Dice visible: dice 1, dice 2, dice 3, dice 4

        onView(withId(R.id.dice_image)).check(matches(isDisplayed())) // dice 1

        onView(withId(R.id.dice_image2)).check(matches(isDisplayed())) // dice 2

        onView(withId(R.id.dice_image3)).check(matches(isDisplayed())) // dice 3

        onView(withId(R.id.dice_image4)).check(matches(isDisplayed())) // dice 4
    }
}
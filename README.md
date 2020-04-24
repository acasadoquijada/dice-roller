# Dice Roller

Android application for a nice session of board games with friends!

## Application description

This application has been developed using Kotlin and is composed by two activities:

### MainActivity

Contains the logic of the application.

For each dice a *onClickListener* is set in order to be able to roll each one individually. The listener checks if the dice is shown in the screen in and if so, the roll is performed. Besides this, there is a *roll button* to roll them all at the same time. 

In order to simulate a roll, a random number between 1 and 6 is generated, then the dice image is updated according to this random number. There are 6 different dice images, one per each possibly value.

To keep the same roll result when this activity is recreated (for example, after a screen rotation), the tag attribute of the ImageView objects corresponding to each dice has been used as follows:

In ***onCreate*** if *savedInstance* is null the tag of all the dice is set to the drawable resource representing a roll with value 1. On the other hand, if savedInstance is not null, the tag value is recovered and use it to update the dice ImageVew and it tag.

In ***rollDice*** the tag is updated according to the roll result

In ***onSaveInstanceState*** the current value of the tags is stored

### SettingsActivity 

This activity contains a PreferenceFragmentCompat with a ListPreference to modify the dice number shown in MainActivity. The number of dice available is 4, being possible to select the number of dices in screen (from 1 to 4). 

## Instrumented testing

There are two classes for the instrumented testing:

* [MainActivityIntentTest](https://github.com/acasadoquijada/dice-roller/blob/master/app/src/androidTest/java/com/example/diceroller/MainActivityIntentTest.kt) tests that the intent generated when the settings button is clicked is correct

* [MainActivityTest](https://github.com/acasadoquijada/dice-roller/blob/master/app/src/androidTest/java/com/example/diceroller/MainActivityTest.kt) tests that the number of dice in screen is the one set in the ListPreference of the SettingsActivity

## Application images

![mainActivity](doc/images/mainActivity.jpg  "mainActivity") ![settingActivity](doc/images/settingsActivity.jpg  "settingActivity")
-
![mainLandscape](doc/images/mainLandscape.jpg  "mainLandscape")

## License

[MIT License](https://github.com/acasadoquijada/dice-roller/blob/master/LICENSE) 
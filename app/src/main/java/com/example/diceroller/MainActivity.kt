package com.example.diceroller

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.example.diceroller.databinding.ActivityMainBinding
import java.util.*
import kotlin.math.log


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var rollImage: ImageView
    private lateinit var mBinding: ActivityMainBinding
    private var mDiceNumber: Int = 0

    private var mDice1ImageKey: String = "first_dice_image"
    private var mDice2ImageKey: String = "second_dice_image"
    private var mDice3ImageKey: String = "third_dice_image"
    private var mDice4ImageKey: String = "fourth_dice_image"

    private var TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if(savedInstanceState != null){
            mBinding.diceImage.setImageResource(savedInstanceState.getInt(mDice1ImageKey))
            mBinding.diceImage2.setImageResource(savedInstanceState.getInt(mDice2ImageKey))
            mBinding.diceImage3.setImageResource(savedInstanceState.getInt(mDice3ImageKey))
            mBinding.diceImage4.setImageResource(savedInstanceState.getInt(mDice4ImageKey))

            // Setup the tags to maintain the dice state between rotation
            mBinding.diceImage.tag = savedInstanceState.getInt(mDice1ImageKey)
            mBinding.diceImage2.tag = savedInstanceState.getInt(mDice2ImageKey)
            mBinding.diceImage3.tag = savedInstanceState.getInt(mDice3ImageKey)
            mBinding.diceImage4.tag = savedInstanceState.getInt(mDice4ImageKey)
        } else{
            // Setup the tags to maintain the dice state between rotation
            mBinding.diceImage.tag = R.drawable.dice_1
            mBinding.diceImage2.tag = R.drawable.dice_1
            mBinding.diceImage3.tag = R.drawable.dice_1
            mBinding.diceImage4.tag = R.drawable.dice_1
        }

        setupDiceListeners()

        setupPreferences()

        // Roll all the dices visible
        mBinding.rollButton.setOnClickListener {
            rollAllDices()
        }
    }

    private fun setupPreferences(){

        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)

        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        loadDiceNumberFromPreferences(sharedPreferences)
    }

    private fun loadDiceNumberFromPreferences(sharedPreferences: SharedPreferences){
        mDiceNumber = sharedPreferences.getString(
            getString(R.string.pref_dice_key),getString(R.string.pref_one_dice_value))?.toInt()
            ?:0

        when(mDiceNumber){
            1 -> setDiceVisibility(View.INVISIBLE,View.INVISIBLE,View.INVISIBLE)
            2 -> setDiceVisibility(View.VISIBLE,View.INVISIBLE,View.INVISIBLE)
            3 -> setDiceVisibility(View.VISIBLE,View.VISIBLE,View.INVISIBLE)
            4 -> setDiceVisibility(View.VISIBLE,View.VISIBLE,View.VISIBLE)
            else -> setDiceVisibility(View.INVISIBLE,View.INVISIBLE,View.INVISIBLE)
        }

    }

    private fun setDiceVisibility(dice2Visibility: Int,
                                  dice3Visibility: Int,
                                  dice4Visibility: Int){

        mBinding.diceImage2.visibility = dice2Visibility
        mBinding.diceImage3.visibility = dice3Visibility
        mBinding.diceImage4.visibility = dice4Visibility
    }

    /**
     * Setups the dice listeners
     */
    private fun setupDiceListeners(){
        mBinding.diceImage.setOnClickListener {
            rollDice(1)
        }

        mBinding.diceImage2.setOnClickListener {
            rollDice(2)
        }

        mBinding.diceImage3.setOnClickListener {
            rollDice(3)
        }
        mBinding.diceImage4.setOnClickListener {
            rollDice(4)
        }
    }

    /**
     * Roll all the dices at the same time
     */
    private fun rollAllDices(){

        for(i in 1..4){
            rollDice(i)
        }
    }

    /**
     * This method rolls the dice provided as argument
     * The roll is only performed is the dice is visible
     */
    private fun rollDice(dice:Int){

        val rollImage:ImageView = when(dice){
            1 -> mBinding.diceImage
            2 -> mBinding.diceImage2
            3 -> mBinding.diceImage3
            4 -> mBinding.diceImage4
            else -> mBinding.diceImage
        }

        // Only roll if dice is visible
        if(rollImage.visibility == View.VISIBLE){

            val drawableResource = when (Random().nextInt(6) + 1) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }

            // Update the tag to be consistent between rotations
            rollImage.tag = drawableResource

            // Update dice image according to its result
            rollImage.setImageResource(drawableResource)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater

        inflater.inflate(R.menu.main_menu, menu)

        return true
    }


    /**
     * Launches the SettingsActivity
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if(id == R.id.action_setting){
         val startSettingsActivity = Intent(this,SettingsActivity::class.java)
            startActivity(startSettingsActivity)
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * Updates the dice number according to the preferences selected by the user
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        if(key.equals(getString(R.string.pref_dice_key))){
            if (sharedPreferences != null) {
                loadDiceNumberFromPreferences(sharedPreferences)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the tags to maintain the dice state between rotation
        outState.putInt(mDice1ImageKey,mBinding.diceImage.tag as Int)
        outState.putInt(mDice2ImageKey,mBinding.diceImage2.tag as Int)
        outState.putInt(mDice3ImageKey,mBinding.diceImage3.tag as Int)
        outState.putInt(mDice4ImageKey,mBinding.diceImage4.tag as Int)
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).
            unregisterOnSharedPreferenceChangeListener(this)
    }
}
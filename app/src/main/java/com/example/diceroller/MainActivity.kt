package com.example.diceroller

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.example.diceroller.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var rollImage: ImageView;
    lateinit var mBinding: ActivityMainBinding;
    var mDiceNumber: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupDiceListeners();

        setupPreferences();

        // Roll all the dices visible
        mBinding.rollButton.setOnClickListener {
            rollAllDices();
        }

    }
    private fun setupPreferences(){

        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        loadDiceNumberFromPreferences(sharedPreferences);
    }

    private fun loadDiceNumberFromPreferences(sharedPreferences: SharedPreferences){
        mDiceNumber = sharedPreferences.getString(
            getString(R.string.pref_dice_key),getString(R.string.pref_one_dice_value))?.toInt()
            ?:0 ;

        if(mDiceNumber == 1){
            setDiceVisibility(View.INVISIBLE,View.INVISIBLE,View.INVISIBLE);
        } else if(mDiceNumber == 2){
            setDiceVisibility(View.VISIBLE,View.INVISIBLE,View.INVISIBLE);
        } else if(mDiceNumber == 3){
            setDiceVisibility(View.VISIBLE,View.VISIBLE,View.INVISIBLE);
        } else if(mDiceNumber == 4){
            setDiceVisibility(View.VISIBLE,View.VISIBLE,View.VISIBLE);
        }
    }

    private fun setDiceVisibility(dice2Visibility: Int,
                                  dice3Visibility: Int,
                                  dice4Visibility: Int){

        mBinding.diceImage2.visibility = dice2Visibility;
        mBinding.diceImage3.visibility = dice3Visibility;
        mBinding.diceImage4.visibility = dice4Visibility
    }

    private fun setupDiceListeners(){
        mBinding.diceImage.setOnClickListener {
            rollDice(1);
        }

        mBinding.diceImage2.setOnClickListener {
            rollDice(2);
        }

        mBinding.diceImage3.setOnClickListener {
            rollDice(3);
        }
        mBinding.diceImage4.setOnClickListener {
            rollDice(4);
        }
    }

    private fun rollAllDices(){

        for(i in 1..4){
            rollDice(i);
        }
    }
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

            val randomInt = Random().nextInt(6) + 1;

            val drawableResource = when (randomInt) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }

            rollImage.setImageResource(drawableResource);
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater;

        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId;

        if(id == R.id.action_setting){
         val startSettingsActivity: Intent = Intent(this,SettingsActivity::class.java);
            startActivity(startSettingsActivity);
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        if(key.equals(getString(R.string.pref_dice_key))){
            if (sharedPreferences != null) {
                loadDiceNumberFromPreferences(sharedPreferences)
            };
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).
            unregisterOnSharedPreferenceChangeListener(this);
    }
}
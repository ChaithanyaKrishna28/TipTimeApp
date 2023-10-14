package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener{
            calculateTip()
        }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
//        setContentView(R.layout.activity_main)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun calculateTip() {
        val textByUser = binding.costOfServiceEditText.text.toString()
        val cost = textByUser.toDoubleOrNull()
        if(cost == null){
            binding.tipResult.text = ""
            return
        }
        //tip opition
        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.amazing -> 0.20
            R.id.good -> 0.18
            else -> 0.15
        }
        // tip calculation
        var tip = tipPercentage * cost
        // round up
        val roundup = binding.roundUpSwitch.isChecked
        if(roundup){
            tip = ceil(tip)
        }
        // currency formatting
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        //display tip
        binding.tipResult.text = getString(R.string.tip_amount,formattedTip)
    }
}
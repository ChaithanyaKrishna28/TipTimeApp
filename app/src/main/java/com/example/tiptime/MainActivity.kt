package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener{
            calculateTip()
        }
//        setContentView(R.layout.activity_main)
    }

    private fun calculateTip() {
        val textByUser = binding.costOfService.text.toString()
        val cost = textByUser.toDouble()
        if(cost ==null){
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
            tip = kotlin.math.ceil(tip)
        }
        // currency formatting
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        //display tip
        binding.tipResult.text = getString(R.string.tip_amount,formattedTip)
    }
}
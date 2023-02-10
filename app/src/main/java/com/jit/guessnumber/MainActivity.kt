package com.jit.guessnumber

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jit.guessnumber.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var viewModel: NumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(NumberViewModel::class.java)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Thinking Number...")
        progressDialog.setCancelable(false)

        enableDisableView(false)

        binding.btGetNumber.setOnClickListener {
            onGetSecretNumber()
        }

        binding.btSubmitNumber.setOnClickListener {
            onGuessSecretNumber()
        }
    }

    private fun onGetSecretNumber() {
        binding.etEnterNumber.setText(null)
        binding.tvResult.text = null
        progressDialog.show()
        object: CountDownTimer(2000,1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
               progressDialog.dismiss()
                viewModel.generateNumber()
                Toast.makeText(this@MainActivity,"Number Generated",Toast.LENGTH_SHORT).show()
                enableDisableView(true)
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun onGuessSecretNumber(){
        val guessNumber = binding.etEnterNumber.text.toString()

        if(guessNumber.isEmpty()){
            Toast.makeText(this@MainActivity,"Enter Number",Toast.LENGTH_SHORT).show()
            return
        }

        if(guessNumber.length != 4){
            Toast.makeText(this@MainActivity,"Please Enter 4 Digit Number!",Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.getBullAndCow(guessNumber)

        val bulls = viewModel.bulls
        val cows = viewModel.cows
        val secretNumber = viewModel.secretNumber


        binding.tvResult.text = "Secret Number: $secretNumber \nBulls- $bulls \nCows- $cows"
        enableDisableView(false)
    }

    private fun enableDisableView(condition: Boolean) {
        binding.apply {
            btSubmitNumber.isEnabled = condition
            etEnterNumber.isEnabled = condition
        }
    }
}
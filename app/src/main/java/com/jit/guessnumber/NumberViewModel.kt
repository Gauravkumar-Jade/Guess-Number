package com.jit.guessnumber

import android.util.Log
import androidx.lifecycle.ViewModel

class NumberViewModel: ViewModel() {

    private var _bulls = 0
    val bulls get() = _bulls

    private var _cows = 0
    val cows get() =  _cows

    private var _secretNumber = 0
    val secretNumber get() = _secretNumber


    fun generateNumber(){
        _secretNumber = 0
        _secretNumber = (1000..9999).random()
        _bulls = 0
        _cows = 0

        Log.i("SECRET_NUM", _secretNumber.toString())
    }

    fun getBullAndCow(guessNumber:String){

        val secretNum = _secretNumber.toString().map { it.toString() }
        val guessNum = guessNumber.map { it.toString()}


        for (i in secretNum.indices) {
            if (secretNum[i] == guessNum[i]) {
                _bulls++
            } else if (secretNum.contains(guessNum[i])) {
                _cows++
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        _bulls =0
        _cows = 0
        _secretNumber= 0
    }
}
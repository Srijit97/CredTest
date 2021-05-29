package com.example.credtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.credtest.utils.PaymentPlanModel
import java.time.Duration

class MainVM: ViewModel() {

    private val _loanAmount = MutableLiveData(0)
    val loanAmount = _loanAmount as LiveData<Int>

    private val _paymentPlan = MutableLiveData<PaymentPlanModel>()
    val paymentPlan = _paymentPlan as LiveData<PaymentPlanModel>


    val userName = MutableLiveData<String>()


    private val _shouldShowIcons = MutableLiveData(false)
    val shouldShowIcons = _shouldShowIcons as LiveData<Boolean>



    fun setLoanAmount(amount: Int){
        _loanAmount.value = amount
    }

    fun setPaymentPlan(amount: Int,duration: Int){
        _paymentPlan.value = PaymentPlanModel(amount,duration)
    }

    fun reset(){
        _loanAmount.value = 0

    }
}
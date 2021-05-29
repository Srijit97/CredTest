package com.example.credtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.credtest.utils.CredTestUtils
import com.example.credtest.utils.PaymentPlanModel
import java.time.Duration

class MainVM: ViewModel() {

    private val _loanAmount = MutableLiveData(0)
    val loanAmount = _loanAmount as LiveData<Int>

    private val _paymentPlan = MutableLiveData<PaymentPlanModel>()
    val paymentPlan = _paymentPlan as LiveData<PaymentPlanModel>


    fun setLoanAmount(amount: Int){
        _loanAmount.value = amount
    }

    fun getLoanAmount(): Int{
        return loanAmount.value?:0
    }

    fun setPaymentPlan(amount: Int,duration: Int){
        _paymentPlan.value = PaymentPlanModel(amount,duration)
    }

    fun setUserName(name: String) {

    }

}
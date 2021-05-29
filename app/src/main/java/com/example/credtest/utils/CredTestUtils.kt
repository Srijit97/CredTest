package com.example.credtest.utils

import kotlin.math.roundToInt

class CredTestUtils {

    companion object{

        fun getLoanAmount(progress: Int): Int {
            return ((progress.toDouble()/100)* maxLoanAmount).roundToInt()
        }

        fun getPaymentPlans(amount:Int): ArrayList<PaymentPlanModel> {

            val paymentList = ArrayList<PaymentPlanModel>()

            monthArray.forEach {
                paymentList.add(
                    PaymentPlanModel(
                        amount = (amount.toDouble()/it).roundToInt(),
                        duration = it
                    )
                )
            }

            return paymentList
        }

        private val monthArray = arrayOf(3,6,9,12,15,18)
        private const val maxLoanAmount= 100000
        val paymentMethods =  listOf("Upi","RazorPay")

        const val razorpayKey ="rzp_test_MehmwvY2czKIn9"


    }

    enum class PaymentMethods{
        UPI,
        RAZORPAY,
    }


}
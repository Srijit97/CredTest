package com.example.credtest.utils

import java.time.Duration
import kotlin.math.pow
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
                        amount = (getCompoundedPrinciple(amount,it)/it).roundToInt(),
                        duration = it
                    )
                )
            }

            return paymentList
        }

        private fun getCompoundedPrinciple(amount: Int,duration: Int): Double {
            val multiplier = (1.0 + interestRate/100.0).pow(duration.toDouble()) - 1.0
            return amount+(multiplier * amount)
        }

        private val monthArray = arrayOf(3,6,9,12,15,18)
        private const val maxLoanAmount= 100000
        val paymentMethods =  listOf("Upi","RazorPay")
        private const val interestRate = 1.04
        const val razorpayKey ="rzp_test_MehmwvY2czKIn9"

        const val UPI_PAYMENT = "UPI"
        const val RAZOR_PAYMENT = "RAZOR"
    }

}
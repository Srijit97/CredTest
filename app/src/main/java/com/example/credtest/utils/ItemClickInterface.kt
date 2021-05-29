package com.example.credtest.utils

import java.time.Duration

interface ItemClickInterface {
    fun onPaymentPlanSelected(amount: Int,duration: Int)
    fun onPaymentMethodSelected(position: Int)
}
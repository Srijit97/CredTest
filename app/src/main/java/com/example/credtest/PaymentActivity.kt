package com.example.credtest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.credtest.utils.CredTestUtils
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentActivity: AppCompatActivity(),PaymentResultListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.getString("paymentMethod","")?.equals(CredTestUtils.UPI_PAYMENT).run {
           if (this!=null){
               if(this)
                   startUPIPayment(intent.extras!!.getInt("paymentAmount",-1))
               else
                   startRazorPayment(intent.extras!!.getInt("paymentAmount",-1))
           }

        }

    }

    private fun startRazorPayment(amount: Int) {
        try {
            val checkout = Checkout()
            checkout.setKeyID(CredTestUtils.razorpayKey)
            checkout.setImage(R.drawable.rzp_logo)

            val options = JSONObject()
            options.put("name", "Merchant Name")
            options.put("description", "Reference No. #123456")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", (amount*100).toString())//pass amount in currency subunits
            options.put("prefill.email", "saha.srijit10111997@gmail.com")
            options.put("prefill.contact", "905`60``52")

            checkout.open(this, options)
        } catch (e: Exception) {
            Log.d("msg1", e.toString())
        }
    }

    private fun startUPIPayment(amount: Int) {
        val paymentUri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", "saha.srijit10111997@okhdfcbank")
            .appendQueryParameter("pn", "Srijit Saha")
            .appendQueryParameter("tn", "Test")
            .appendQueryParameter("am", amount.toString())
            .appendQueryParameter("cu", "INR")
            .build()

        val paymentIntent = Intent(Intent.ACTION_VIEW)
        paymentIntent.data = paymentUri

        val chooser = Intent.createChooser(paymentIntent, "Pay with")
        if (null != chooser.resolveActivity(packageManager)) {
            startActivityForResult(chooser, 1012);
        } else {
            Toast.makeText(
                this,
                "No UPI app found, please install one to continue",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onPaymentSuccess(p0: String?) {

    }

    override fun onPaymentError(p0: Int, p1: String?) {

    }
}
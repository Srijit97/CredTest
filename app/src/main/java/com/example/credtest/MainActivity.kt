package com.example.credtest

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.credtest.databinding.ActivityMainBinding
import com.example.credtest.utils.CredTestUtils
import com.example.credtest.utils.ItemClickInterface
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity(),ItemClickInterface,PaymentResultListener {

    private val viewModel by viewModels<MainVM>()
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = viewModel
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0)
            supportFragmentManager.popBackStack()
        else
            showExitDialog()
    }

    private fun showExitDialog() {
        val exitDialog = Dialog(this)
        exitDialog.setContentView(R.layout.dialog_exit_app)
        exitDialog.setCancelable(false)
        exitDialog.setCanceledOnTouchOutside(false)
        exitDialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        exitDialog.show()
        exitDialog.findViewById<Button>(R.id.yes_button).setOnClickListener {
            exitDialog.dismiss()
            super.onBackPressed()
        }

        exitDialog.findViewById<Button>(R.id.no_button).setOnClickListener {
            exitDialog.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()

        binding.ctaLayout.ctaText.text = text
        binding.ctaLayout.root.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id,LoanAmountFragment()).addToBackStack(null).commit()
        }
    }
    companion object{
        const val text = "Get loan"
    }

    override fun onPaymentPlanSelected(amount: Int, duration: Int) {

    }

    override fun onPaymentMethodSelected(position: Int) {
        try {
            val checkout = Checkout()
            checkout.setKeyID(CredTestUtils.razorpayKey)
            checkout.setImage(R.drawable.rzp_logo)

            val options = JSONObject()
            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","9988776655");

            checkout.open(this,options)
        }
        catch (e: Exception){
            Log.d("msg1",e.toString())
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        doSomething()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("msg1", p1.toString())
    }

    fun doSomething(){

    }
}
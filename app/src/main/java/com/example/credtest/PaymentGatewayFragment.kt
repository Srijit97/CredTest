package com.example.credtest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.credtest.R
import com.example.credtest.adapters.PaymentGatewayAdapter
import com.example.credtest.databinding.FragmentPaymentGatewayBinding
import com.example.credtest.utils.CredTestUtils
import com.example.credtest.utils.ItemClickInterface
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.lang.Exception


class PaymentGatewayFragment : Fragment(), ItemClickInterface{

    lateinit var gatewayBinding: FragmentPaymentGatewayBinding
    val viewModel by activityViewModels<MainVM>()
    var paymentMethod = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gatewayBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_payment_gateway,container,false)
        gatewayBinding.run {
            this.lifecycleOwner = viewLifecycleOwner
            this.vm = viewModel
        }
        return gatewayBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gatewayBinding.paymentGatewayRecycler.adapter = PaymentGatewayAdapter(
            context = requireContext(),
            paymentList = CredTestUtils.paymentMethods,
            this
        )

        gatewayBinding.layoutCta.ctaText.text = resources.getString(R.string.proceed_to_payment)
        gatewayBinding.layoutCta.root.setOnClickListener {
            startPaymentProcess()
        }


//        val razorPay =  Checkout()
//        razorPay.setKeyID(CredTestUtils.razorpayKey)
//
//        val options = JSONObject()
//
//        options.put("name", "dsadasdas")
//        options.put("description", "Reference No. #123456")
//        options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
//        options.put("order_id", "order_DBJOWzybf0sJbb") //from response of step 3.
//
//        options.put("theme.color", "#3399cc")
//        options.put("currency", "INR")
//        options.put("amount", "50000") //pass amount in currency subunits
//
//        options.put("prefill.email", "gaurav.kumar@example.com")
//        options.put("prefill.contact", "9988776655")
//        val retryObj = JSONObject()
//        retryObj.put("enabled", true)
//        retryObj.put("max_count", 4)
//        options.put("retry", retryObj)


        //razorPay.open()
    }

    private fun startPaymentProcess() {

//        val paymentUri = Uri.parse("upi://pay").buildUpon()
//            .appendQueryParameter("pa","saha.srijit10111997@okhdfcbank")
//            .appendQueryParameter("pn","Srijit Saha")
//            .appendQueryParameter("tn","Test")
//            .appendQueryParameter("am","100")
//            .appendQueryParameter("cu","INR")
//            .build()
//
//        val paymentIntent = Intent(Intent.ACTION_VIEW)
//        paymentIntent.data = paymentUri
//
//        val chooser = Intent.createChooser(paymentIntent,"Pay with")
//        if(null != chooser.resolveActivity(requireActivity().packageManager)) {
//            startActivityForResult(chooser, 1012);
//        } else {
//            Toast.makeText(requireContext(),"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
//        }



    }

    override fun onPaymentPlanSelected(amount: Int, duration: Int) {

    }

    override fun onPaymentMethodSelected(position: Int) {

    }


}
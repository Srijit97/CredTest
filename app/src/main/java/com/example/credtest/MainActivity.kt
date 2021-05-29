package com.example.credtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import com.example.credtest.adapters.PaymentGatewayAdapter
import com.example.credtest.adapters.PaymentSchemeAdapter
import com.example.credtest.databinding.ActivityMainBinding
import com.example.credtest.utils.CredTestUtils
import com.example.credtest.utils.ItemClickInterface
import com.example.credtest.utils.SeekArc
import android.net.Uri
import android.util.Log


class MainActivity : AppCompatActivity(), ItemClickInterface,
    View.OnClickListener {

    private val viewModel by viewModels<MainVM>()
    lateinit var binding: ActivityMainBinding
    var paymentMethod: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = viewModel
    }

    override fun onBackPressed() {
        when (binding.root.currentState) {
            R.id.secondView -> binding.root.transitionToState(R.id.firstView)
            R.id.thirdView -> binding.root.transitionToState(R.id.secondView)
            else -> super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()

        initSeekListener()
        initLayoutListener()
        initClicks()

        binding.tvHeadingCredit.text = String.format(
            resources.getString(R.string.name_welcome),
            intent.extras?.getString("userName", "Srijit Saha")
        )
    }

    private fun initClicks() {
        binding.tvStartPayment.setOnClickListener(this)
        binding.fabClose.setOnClickListener(this)
        binding.fabHelp.setOnClickListener(this)
    }

    private fun initLayoutListener() {
        binding.root.setTransitionListener(object: MotionLayout.TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
               p0?.currentState.let {
                   when(it){
                       R.id.secondView -> initEmiRecycler()

                       R.id.thirdView -> {
                           binding.tvSelectedEmi.text = "₹${viewModel.paymentPlan.value?.amount} /mo"
                           binding.tvSelectedEmiDuration.text = "${viewModel.paymentPlan.value?.duration} months"
                           initPaymentRecycler()
                       }
                   }
               }
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }

        } )
    }

    private fun initPaymentRecycler() {
        binding.rvPaymentGateway.adapter = PaymentGatewayAdapter(
            context = this,
            paymentList = CredTestUtils.paymentMethods,

        )
    }

    private fun initEmiRecycler() {
        viewModel.loanAmount.value.let {
            if (it != null) {
                val paymentList = CredTestUtils.getPaymentPlans(it)
                binding.rvPaymentPlan.adapter = PaymentSchemeAdapter(
                    context = this,
                    paymentList = paymentList,
                )
            }
        }
    }

    private fun initSeekListener() {
        binding.tvLoanAmt.text = "₹${viewModel.getLoanAmount()}"
        binding.seekArc.setOnSeekArcChangeListener(object : SeekArc.OnSeekArcChangeListener {
            override fun onProgressChanged(seekArc: SeekArc?, progress: Int, fromUser: Boolean) {

                CredTestUtils.getLoanAmount(progress).run {
                    binding.tvLoanAmt.text = "₹$this"
                    viewModel.setLoanAmount(this)
                }

            }

            override fun onStartTrackingTouch(seekArc: SeekArc?) {
            }

            override fun onStopTrackingTouch(seekArc: SeekArc?) {
            }

        })
    }

    override fun onPaymentPlanSelected(amount: Int, duration: Int) {
        viewModel.setPaymentPlan(amount = amount, duration = duration)
    }

    override fun onPaymentMethodSelected(position: Int) {
        paymentMethod = if(position == 0)
            CredTestUtils.UPI_PAYMENT
        else
            CredTestUtils.RAZOR_PAYMENT
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.tvStartPayment.id -> {
                val intent = Intent(this,PaymentActivity::class.java)
                intent.putExtra("paymentMethod",paymentMethod)
                intent.putExtra("paymentAmt",viewModel.getLoanAmount())
                startActivity(intent)
                finish()
            }

            binding.fabClose.id -> {
                binding.root.transitionToState(R.id.firstView)
            }
            binding.fabHelp.id -> {
                val url = "https://cred.club/privacy"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }
    }


}
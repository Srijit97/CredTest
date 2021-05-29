package com.example.credtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.credtest.adapters.PaymentSchemeAdapter
import com.example.credtest.databinding.FragmentPaymentSchemeBinding
import com.example.credtest.utils.CredTestUtils
import com.example.credtest.utils.ItemClickInterface

class PaymentSchemeFragment : Fragment(),ItemClickInterface {

    lateinit var binding: FragmentPaymentSchemeBinding
    private val viewModel by activityViewModels<MainVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_payment_scheme,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutCta.ctaText.text = resources.getString(R.string.select_bank_account)

        binding.layoutCta.root.setOnClickListener {
            childFragmentManager.beginTransaction().add(binding.fragmentContainer.id,PaymentGatewayFragment()).addToBackStack(null).commit()
        }

        viewModel.loanAmount.value.let {
            if(it!=null){
                val paymentList = CredTestUtils.getPaymentPlans(it)
                binding.paymentPlanRecycler.adapter = PaymentSchemeAdapter(
                    context = requireContext(),
                    paymentList = paymentList,
                    this
                )
            }
        }
    }

    override fun onPaymentPlanSelected(amount: Int, duration: Int) {
        viewModel.setPaymentPlan(amount = amount,duration = duration)
    }

    override fun onPaymentMethodSelected(position: Int) {

    }

}
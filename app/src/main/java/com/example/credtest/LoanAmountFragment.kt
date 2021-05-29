package com.example.credtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.credtest.databinding.FragmentLoanAmountBinding
import com.example.credtest.utils.CredTestUtils
import com.example.credtest.utils.SeekArc

class LoanAmountFragment : Fragment() {

    lateinit var binding: FragmentLoanAmountBinding
    val viewModel by activityViewModels<MainVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_loan_amount,container,false)

        binding.run {
            this.lifecycleOwner = viewLifecycleOwner
            this.vm = viewModel
        }
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ctaLayout.ctaText.text = resources.getString(R.string.proceed_to_emi)

        binding.loanAmountText.text = "₹${viewModel.loanAmount.value.toString()}"

        binding.arcProgress.setOnSeekArcChangeListener(object : SeekArc.OnSeekArcChangeListener{
            override fun onProgressChanged(seekArc: SeekArc?, progress: Int, fromUser: Boolean) {

                CredTestUtils.getLoanAmount(progress).run {
                    binding.loanAmountText.text = "₹$this"
                    viewModel.setLoanAmount(this)
                }

            }

            override fun onStartTrackingTouch(seekArc: SeekArc?) {

            }

            override fun onStopTrackingTouch(seekArc: SeekArc?) {

            }

        })

        binding.ctaLayout.root.setOnClickListener{
            childFragmentManager.beginTransaction().add(binding.fragmentContainer.id,PaymentSchemeFragment()).addToBackStack(null).commit()
        }
    }

    companion object {

    }
}
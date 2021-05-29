package com.example.credtest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.credtest.MainActivity
import com.example.credtest.R
import com.example.credtest.databinding.LayoutPaymentSchemeItemBinding
import com.example.credtest.utils.PaymentPlanModel

class PaymentSchemeAdapter(
    private val context: Context,
    private val paymentList: List<PaymentPlanModel>,
) :
    RecyclerView.Adapter<PaymentSchemeAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutPaymentSchemeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var paymentSchemeBinding: LayoutPaymentSchemeItemBinding
    var currentPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        paymentSchemeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_payment_scheme_item, parent, false
        )
        return ViewHolder(paymentSchemeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (position % 3) {
            0 -> holder.binding.cardBackground.setCardBackgroundColor(
                context.resources.getColor(
                    R.color.vintageBlue,
                    null
                )
            )
            1 -> holder.binding.cardBackground.setCardBackgroundColor(
                context.resources.getColor(
                    R.color.vintageBrown,
                    null
                )
            )
            2 -> holder.binding.cardBackground.setCardBackgroundColor(
                context.resources.getColor(
                    R.color.vintageViolet,
                    null
                )
            )
        }

        holder.binding.paymentCheckListener.isChecked = position == currentPosition


        holder.binding.amountText.text = "₹${paymentList[position].amount} /mo"
        holder.binding.durationText.text = "for ${paymentList[position].duration} months"

        holder.binding.root.setOnClickListener {
            currentPosition = position
            if (context is MainActivity) {

                context.onPaymentPlanSelected(
                    paymentList[position].amount,
                    paymentList[position].duration
                )

            }
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        paymentList.let {
            return if (it.isNullOrEmpty())
                0
            else
                paymentList.size
        }
    }
}
package com.example.credtest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.credtest.PaymentSchemeFragment
import com.example.credtest.R
import com.example.credtest.databinding.LayoutPaymentSchemeItemBinding
import com.example.credtest.utils.ItemClickInterface
import com.example.credtest.utils.PaymentPlanModel

class PaymentSchemeAdapter(
    private val context: Context,
    private val paymentList: List<PaymentPlanModel>,
    private val fragment: Fragment
):
    RecyclerView.Adapter<PaymentSchemeAdapter.ViewHolder>() {

    class ViewHolder( val binding: LayoutPaymentSchemeItemBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var paymentSchemeBinding: LayoutPaymentSchemeItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        paymentSchemeBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.layout_payment_scheme_item,parent,false)
        return ViewHolder(paymentSchemeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when(position%3){
            0 -> holder.binding.cardBackground.setCardBackgroundColor(context.resources.getColor(R.color.recyclerColor1,null))
            1 -> holder.binding.cardBackground.setCardBackgroundColor(context.resources.getColor(R.color.recyclerColor2,null))
            2 -> holder.binding.cardBackground.setCardBackgroundColor(context.resources.getColor(R.color.recyclerColor3,null))
        }

        holder.binding.amountText.text = paymentList[position].amount.toString()
        holder.binding.durationText.text = "${paymentList[position].duration}"

        holder.binding.root.setOnClickListener {
            if(holder.binding.paymentCheckListener.isChecked)
                holder.binding.paymentCheckListener.isChecked = false
            else{
                if(fragment is PaymentSchemeFragment){
                    fragment.onPaymentPlanSelected(paymentList[position].amount,paymentList[position].duration)
                    holder.binding.paymentCheckListener.isChecked = true
                }
            }
        }

        holder.binding.paymentCheckListener.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){


            }

        }

    }

    override fun getItemCount(): Int {
         paymentList.let {
             return if(it.isNullOrEmpty())
                 0
             else
                 paymentList.size
         }
    }
}
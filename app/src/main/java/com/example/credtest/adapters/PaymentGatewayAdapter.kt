package com.example.credtest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.credtest.MainActivity
import com.example.credtest.R
import com.example.credtest.databinding.LayoutPaymentGatewayItemBinding

class PaymentGatewayAdapter(private val context: Context, private val paymentList: List<String>):
    RecyclerView.Adapter<PaymentGatewayAdapter.ViewHolder>() {

    class ViewHolder( val binding: LayoutPaymentGatewayItemBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var paymentSchemeBinding: LayoutPaymentGatewayItemBinding
    var currentPoisition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        paymentSchemeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_payment_gateway_item,parent,false)
        return ViewHolder(paymentSchemeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.paymentCheckListener.isChecked = position==currentPoisition

        holder.binding.paymentGatewayText.text = paymentList[position]

        holder.binding.root.setOnClickListener {
            currentPoisition = position
            holder.binding.paymentCheckListener.isChecked = true
            if(context is MainActivity){
                context.onPaymentMethodSelected(position)
                notifyDataSetChanged()
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
package com.example.credtest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.credtest.MainActivity
import com.example.credtest.PaymentGatewayFragment
import com.example.credtest.R
import com.example.credtest.databinding.LayoutPaymentGatewayItemBinding

class PaymentGatewayAdapter(private val context: Context, private val paymentList: List<String>,val fragment: Fragment):
    RecyclerView.Adapter<PaymentGatewayAdapter.ViewHolder>() {

    class ViewHolder( val binding: LayoutPaymentGatewayItemBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var paymentSchemeBinding: LayoutPaymentGatewayItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        paymentSchemeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_payment_gateway_item,parent,false)
        return ViewHolder(paymentSchemeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.paymentGatewayText.text = paymentList[position]

        holder.binding.root.setOnClickListener {
            if(context is MainActivity){
                context.onPaymentMethodSelected(position)
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
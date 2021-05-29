package com.example.credtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import com.example.credtest.databinding.ActivityLauncherBinding


class LauncherActivity : AppCompatActivity() {
    
    
    lateinit var binding: ActivityLauncherBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_launcher)
        binding.lifecycleOwner = this

        binding.ivNext.setOnClickListener {
            if(!binding.etName.text.toString().trim().isNullOrEmpty()){
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("userName",binding.etName.text.toString().trim())
                startActivity(intent)
                finish()
            }
        }

    }

}
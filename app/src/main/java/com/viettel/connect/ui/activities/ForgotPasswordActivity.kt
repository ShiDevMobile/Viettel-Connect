package com.viettel.connect.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.viettel.connect.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }
}
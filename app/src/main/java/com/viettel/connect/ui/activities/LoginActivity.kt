package com.viettel.connect.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.viettel.connect.databinding.ActivityLoginBinding
import com.viettel.connect.ui.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registerTextView = binding.registerTextview

        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}
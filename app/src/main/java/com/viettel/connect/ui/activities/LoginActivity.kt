package com.viettel.connect.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.viettel.connect.databinding.ActivityLoginBinding
import com.viettel.connect.extensions.ValidationExtensions.validateInput
import com.viettel.connect.extensions.ValidationRule
import com.viettel.connect.ui.viewmodel.UserViewModel
import com.viettel.connect.utils.UserUtils
import com.viettel.connect.utils.UserUtils.isStrongPassword
import com.viettel.connect.utils.UserUtils.isValidEmail

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

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

        val emailInputLayout = binding.emailInputLayout
        val passwordInputLayout = binding.passwordInputLayout

        val emailValidationRules = listOf(
            ValidationRule({ it.isNotEmpty() }, "Email không được để trống"),
            ValidationRule(
                { it.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) },
                "Email không hợp lệ"
            )
        )
        val passwordValidationRules = listOf(
            ValidationRule({ it.isNotEmpty() }, "Mật khẩu không được để trống"),
            ValidationRule({ it.length >= 8 }, "Mật khẩu phải có ít nhất 8 ký tự"),
            ValidationRule(
                { isStrongPassword(it) },
                "Mật khẩu phải chứa chữ viết hoa, số và chữ thường"
            )
        )

        emailInputLayout.validateInput(emailValidationRules)
        passwordInputLayout.validateInput(passwordValidationRules)

        val emailInputEditText = binding.emailInputEdittext
        val passwordInputEditText = binding.passwordInputEdittext

        val loginButton = binding.loginButton
        loginButton.setOnClickListener {
            val email = emailInputEditText.text.toString()
            val password = passwordInputEditText.text.toString()


            viewModel.loginUser(email = email, password = password) { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Dang nhap that bai", Toast.LENGTH_SHORT).show()
                }
            }


        }


    }
}
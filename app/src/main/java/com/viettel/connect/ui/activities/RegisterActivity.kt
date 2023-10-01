package com.viettel.connect.ui.activities


import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.viettel.connect.databinding.ActivityRegisterBinding
import com.viettel.connect.extensions.ValidationExtensions.validateInput
import com.viettel.connect.ui.base.BaseActivity
import com.viettel.connect.ui.viewmodel.UserViewModel
import com.viettel.connect.utils.ValidationRules


class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {


    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    private fun setupLoginLink(binding: ActivityRegisterBinding) {
        val loginTextView = binding.loginTextview
        loginTextView.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupValidationRules(binding: ActivityRegisterBinding) {
        val emailInputLayout = binding.emailInputLayout
        val passwordInputLayout = binding.passwordInputLayout
        val confirmPasswordInputLayout = binding.confirmPasswordInputLayout
        val userNameInputLayout = binding.userNameInputLayout

        emailInputLayout.validateInput(ValidationRules.emailValidationRules())
        passwordInputLayout.validateInput(ValidationRules.passwordValidationRules())
        confirmPasswordInputLayout.validateInput(ValidationRules.confirmPasswordValidationRules())
        userNameInputLayout.validateInput(ValidationRules.userNameValidationRules())
    }

    private fun setupRegisterButton(binding: ActivityRegisterBinding) {
        val emailInputEditText = binding.emailInputEdittext
        val passwordInputEditText = binding.passwordInputEdittext
        val userNameInputEditText = binding.userNameInputEdittext
        val confirmPasswordInputEditText = binding.confirmPasswordInputEdittext
        val registerButton = binding.registerButton


        registerButton.setOnClickListener {
            val email = emailInputEditText.text.toString()
            val password = passwordInputEditText.text.toString()
            val userName = userNameInputEditText.text.toString()
            val confirmPassword = confirmPasswordInputEditText.text.toString()

            if (password != confirmPassword) {
                Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.registerUser(
                email = email,
                password = password,
                name = userName
            ) { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                } else {
                    Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initBinding(): ActivityRegisterBinding {
        setupLoginLink(binding)
        setupValidationRules(binding)
        setupRegisterButton(binding)
        return binding
    }
}
package com.viettel.connect.ui.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.viettel.connect.databinding.ActivityRegisterBinding
import com.viettel.connect.extensions.ValidationExtensions.validateInput
import com.viettel.connect.extensions.ValidationRule
import com.viettel.connect.ui.viewmodel.RegistrationStatus
import com.viettel.connect.ui.viewmodel.UserViewModel
import com.viettel.connect.utils.UserUtils.isStrongPassword
import com.viettel.connect.utils.UserUtils.isValidEmail
import com.viettel.connect.utils.UserUtils.isValidUsername


class RegisterActivity : AppCompatActivity() {


    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailInputLayout = binding.emailInputLayout
        val passwordInputLayout = binding.passwordInputLayout
        val userNameInputLayout = binding.userNameInputLayout
        val confirmPasswordInputLayout = binding.confirmPasswordInputLayout


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
        val userNameValidationRules = listOf(
            ValidationRule({ it.isNotEmpty() }, "Tên không được để trống"),
            ValidationRule({ it.matches(Regex("^[a-zA-Z\\s]+$")) }, "Tên không hợp lệ")
        )
        val confirmPasswordValidationRules = listOf(
            ValidationRule({ it.isNotEmpty() }, "Xác nhận mật khẩu không được để trống"),
            ValidationRule({ it.length >= 8 }, "Mật khẩu phải có ít nhất 8 ký tự"),
            ValidationRule(
                { isStrongPassword(it) },
                "Xác nhận mật khẩu phải chứa chữ viết hoa, số và chữ thường"
            )
        )


        emailInputLayout.validateInput(emailValidationRules)
        passwordInputLayout.validateInput(passwordValidationRules)
        userNameInputLayout.validateInput(userNameValidationRules)
        confirmPasswordInputLayout.validateInput(confirmPasswordValidationRules)


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

            viewModel.registerUser(email = email, password = password, name = userName)

        }
        viewModel.registrationStatus.observe(this) { status ->
            when (status) {
                is RegistrationStatus.Success -> {
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT)
                        .show()

                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }

                is RegistrationStatus.Error -> {
                    Toast.makeText(this, "Đăng ký thất bại: ${status.message}", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }


    }


}
package com.viettel.connect.ui.activities

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.viettel.connect.databinding.ActivityLoginBinding
import com.viettel.connect.extensions.ValidationExtensions.validateInput
import com.viettel.connect.ui.base.BaseActivity
import com.viettel.connect.ui.viewmodel.UserViewModel
import com.viettel.connect.utils.SharedPreferencesManager
import com.viettel.connect.utils.ToastUtil.showToast
import com.viettel.connect.utils.ValidationRules


class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private var isLoggedIn = false
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun initBinding(): ActivityLoginBinding {
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setupRegistrationLink(binding)
        setupValidationRules(binding)
        setupLoginButton(binding)
        setupEmailAutoComplete()
        sharedPreferencesManager = SharedPreferencesManager(this)
        return binding
    }

    private fun setupEmailAutoComplete() {
        val emailInputEditText = binding.emailInputEdittext
        val previousEmails = getLoggedInEmails()
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, previousEmails)
        emailInputEditText.setAdapter(adapter)
        emailInputEditText.setOnItemClickListener { _, _, position, _ ->
            // Xử lý khi người dùng chọn một email từ danh sách
            val selectedEmail = adapter.getItem(position)
            val passwordInputEditText = binding.passwordInputEdittext
            passwordInputEditText.setText("") // Xóa mật khẩu cũ (nếu có)
            passwordInputEditText.clearFocus()
            passwordInputEditText.requestFocus()
            emailInputEditText.setText(selectedEmail)
        }
    }

    private fun setupLoginButton(binding: ActivityLoginBinding) {
        val emailInputEditText = binding.emailInputEdittext
        val passwordInputEditText = binding.passwordInputEdittext
        val loginButton = binding.loginButton

        loginButton.setOnClickListener {
            val email = emailInputEditText.text.toString()
            val password = passwordInputEditText.text.toString()
            try {
                viewModel.loginUser(email = email, password = password) { isSuccess, _ ->
                    if (isSuccess) {
                        showToast(this, "Đăng nhập thành công")

                        isLoggedIn = true
                        saveLoggedInEmail(email)

                        showAccountSuggestion()
                        navigateToHome()
                    } else {
                        showToast(this, "Đăng nhập thất bại")
                    }
                }
            } catch (e: Exception) {
                // Xử lý lỗi ở đây
                showToast(this, "Lỗi xảy ra: ${e.localizedMessage}")
            }
        }
    }

    private fun saveLoggedInEmail(email: String) {
        val previousEmails = getLoggedInEmails().toMutableSet()
        previousEmails.add(email)
        sharedPreferencesManager.savePreviousEmails(previousEmails.toList())

    }

    private fun getLoggedInEmails(): List<String> {
        return sharedPreferencesManager.loadPreviousEmails()
    }

    private fun showAccountSuggestion() {
        if (isLoggedIn) {

            val previousEmails = getLoggedInEmails()
            val dialog = AlertDialog.Builder(this)
                .setTitle("Gợi ý tài khoản")
                .setItems(previousEmails.toTypedArray()) { _, position ->
                    // Gắn email vào EditText email
                    val selectedEmail = previousEmails[position]
                    val emailInputEditText = binding.emailInputEdittext
                    emailInputEditText.setText(selectedEmail)
                }
                .setPositiveButton("Đóng", null)
                .create()
            dialog.show()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupValidationRules(binding: ActivityLoginBinding) {
        val emailInputLayout = binding.emailInputLayout
        val passwordInputLayout = binding.passwordInputLayout

        emailInputLayout.validateInput(ValidationRules.emailValidationRules())
        passwordInputLayout.validateInput(ValidationRules.passwordValidationRules())
    }

    private fun setupRegistrationLink(binding: ActivityLoginBinding) {
        val registerTextView = binding.registerTextview
        registerTextView.setOnClickListener {
            navigateToRegister()
        }

    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}


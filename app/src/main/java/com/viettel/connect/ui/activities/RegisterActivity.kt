package com.viettel.connect.ui.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.viettel.connect.databinding.ActivityRegisterBinding
import com.viettel.connect.ui.viewmodel.RegistrationStatus
import com.viettel.connect.ui.viewmodel.UserViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity() {


    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailInputEditText = binding.emailInputEdittext
        val passwordInputEditText = binding.passwordInputEdittext
        val userNameInputEditText = binding.userNameInputEdittext
        val confirmPasswordInputEditText = binding.confirmPasswordInputEdittext
        val registerButton = binding.registerButton

        registerButton.setOnClickListener{
            val email = emailInputEditText.text.toString()
            val password = passwordInputEditText.text.toString()
            val userName = userNameInputEditText.text.toString()
            val confirmPassword = confirmPasswordInputEditText.text.toString()

            if (password == confirmPassword) {
                // Gọi phương thức đăng ký từ ViewModel
                    viewModel.registerUser(email = email, name = userName, password = password)
            } else {
                // Hiển thị thông báo lỗi mật khẩu không trùng khớp
                Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.registrationStatus.observe(this) { status ->
            when (status) {
                is RegistrationStatus.Success -> {
                    // Đăng ký thành công, thực hiện hành động tương ứng
                    // Ví dụ: chuyển đến màn hình đăng nhập
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }

                is RegistrationStatus.Error -> {
                    // Xử lý lỗi và hiển thị thông báo lỗi
                    Toast.makeText(this, "Đăng ký thất bại: ${status.message}", Toast.LENGTH_SHORT)
                        .show()
                }
                // Các trạng thái khác nếu cần
            }
        }


    }


}
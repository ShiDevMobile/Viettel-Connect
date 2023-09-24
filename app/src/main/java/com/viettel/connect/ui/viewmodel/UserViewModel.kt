package com.viettel.connect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viettel.connect.data.repositories.UserRepository


sealed class RegistrationStatus {
    data object Success : RegistrationStatus()
    data class Error(val message: String) : RegistrationStatus()
    // Các trạng thái khác nếu cần
}

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val _registrationStatus = MutableLiveData<RegistrationStatus>()
    val registrationStatus: LiveData<RegistrationStatus>
        get() = _registrationStatus

    fun registerUser(email: String, name: String, password: String) {

        try {
            userRepository.registerUser(email, name, password)
            _registrationStatus.value = RegistrationStatus.Success
        } catch (e: Exception) {
            // Xử lý lỗi và cập nhật trạng thái lỗi
            _registrationStatus.value = RegistrationStatus.Error(e.message ?: "Đăng ký thất bại")
        }
    }

    fun loginUser(email: String, password: String) {
        userRepository.loginUser(email, password)
    }

    suspend fun getCurrentUser() = userRepository.getCurrentUser()


}
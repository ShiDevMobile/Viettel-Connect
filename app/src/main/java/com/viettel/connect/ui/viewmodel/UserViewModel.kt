package com.viettel.connect.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.viettel.connect.data.models.User
import com.viettel.connect.data.repositories.UserRepository



class UserViewModel(private val userRepository: UserRepository) : ViewModel() {


    fun registerUser(email: String, password: String, name: String,callback: (Boolean) -> Unit) {
        userRepository.registerUser(email, password, name, callback)
    }

    fun loginUser(email: String, password: String, callback: (Boolean, User?) -> Unit) {
        userRepository.loginUser(email, password, callback)
    }



}
package com.viettel.connect.data.repositories

import com.viettel.connect.data.firebase.FirebaseAuthenticationManager
import com.viettel.connect.data.models.User


class UserRepository(private val firebaseAuthenticationManager: FirebaseAuthenticationManager) {


    fun registerUser(email: String, password: String, name: String, callback: (Boolean) -> Unit) {
       firebaseAuthenticationManager.registerUser(email, password, name, callback)
    }

    fun loginUser(email: String, password: String, callback: (Boolean, User?) -> Unit) {
        firebaseAuthenticationManager.loginUser(email, password, callback)

    }



}

package com.viettel.connect.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.viettel.connect.data.models.User
import com.viettel.connect.utils.UserUtils.determineUserRole
import kotlinx.coroutines.tasks.await


class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.reference.child("users")
    fun registerUser(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    val userRole = determineUserRole(email)

                    val user = User(
                        userId = userId,
                        userName = name,
                        avatar = null,
                        email = email,
                        password = password,
                        address = "",
                        role = userRole
                    )

                    usersRef.child(userId).setValue(user)
                        .addOnCompleteListener {
                            Log.e(TAG, "Luu thanh cong")
                        }



                } else {
                    // Xử lý lỗi nếu đăng ký thất bại
                }
            }
    }

    fun loginUser(email: String, password: String) {

    }

    suspend fun getCurrentUser(): User? {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            return usersRef.child(currentUser.uid).get().await().getValue(User::class.java)
        }
        return null
    }


}
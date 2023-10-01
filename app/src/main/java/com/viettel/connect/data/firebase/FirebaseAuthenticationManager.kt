package com.viettel.connect.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.viettel.connect.data.models.User
import com.viettel.connect.utils.UserUtils.determineUserRole
import java.util.Calendar
import java.util.Date

class FirebaseAuthenticationManager(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore) {

    fun loginUser(email: String, password: String, callback: (Boolean, User?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        // Đăng nhập thành công, lấy thông tin người dùng từ Firestore
                        val userId = currentUser.uid
                        firestore.collection("users")
                            .document(userId)
                            .get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    // Lấy thông tin người dùng từ Firestore
                                    val user = document.toObject(User::class.java)
                                    callback(true, user)
                                } else {
                                    callback(false, null)
                                }
                            }
                            .addOnFailureListener {
                                callback(false, null)
                            }
                    } else {
                        callback(false, null)
                    }
                } else {
                    callback(false, null)
                }
            }
    }

    fun registerUser(email: String, password: String, name:String, callback: (Boolean) -> Unit) {

        checkIfEmailExists(email) { emailExists ->
            if (emailExists) {
                // Email đã tồn tại, gọi callback với kết quả là false
                callback(false)
            } else {
            auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userRole = determineUserRole(email)
                    val currentUser = auth.currentUser

                    if (currentUser != null) {
                        val currentDate = Calendar.getInstance()
                        val year = currentDate.get(Calendar.YEAR)
                        val month = currentDate.get(Calendar.MONTH) + 1
                        val day = currentDate.get(Calendar.DAY_OF_MONTH)
                        val userId = currentUser.uid
                        val user = User(
                            userId,
                            name,
                            "",
                            email,
                            password,
                            "",
                            Date(year, month, day),
                            userRole
                        )
                        firestore.collection("users")
                            .document(userId)
                            .set(user)
                            .addOnCompleteListener {
                                    documentReference ->
                                if (documentReference.isSuccessful) {
                                    callback(true)
                                }
                                else {

                                    callback(false)
                                }
                            }

                        }
                    else {
                        callback(false)
                    }
                } else {
                    callback(false)
                }
            }
    }}}

    private fun checkIfEmailExists(email: String, callback: (Boolean) -> Unit) {
        firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    val emailExists = !querySnapshot.isEmpty
                    callback(emailExists)
                } else {
                    callback(false) // Xử lý lỗi
                }
            }

    }
}

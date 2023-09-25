package com.viettel.connect.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.viettel.connect.data.models.User
import com.viettel.connect.utils.UserUtils.determineUserRole
import java.util.Calendar
import java.util.Date


class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")
    fun registerUser(email: String, password: String, name: String) {
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
                        usersCollection.document(userId).set(user)
                    }


                }
            }
    }

    fun loginUser(email: String, password: String, callback: (Boolean) -> Unit) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCustomToken:success")
                    callback(true)
                } else {
                    Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                    callback(false)
                }
            }

    }

    fun getCurrentUser(callback: (User?) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            usersCollection.document(currentUser.uid).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val snapshot = task.result
                        val user = snapshot.toObject(User::class.java)
                        callback(user)
                    } else {
                        callback(null)
                    }
                }
        }
    }


}
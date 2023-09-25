package com.viettel.connect.data.models

import java.util.Date

data class User(
    val userId: String,
    var userName: String,
    var avatar: String,
    val email: String,
    var password: String,
    var address: String,
    var birthday: Date,
    val role: String
)
package com.viettel.connect.data.models

import java.util.Date

data class User(
    val userId: String,
    var userName: String,
    var avatar: String? = null,
    val email: String,
    var password: String,
    var address: String = "",
    var birthday: Date? = null,
    val role: String
) {
    fun getAvatarUrl() = avatar ?: "drawable/icon_user_avatar.png"
}
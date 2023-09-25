package com.viettel.connect.utils

object UserUtils {


    fun determineUserRole(email: String): String {
        val domain = extractDomainFromEmail(email)
        return if (domain == "viettelconstruction.vn") {
            "Nhân viên hỗ trợ"
        } else {
            "Khách hàng"
        }

    }
    fun isStrongPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*[@#\$%^&+=])\\S+\$"
        return password.matches(passwordPattern.toRegex())
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun extractDomainFromEmail(email: String): String {
        val parts = email.split("@")
        if (parts.size == 2) {
            return parts[1]
        }
        return ""
    }

    fun isValidUsername(username: String): Boolean {
        val regex = Regex("^[a-zA-Z\\s]+$")
        return regex.matches(username)
    }




}
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



    private fun extractDomainFromEmail(email: String): String {
        val parts = email.split("@")
        if (parts.size == 2) {
            return parts[1]
        }
        return ""
    }
}
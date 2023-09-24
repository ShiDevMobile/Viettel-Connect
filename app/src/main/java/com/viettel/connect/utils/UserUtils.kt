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

    private fun extractDomainFromEmail(email: String): String {
        val parts = email.split("@")
        if (parts.size == 2) {
            return parts[1]
        }
        return ""
    }




}
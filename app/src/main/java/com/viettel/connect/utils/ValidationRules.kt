package com.viettel.connect.utils

import com.viettel.connect.extensions.ValidationRule

class ValidationRules {
    companion object{
        fun emailValidationRules() = listOf(
            ValidationRule({ it.isNotEmpty() }, "Email không được để trống"),
            ValidationRule(
                { it.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) },
                "Email không hợp lệ"
            )
        )

        fun  passwordValidationRules() = listOf(
            ValidationRule({ it.isNotEmpty() }, "Mật khẩu không được để trống"),
            ValidationRule({ it.length >= 8 }, "Mật khẩu phải có ít nhất 8 ký tự"),
            ValidationRule(
                { UserUtils.isStrongPassword(it) },
                "Mật khẩu phải chứa chữ viết hoa, số và chữ thường"
            )
        )
        fun userNameValidationRules() = listOf(
            ValidationRule({ it.isNotEmpty() }, "Tên không được để trống"),
            ValidationRule({ it.matches(Regex("^[a-zA-Z\\s]+$")) }, "Tên không hợp lệ")
        )
        fun confirmPasswordValidationRules() = listOf(
            ValidationRule({ it.isNotEmpty() }, "Xác nhận mật khẩu không được để trống"),
            ValidationRule({ it.length >= 8 }, "Mật khẩu phải có ít nhất 8 ký tự"),
            ValidationRule(
                { UserUtils.isStrongPassword(it) },
                "Xác nhận mật khẩu phải chứa chữ viết hoa, số và chữ thường"
            )
        )
    }
}
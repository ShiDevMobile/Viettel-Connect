package com.viettel.connect.extensions

import com.google.android.material.textfield.TextInputLayout

object ValidationExtensions {
    fun TextInputLayout.validateInput(
        validationRules: List<ValidationRule>
    ) {
        val editText = this.editText ?: return

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val inputText = editText.text.toString()
                var hasError = false
                for (rule in validationRules) {
                    if (!rule.isValid(inputText)) {
                        isErrorEnabled = true
                        error = rule.errorMessage
                        hasError = true
                        break
                    }
                }
                if (!hasError) {
                    isErrorEnabled = false
                    error = null
                }
            }
        }

    }

}

data class ValidationRule(
    val isValid: (String) -> Boolean,
    val errorMessage: String
)
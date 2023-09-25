package com.viettel.connect.extensions

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

object ValidationExtensions {
    fun TextInputLayout.validateInput(
        validationRules: List<ValidationRule>
    ) {
        val editText = this.editText ?: return

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInput(validationRules)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputText = s.toString()
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

        })
    }

}

data class ValidationRule(
    val isValid: (String) -> Boolean,
    val errorMessage: String
)
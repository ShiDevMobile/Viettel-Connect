package com.viettel.connect.utils

import android.content.Context
import android.widget.Toast

object ToastUtil {
    fun showToast(context: Context, messageResId: Int) {
        val message = context.getString(messageResId)
        showToast(context, message)
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToastLong(context: Context, messageResId: Int) {
        val message = context.getString(messageResId)
        showToastLong(context, message)
    }

     fun showToastLong(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
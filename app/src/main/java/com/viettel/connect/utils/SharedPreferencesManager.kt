package com.viettel.connect.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context){
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "AppPreferences", Context.MODE_PRIVATE
    )

    fun savePreviousEmails(emails: List<String>){
        val editor = sharedPreferences.edit()
        editor.putStringSet("previousEmails", emails.toSet())
        editor.apply()
    }

    fun loadPreviousEmails(): List<String>{
        val emailSet= sharedPreferences.getStringSet("previousEmails", emptySet() )
        return emailSet?.toList() ?: emptyList()
    }
}
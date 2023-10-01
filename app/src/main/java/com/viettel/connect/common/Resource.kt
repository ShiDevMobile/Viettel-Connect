package com.viettel.connect.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val errorCode: String? = null) : Resource<Nothing>()
}
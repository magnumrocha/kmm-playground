package com.magnumrocha.kmm.playground.utils

sealed class DataState<T> {
    data class Loading<T>(val data: T? = null) : DataState<T>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val data: T? = null, val reason: Throwable) : DataState<T>()
}

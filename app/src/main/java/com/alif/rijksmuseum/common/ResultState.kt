package com.alif.rijksmuseum.common

import androidx.annotation.StringRes

sealed class ResultState <out T> {
    class NoInternetConnection<out T>: ResultState<T>()
    class Loading<out T>: ResultState<T>()
    class NoData<out T>: ResultState<T>()
    data class HasData<out T>(val data: T): ResultState<T>()
    data class Error<out T> (@StringRes val errorMessage: Int): ResultState<T>()
    data class ErrorMessage<out T>(val errorMessage: String): ResultState<T>()
}
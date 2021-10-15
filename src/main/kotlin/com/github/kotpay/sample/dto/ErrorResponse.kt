package com.github.kotpay.sample.dto

data class ErrorResponse<T>(private val errorHolder: ErrorHolder) : Response<T> {
    override fun getSuccess(): Boolean = false
    override fun getError(): ErrorHolder = errorHolder
}
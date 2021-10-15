package com.github.kotpay.sample.dto

data class SuccessResponse<T>(private val data: T) : Response<T> {
    override fun getSuccess(): Boolean = true
    override fun getData(): T = data
}
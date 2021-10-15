package com.github.kotpay.sample.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Contains data if success is true, otherwise contains error")
interface Response<T> {
    @JsonProperty(index = 0)
    @Schema(description = "Success flag", required = true)
    fun getSuccess(): Boolean
    fun getData(): T? = null
    fun getError(): ErrorHolder? = null
}
package com.github.kotpay.sample.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ErrorHolder(
    @Schema(description = "Error code", example = "400")
    val code: Int,
    @Schema(description = "Error description", example = "Requested file not found")
    val description: String
)
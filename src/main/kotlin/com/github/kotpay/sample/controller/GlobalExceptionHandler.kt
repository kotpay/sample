package com.github.kotpay.sample.controller

import com.github.kotpay.sample.dto.ErrorHolder
import com.github.kotpay.sample.dto.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> = handleException(ex)

    @ExceptionHandler(value = [Exception::class])
    fun handleException(exception: Exception): ResponseEntity<Any> {
        logger.error("Caught unhandled exception", exception)
        return ResponseEntity.ok(ErrorResponse<Any>(ErrorHolder(500, "Internal error")))
    }
}
package com.github.kotpay.sample.api

import com.github.kotpay.sample.dto.Response
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping(value = ["/test"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface TestControllerMeta {
    @GetMapping("/hello")
    @Operation(summary = "Returns hello message", tags = [SwaggerTag.BASIC_RESOURCE_TAG])
    fun getHello(@RequestParam @Parameter(description = "Name to say hello to") name: String): Response<String>

    @GetMapping("/exception")
    @Operation(summary = "Throws an exception", tags = [SwaggerTag.BASIC_RESOURCE_TAG])
    fun getException(): Response<String>
}
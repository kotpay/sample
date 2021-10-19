package com.github.kotpay.sample.api

import com.github.kotpay.api.dto.Response
import com.github.kotpay.api.sample.SampleTestApi
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping(value = ["/test"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface SampleControllerMeta : SampleTestApi {
    @GetMapping("/hello")
    @Operation(summary = "Returns hello message", tags = [SwaggerTag.BASIC_RESOURCE_TAG])
    override fun getHello(@RequestParam @Parameter(description = "Name to say hello to") name: String): Response<String>

    @GetMapping("/exception")
    @Operation(summary = "Throws an exception", tags = [SwaggerTag.BASIC_RESOURCE_TAG])
    override fun getException(): Response<String>
}
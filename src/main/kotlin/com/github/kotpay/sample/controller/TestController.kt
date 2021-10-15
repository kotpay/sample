package com.github.kotpay.sample.controller

import com.github.kotpay.sample.api.TestControllerMeta
import com.github.kotpay.sample.dto.Response
import com.github.kotpay.sample.dto.SuccessResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController : TestControllerMeta {
    override fun getHello(name: String): Response<String> = SuccessResponse("Hello, $name!")
    override fun getException(): Response<String> = throw IllegalArgumentException("BAD")
}
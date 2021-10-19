package com.github.kotpay.sample.controller

import com.github.kotpay.api.dto.Response
import com.github.kotpay.api.dto.SuccessResponse
import com.github.kotpay.sample.api.SampleControllerMeta
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController : SampleControllerMeta {
    override fun getHello(name: String): Response<String> = SuccessResponse("Hello, $name!")
    override fun getException(): Response<String> = throw IllegalArgumentException("BAD")
}
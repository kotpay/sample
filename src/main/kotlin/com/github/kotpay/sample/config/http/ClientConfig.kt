package com.github.kotpay.sample.config.http

import org.springframework.http.client.reactive.ClientHttpConnector

interface ClientConfig {
    fun defaultClientHttpConnector(): ClientHttpConnector
}
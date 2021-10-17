package com.github.kotpay.sample.config.http

import org.eclipse.jetty.client.HttpClient
import org.eclipse.jetty.util.ssl.SslContextFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.JettyClientHttpConnector
import javax.net.ssl.SSLContext

@Profile("jetty-client")
@Configuration
class JettyClientConfig(private val sslContext: SSLContext) : ClientConfig {
    @Bean
    override fun defaultClientHttpConnector(): ClientHttpConnector {
        val sslContextFactory = SslContextFactory.Client()
        sslContextFactory.sslContext = sslContext
        return JettyClientHttpConnector(HttpClient(sslContextFactory))
    }
}
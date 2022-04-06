package com.github.kotpay.sample.config.http

import io.netty.handler.ssl.ApplicationProtocolConfig
import io.netty.handler.ssl.ClientAuth
import io.netty.handler.ssl.IdentityCipherSuiteFilter
import io.netty.handler.ssl.JdkSslContext
import io.netty.handler.ssl.SslContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import reactor.netty.http.client.HttpClient
import javax.net.ssl.SSLContext

@Profile("reactor-client")
@Configuration
class ReactorClientConfig(private val sslContext: SSLContext) : ClientConfig {
    @Bean
    override fun defaultClientHttpConnector(): ClientHttpConnector =
        ReactorClientHttpConnector(defaultHttpClient(defaultSslContext(sslContext)))

    private fun defaultSslContext(sslContext: SSLContext): SslContext =
        JdkSslContext(
            sslContext, true, null, IdentityCipherSuiteFilter.INSTANCE, ApplicationProtocolConfig.DISABLED,
            ClientAuth.NONE, null, false
        )

    private fun defaultHttpClient(defaultSslContext: SslContext): HttpClient {
        return HttpClient.create().secure { it.sslContext(defaultSslContext) }
    }
}
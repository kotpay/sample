package com.github.kotpay.sample.config.http

import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy
import org.apache.hc.core5.util.TimeValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext

@Profile("apache-client")
@Configuration
class HttpComponentsClientConfig(
    private val sslContext: SSLContext,
    @Autowired(required = false) private val hostnameVerifier: HostnameVerifier?,
    @Value("\${http.connection.total:25}") private val maxConnTotal: Int,
    @Value("\${http.connection.route:5}") private val maxConnRoute: Int,
    @Value("\${http.timeout.live:3M}") private val timeoutLive: Duration,
    @Value("\${http.timeout.connect:1M}") private val timeoutConnect: Duration,
    @Value("\${http.timeout.response:1M}") private val timeoutResponse: Duration,
) : ClientConfig {
    @Bean
    override fun defaultClientHttpConnector(): ClientHttpConnector {
        val tlsStrategy = DefaultClientTlsStrategy(sslContext, hostnameVerifier)
        val connectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
            .setMaxConnTotal(maxConnTotal)
            .setMaxConnPerRoute(maxConnRoute)
            .setConnectionTimeToLive(TimeValue.ofMilliseconds(timeoutLive.toMillis()))
            .setTlsStrategy(tlsStrategy)
            .build()
        val requestConfig = RequestConfig.custom()
            .setResponseTimeout(timeoutResponse.toMillis(), TimeUnit.MILLISECONDS)
            .setConnectTimeout(timeoutConnect.toMillis(), TimeUnit.MILLISECONDS)
            .build()
        val httpAsyncClient = HttpAsyncClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .setConnectionManager(connectionManager)
            .build()
        return HttpComponentsClientHttpConnector(httpAsyncClient)
    }
}
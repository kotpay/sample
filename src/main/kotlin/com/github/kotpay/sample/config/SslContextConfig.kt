package com.github.kotpay.sample.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory

@Configuration
class SslContextConfig {
    @Bean
    @ConditionalOnMissingBean(SSLContext::class)
    @ConditionalOnProperty(value = ["http.truststore.resource"])
    fun trustStoreSslContext(
        @Value("\${http.truststore.resource}") truststoreResource: Resource,
        @Value("\${http.truststore.password}") truststorePassword: String,
        @Value("\${http.truststore.type}") truststoreType: String
    ): SSLContext {
        val keyStore = KeyStore.getInstance(truststoreType)
        keyStore.load(truststoreResource.inputStream, truststorePassword.toCharArray())
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, loadTrustMaterial(keyStore), null)
        return sslContext
    }

    private fun loadTrustMaterial(truststore: KeyStore): Array<TrustManager> {
        val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        factory.init(truststore)
        return factory.trustManagers
    }

    @Bean
    @ConditionalOnMissingBean(SSLContext::class)
    fun jdkSslContext(): SSLContext = SSLContext.getDefault()
}
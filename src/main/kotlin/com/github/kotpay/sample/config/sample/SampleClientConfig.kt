package com.github.kotpay.sample.config.sample

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.web.codec.CodecCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class SampleClientConfig {

    @Bean
    fun sampleObjectMapper(): ObjectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    @Bean
    fun sampleCustomizer(@Qualifier("sampleObjectMapper") sampleObjectMapper: ObjectMapper): CodecCustomizer =
        CodecCustomizer { configurer ->
            val defaults = configurer.defaultCodecs()
            defaults.jackson2JsonDecoder(Jackson2JsonDecoder(sampleObjectMapper))
            defaults.jackson2JsonEncoder(Jackson2JsonEncoder(sampleObjectMapper))
        }

    @Bean
    fun sampleClient(
        @Qualifier("defaultClientHttpConnector") defaultClientHttpConnector: ClientHttpConnector,
        @Qualifier("sampleCustomizer") sampleCustomizer: CodecCustomizer
    ): WebClient =
        WebClient.builder()
            .clientConnector(defaultClientHttpConnector)
            .codecs { sampleCustomizer.customize(it) }
            .build()
}
package com.github.kotpay.sample.config

import org.h2.tools.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("h2")
@Configuration
class H2Config {
    @Bean(initMethod = "start", destroyMethod = "stop")
    fun h2Server(): Server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092")
}
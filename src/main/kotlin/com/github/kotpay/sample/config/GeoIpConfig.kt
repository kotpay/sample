package com.github.kotpay.sample.config

import com.maxmind.geoip2.DatabaseReader
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource


@Configuration
class GeoIpConfig {

    @Bean
    @ConditionalOnProperty("geoIp.mmdb.resource")
    fun mmdbDatabaseReader(@Value("\${geoIp.mmdb.resource}") databaseResource: Resource): DatabaseReader =
        databaseResource.inputStream
            .use {
                val builder = DatabaseReader.Builder(it)
                builder.build()
            }

    @Bean
    @ConditionalOnMissingBean(DatabaseReader::class)
    @ConditionalOnProperty("geoIp.gz.resource")
    fun gzDatabaseReader(@Value("\${geoIp.gz.resource}") databaseResource: Resource): DatabaseReader =
        databaseResource.inputStream
            .use {
                val gzipInputStream = GzipCompressorInputStream(it)
                val tarInputStream = TarArchiveInputStream(gzipInputStream)
                while (!tarInputStream.nextEntry.name.endsWith("mmdb")) {
                    continue
                }
                val builder = DatabaseReader.Builder(tarInputStream)
                builder.build()
            }

}
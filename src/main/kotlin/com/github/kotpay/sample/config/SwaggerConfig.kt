package com.github.kotpay.sample.config

import com.github.kotpay.sample.api.SwaggerTag
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("swagger")
@Configuration
class SwaggerConfig(buildProperties: BuildProperties) {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .info(info)
        .tags(tags)

    val info: Info = Info()
        .title(buildProperties.name)
        .description(buildProperties.group + ":" + buildProperties.artifact)
        .version(buildProperties.version)

    val tags: List<Tag> = listOf(
        Tag().name(SwaggerTag.BASIC_RESOURCE_TAG).description("Contains basic get resources"),
    )
}
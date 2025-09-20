package ru.hits.bdui.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration

class ApplicationConfiguration {
    @Bean("CustomObjectMapper")
    fun customObjectMapper(): ObjectMapper =
        jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Bean("CustomWebClient")
    fun webClient(): WebClient =
        WebClient.builder()
            .build()
}
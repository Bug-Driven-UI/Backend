package ru.hits.bdui.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.server.WebFilter

@Configuration
@ConditionalOnProperty(name = ["hits.security.enabled"])
@EnableConfigurationProperties(SecurityProperties::class)
class SecurityConfiguration {
    @Bean
    fun securityFilter(securityProperties: SecurityProperties): WebFilter =
        WebFilter { exchange, chain ->
            val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

            if (authHeader != securityProperties.apiKey) {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                exchange.response.setComplete()
            } else {
                chain.filter(exchange)
            }
        }
}

@ConfigurationProperties("hits.security")
data class SecurityProperties(
    val apiKey: String,
)


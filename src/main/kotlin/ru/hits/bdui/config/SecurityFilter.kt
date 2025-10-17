package ru.hits.bdui.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.WebFilter

@Configuration
@ConditionalOnProperty(name = ["hits.security.enabled"])
@EnableConfigurationProperties(SecurityProperties::class)
class SecurityConfiguration {
    @Bean
    fun securityFilter(securityProperties: SecurityProperties): WebFilter =
        WebFilter { exchange, chain ->
            val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

            //TODO(УДАЛИТЬ ЭТУ ЛЕГЕНДАРНУЮ ЗАЩИТУ)
            if (!exchange.request.path.value().endsWith("/check") && authHeader != securityProperties.apiKey) {
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

@RestController
@ConditionalOnBean(SecurityConfiguration::class)
class SecurityController(
    private val securityProperties: SecurityProperties
) {
    @GetMapping("/check")
    fun check(@RequestHeader(name = HttpHeaders.AUTHORIZATION) apiKey: String): Boolean =
        securityProperties.apiKey == apiKey
}


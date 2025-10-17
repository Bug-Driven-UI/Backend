package ru.hits.bdui.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Configuration
@ConditionalOnProperty(name = ["hits.security.enabled"])
@EnableConfigurationProperties(SecurityProperties::class)
class SecurityConfiguration {
    @Component
    class SecurityFilter(
        private val securityProperties: SecurityProperties
    ) : WebFilter {
        override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
            val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

            return if (authHeader != securityProperties.apiKey) {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                exchange.response.setComplete()
            } else {
                chain.filter(exchange)
            }
        }
    }
}

@ConfigurationProperties("hits.security")
data class SecurityProperties(
    val apiKey: String,
)


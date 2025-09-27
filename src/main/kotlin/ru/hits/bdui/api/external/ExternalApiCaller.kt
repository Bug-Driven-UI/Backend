package ru.hits.bdui.api.external

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.api.external.ExternalApiCaller.Response
import ru.hits.bdui.domain.api.Endpoint
import ru.hits.bdui.domain.api.HttpMethod
import java.time.Duration

/**
 * Отвечает за асинхронный вызов внешних эндпоинтов
 */
sealed interface ExternalApiCaller {
    fun call(endpoint: Endpoint): Mono<Response>

    sealed interface Response {
        data class Success(val result: JsonNode) : Response
        data class Error(val error: Throwable) : Response
    }
}

@Component
class ExternalApiCallerImpl(
    @Qualifier("CustomWebClient") private val webClient: WebClient,
    @Qualifier("CustomObjectMapper") private val objectMapper: ObjectMapper
) : ExternalApiCaller {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun call(endpoint: Endpoint): Mono<Response> =
        webClient
            .methodByString(endpoint.method)
            .uri(endpoint.url)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { objectMapper.readTree(it) }
            .map<Response>(Response::Success)
            .timeout(
                Duration.ofMillis(endpoint.timeoutMs),
                Mono.error(ExternalApiCallerException.TimeoutException("Не удалось получить ответ по запросу в течении ${endpoint.timeoutMs} мс"))
            )
            .doOnError { error ->
                log.error(
                    "При отправке ${endpoint.method} запроса по пути ${endpoint.url} произошла ошибка",
                    error
                )
            }
            .onErrorMap { error ->
                when (error) {
                    is ExternalApiCallerException -> error
                    else -> ExternalApiCallerException.UnexpectedException(error)
                }
            }
            .onErrorResume { Response.Error(it).toMono() }

    private fun WebClient.methodByString(method: HttpMethod): WebClient.RequestHeadersUriSpec<*> =
        when (method) {
            HttpMethod.GET -> this.get()
            HttpMethod.POST -> this.post()
            HttpMethod.PUT -> this.put()
            HttpMethod.DELETE -> this.delete()
            HttpMethod.PATCH -> this.patch()
        }
}
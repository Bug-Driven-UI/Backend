package ru.hits.bdui.core.api.caller

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.core.api.caller.ExternalApiCaller.Response
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
            .withMethodAndUri(endpoint.method, endpoint.url)
            .requestBodyWhenPossible(endpoint.requestBody)
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

    private fun WebClient.withMethodAndUri(method: HttpMethod, uri: String): WebClient.RequestHeadersSpec<*> =
        when (method) {
            HttpMethod.GET -> this.get().uri(uri)
            HttpMethod.POST -> this.post().uri(uri)
            HttpMethod.PUT -> this.put().uri(uri)
            HttpMethod.DELETE -> this.delete().uri(uri)
            HttpMethod.PATCH -> this.patch().uri(uri)
        }

    private fun WebClient.RequestHeadersSpec<*>.requestBodyWhenPossible(body: JsonNode?): WebClient.RequestHeadersSpec<*> =
        if (body != null && this is WebClient.RequestBodySpec) this.bodyValue(body) else this
}
package ru.hits.bdui.parser.api

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.hits.bdui.domain.Endpoint
import ru.hits.bdui.parser.api.ApiCaller.Response
import java.time.Duration
import java.util.concurrent.CompletableFuture

/**
 * Отвечает за асинхронный вызов внешних эндпоинтов
 */
sealed interface ApiCaller {
    fun call(endpoint: Endpoint): CompletableFuture<Response>

    sealed interface Response {
        data class Success(val result: JsonNode) : Response
        data class Error(val error: Throwable) : Response
    }
}

@Component
class ApiCallerImpl(
    @Qualifier("CustomWebClient") private val webClient: WebClient,
    @Qualifier("CustomObjectMapper") private val objectMapper: ObjectMapper
) : ApiCaller {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun call(endpoint: Endpoint): CompletableFuture<Response> =
        webClient
            .methodByString(endpoint.method)
            .uri(endpoint.url)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { objectMapper.readTree(it) }
            .map<Response>(Response::Success)
            .timeout(
                Duration.ofMillis(endpoint.timeoutMs),
                Mono.error(ApiCallerException.TimeoutException("Не удалось получить ответ по запросу в течении ${endpoint.timeoutMs} мс"))
            )
            .doOnError { error ->
                log.error(
                    "При отправке ${endpoint.method.lowercase()} запроса по пути ${endpoint.url} произошла ошибка",
                    error
                )
            }
            .onErrorMap { error ->
                when (error) {
                    is ApiCallerException -> error
                    else -> ApiCallerException.UnexpectedException(error)
                }
            }
            .onErrorResume { Mono.just(Response.Error(it)) }
            .toFuture()

    private fun WebClient.methodByString(method: String): WebClient.RequestHeadersUriSpec<*> =
        when (method.lowercase()) {
            "get" -> this.get()
            "post" -> this.post()
            "put" -> this.put()
            "delete" -> this.delete()
            "head" -> this.head()
            "patch" -> this.patch()
            "options" -> this.options()
            else -> throw ApiCallerException.UnknownMethodException(method)
        }
}
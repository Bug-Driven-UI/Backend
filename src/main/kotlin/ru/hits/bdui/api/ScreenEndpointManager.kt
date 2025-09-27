package ru.hits.bdui.api

import com.fasterxml.jackson.databind.JsonNode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hits.bdui.api.external.ExternalApiCaller
import ru.hits.bdui.domain.screen.ErrorContent
import ru.hits.bdui.domain.screen.Screen
import ru.hits.bdui.domain.screen.ScreenResponse

/**
 * Отвечает за запрос данных по внешним эндпоинтам для экрана
 */
sealed interface ScreenEndpointManager {
    fun getData(screen: Screen): Mono<Response>

    sealed interface Response {
        data class Success(val data: Map<String, JsonNode>) : Response
        data object NothingToRequest : Response
        data class Error(val error: ScreenResponse.Error) : Response
    }
}

@Component
class ScreenEndpointManagerImpl(
    private val externalApiCaller: ExternalApiCaller
) : ScreenEndpointManager {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun getData(screen: Screen): Mono<ScreenEndpointManager.Response> {
        val endpoints = screen.apis.values.flatMap { it.endpoints }.toSet()

        if (endpoints.isEmpty()) {
            log.info("У данного экрана отсутствуют запросы. Пропуск этапа запроса данных")
            return Mono.just(ScreenEndpointManager.Response.NothingToRequest)
        }

        return Flux.fromIterable(endpoints)
            .flatMap { endpoint ->
                externalApiCaller.call(endpoint)
                    .map { response -> endpoint to response }
            }
            .collectList()
            .map { results ->
                val (successfulRequests, failedRequests) = results.partition { (_, response) ->
                    response is ExternalApiCaller.Response.Success
                }

                val failedRequiredRequests = failedRequests.filter { (endpoint, _) -> endpoint.isRequired }

                if (failedRequiredRequests.isNotEmpty()) {
                    log.error("Не удалось получить данные по обязательным эндпоинтам: {}", failedRequiredRequests)

                    ScreenEndpointManager.Response.Error(
                        ScreenResponse.Error(
                            failedRequiredRequests.map { (endpoint, _) ->
                                ErrorContent.emerge("Не удалось получить данные от обязательного эндпоинта ${endpoint.url}")
                            }
                        )
                    )
                } else {
                    val responseNameToJsonNode = successfulRequests.associate { (endpoint, response) ->
                        endpoint.responseName to (response as ExternalApiCaller.Response.Success).result
                    }

                    ScreenEndpointManager.Response.Success(responseNameToJsonNode)
                }
            }
            .onErrorResume { error ->
                log.error("При получении данных для насыщения экрана произошла ошибка", error)
                Mono.just(
                    ScreenEndpointManager.Response.Error(
                        ScreenResponse.Error(
                            listOf(ErrorContent.emerge("Не удалось получить данные для насыщения экрана"))
                        )
                    )
                )
            }
    }

}
package ru.hits.bdui.parser

import com.fasterxml.jackson.databind.JsonNode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.hits.bdui.domain.ErrorContent
import ru.hits.bdui.domain.Screen
import ru.hits.bdui.domain.ScreenResponse
import ru.hits.bdui.parser.api.ApiCaller
import java.util.concurrent.CompletableFuture

/**
 * Отвечает за запрос данных по внешним эндпоинтам для экрана
 */
sealed interface ScreenEndpointManager {
    fun getData(screen: Screen): CompletableFuture<Response>

    sealed interface Response {
        data class Success(val data: Map<String, JsonNode>) : Response
        data class Error(val error: ScreenResponse.Error) : Response
    }
}

@Component
class ScreenEndpointManagerImpl(
    private val apiCaller: ApiCaller
) : ScreenEndpointManager {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun getData(screen: Screen): CompletableFuture<ScreenEndpointManager.Response> {
        val endpointFutures = screen.endpoints
            .map { endpoint ->
                apiCaller.call(endpoint)
                    .thenApply { response -> endpoint to response }
            }

        return CompletableFuture.allOf(*endpointFutures.toTypedArray())
            .thenApply {
                val results = endpointFutures.map { it.join() }
                val (successfulRequests, failedRequests) = results
                    .partition { (_, response) -> response is ApiCaller.Response.Success }

                val failedRequiredRequests = failedRequests
                    .filter { (endpoint, _) -> endpoint.isRequired }

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
                    val responseNameToJsonNode = successfulRequests.associate {
                        val endpoint = it.first
                        val response = it.second as ApiCaller.Response.Success

                        endpoint.responseName to response.result
                    }

                    ScreenEndpointManager.Response.Success(responseNameToJsonNode)
                }
            }
            .exceptionally { error ->
                log.error("При получении данных для насыщения экрана произошла ошибка", error)

                ScreenEndpointManager.Response.Error(
                    ScreenResponse.Error(
                        listOf(ErrorContent.emerge("Не удалось получить данные для насыщения экрана"))
                    )
                )
            }
    }
}
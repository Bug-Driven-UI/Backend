package ru.hits.bdui.api

import com.fasterxml.jackson.databind.JsonNode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.domain.screen.ErrorContent
import ru.hits.bdui.domain.screen.Screen
import ru.hits.bdui.domain.screen.ScreenResponse

interface ScreenExpressionResolver {
    fun enrich(screen: Screen): Mono<Response>

    sealed interface Response {
        data class Success(val screen: Screen) : Response
        data class Error(val error: ScreenResponse.Error) : Response
    }
}

@Component
class ScreenExpressionResolverImpl(
    private val endpointManager: ScreenEndpointManager
) : ScreenExpressionResolver {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun enrich(screen: Screen): Mono<ScreenExpressionResolver.Response> =
        endpointManager.getData(screen)
            .map { response ->
                when (response) {
                    is ScreenEndpointManager.Response.Success ->
                        ScreenExpressionResolver.Response.Success(replaceExpressions(screen, response.data))

                    is ScreenEndpointManager.Response.NothingToRequest ->
                        ScreenExpressionResolver.Response.Success(screen)

                    is ScreenEndpointManager.Response.Error ->
                        ScreenExpressionResolver.Response.Error(response.error)
                }
            }
            .doOnError { error ->
                log.error("При получении данных для насыщения экрана произошла ошибка", error)
            }
            .onErrorResume {
                ScreenExpressionResolver.Response.Error(
                    ScreenResponse.Error(
                        listOf(ErrorContent.emerge("Не удалось наполнить экран данными из переменных"))
                    )
                ).toMono()
            }

    private fun replaceExpressions(
        screen: Screen,
        responseNameToResult: Map<String, JsonNode>
    ): Screen {
        if (responseNameToResult.isEmpty()) {
            log.info("Отсутствует данные для подмены переменных. Пропуск подмены")
            return screen
        }

        return screen //TODO(Заменить на подмену выражений на значения)
    }
}
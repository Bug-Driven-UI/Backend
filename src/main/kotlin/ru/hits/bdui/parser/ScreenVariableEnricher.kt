package ru.hits.bdui.parser

import com.fasterxml.jackson.databind.JsonNode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.hits.bdui.domain.ErrorContent
import ru.hits.bdui.domain.Screen
import ru.hits.bdui.domain.ScreenResponse
import ru.hits.bdui.parser.visitor.VariableReplacer
import java.util.concurrent.CompletableFuture

interface ScreenVariableEnricher {
    fun enrich(screen: Screen): CompletableFuture<Response>

    sealed interface Response {
        data class Success(val screen: Screen) : Response
        data class Error(val error: ScreenResponse.Error) : Response
    }
}

@Component
class ScreenVariableEnricherImpl(
    private val endpointManager: ScreenEndpointManager
) : ScreenVariableEnricher {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun enrich(screen: Screen): CompletableFuture<ScreenVariableEnricher.Response> =
        endpointManager.getData(screen)
            .thenApply { response ->
                when (response) {
                    is ScreenEndpointManager.Response.Success ->
                        ScreenVariableEnricher.Response.Success(replaceVariables(screen, response.data))

                    is ScreenEndpointManager.Response.NothingToRequest ->
                        ScreenVariableEnricher.Response.Success(screen)

                    is ScreenEndpointManager.Response.Error ->
                        ScreenVariableEnricher.Response.Error(response.error)
                }
            }
            .exceptionally { error ->
                log.error("При получении данных для насыщения экрана произошла ошибка", error)

                ScreenVariableEnricher.Response.Error(
                    ScreenResponse.Error(
                        listOf(ErrorContent.emerge("Не удалось наполнить экран данными из переменных"))
                    )
                )
            }

    private fun replaceVariables(
        screen: Screen,
        responseNameToResult: Map<String, JsonNode>
    ): Screen {
        if (responseNameToResult.isEmpty()) {
            log.info("Отсутствует данные для подмены переменных. Пропуск подмены")
            return screen
        }

        val variableReplacer = VariableReplacer(responseNameToResult)

        val enrichedScreen = screen.copy(
            components = screen.components.map { it.accept(variableReplacer) }
        )

        return enrichedScreen
    }
}
package ru.hits.bdui.client.screen

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.screen.ScreenService
import ru.hits.bdui.client.screen.models.RenderScreenByIdRequestModel
import ru.hits.bdui.client.screen.models.RenderScreenRequestModel
import ru.hits.bdui.common.exceptions.BadRequestException
import ru.hits.bdui.domain.ScreenId
import ru.hits.bdui.domain.ScreenName
import ru.hits.bdui.domain.screen.ScreenFromDatabase
import ru.hits.bdui.engine.api.ExternalApiManager
import ru.hits.bdui.engine.api.ExternalApisCallResult
import ru.hits.bdui.engine.expression.JSInterpreter
import ru.hits.bdui.engine.screen.ScreenRenderer
import ru.hits.bdui.engine.setVariables

interface ScreenRenderService {
    fun renderScreen(request: RenderScreenRequestModel): Mono<ScreenFromDatabase>
    fun renderScreenById(request: RenderScreenByIdRequestModel): Mono<ScreenFromDatabase>
}

@Component
class ScreenRenderServiceImpl(
    private val externalApiManager: ExternalApiManager,
    private val screenRenderer: ScreenRenderer,
    private val screenService: ScreenService,
    private val objectMapper: ObjectMapper,
) : ScreenRenderService {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun renderScreen(request: RenderScreenRequestModel): Mono<ScreenFromDatabase> =
        screenService.findByName(ScreenName(request.screenName))
            .flatMap { meta ->
                if (meta.versionId == null)
                    throw BadRequestException("Для экрана ${request.screenName} не установлена версия для рендеринга")

                screenService.find(meta.id, meta.versionId)
            }
            .enrichScreen(request.variables)
            .doOnError { throwable ->
                log.error("Ошибка при рендеринге экрана ${request.screenName}", throwable)
            }


    override fun renderScreenById(request: RenderScreenByIdRequestModel): Mono<ScreenFromDatabase> =
        screenService.find(ScreenId(request.screenId), request.versionId)
            .enrichScreen(request.variables)
            .doOnError { throwable ->
                log.error(
                    "Ошибка при рендеринге экрана c id = ${request.screenId} и version = ${request.versionId}",
                    throwable
                )
            }

    private fun Mono<ScreenFromDatabase>.enrichScreen(variables: Map<String, JsonNode>): Mono<ScreenFromDatabase> {
        val interpreter = JSInterpreter(objectMapper)
        interpreter.setVariables(variables)

        return this
            .flatMap { screen ->
                externalApiManager.getData(interpreter, screen.screen.apis)
                    .map { apiData ->
                        when (apiData) {
                            is ExternalApisCallResult.Success -> interpreter.setVariables(apiData.data)
                            is ExternalApisCallResult.Error -> throw apiData.error
                        }
                    }
                    .then(screen.toMono())
            }
            .map { screen ->
                screenRenderer.renderScreen(screen, interpreter)
            }
    }
}

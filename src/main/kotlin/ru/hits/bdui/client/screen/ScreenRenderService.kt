package ru.hits.bdui.client.screen

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.screen.ScreenService
import ru.hits.bdui.client.screen.models.RenderScreenRequestModel
import ru.hits.bdui.domain.screen.ScreenFromDatabase
import ru.hits.bdui.engine.api.ExternalApiManager
import ru.hits.bdui.engine.api.ExternalApisCallResult
import ru.hits.bdui.engine.expression.JSInterpreter
import ru.hits.bdui.engine.screen.ScreenRenderer
import ru.hits.bdui.engine.setVariables

interface ScreenRenderService {
    fun renderScreen(request: RenderScreenRequestModel): Mono<ScreenFromDatabase>
}

@Component
class ScreenRenderServiceImpl(
    private val externalApiManager: ExternalApiManager,
    private val screenRenderer: ScreenRenderer,
    private val screenService: ScreenService,
    private val objectMapper: ObjectMapper,
) : ScreenRenderService {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun renderScreen(request: RenderScreenRequestModel): Mono<ScreenFromDatabase> {
        val interpreter = JSInterpreter(objectMapper)
        interpreter.setVariables(request.variables)

        return screenService.findAllLikeName(request.screenName)
            .flatMapIterable { it }
            .next()
            .flatMap { meta ->
                screenService.find(meta.id, requireNotNull(meta.versionId))
            }
            .flatMap { screen ->
                externalApiManager.getData(interpreter, screen.screen.apis)
                    .map { apiData ->
                        when (apiData) {
                            is ExternalApisCallResult.Error -> throw apiData.error
                            is ExternalApisCallResult.Success -> interpreter.setVariables(apiData.data)
                        }
                    }
                    .then(Mono.just(screen))
            }
            .map { screen ->
                screenRenderer.renderScreen(screen, interpreter)
            }.doOnError { throwable ->
                log.error("Error rendering screen ${request.screenName}", throwable)
            }
    }
}

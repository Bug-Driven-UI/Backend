package ru.hits.bdui.client.screen.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.hits.bdui.client.screen.ScreenRenderService
import ru.hits.bdui.client.screen.controller.raw.RenderScreenRequestRaw
import ru.hits.bdui.client.screen.controller.raw.RenderedScreenRawWrapper
import ru.hits.bdui.client.screen.controller.raw.utils.emerge
import ru.hits.bdui.client.screen.models.RenderScreenRequestModel
import ru.hits.bdui.common.models.api.DataModel
import ru.hits.bdui.utils.doOnNextWithMeasure

@RestController
class ScreenRenderController(
    private val screenRenderService: ScreenRenderService,
    private val metrics: ScreenRenderMetrics
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/v1/screen/render")
    fun renderScreen(@RequestBody request: DataModel<RenderScreenRequestRaw>): Mono<RenderedScreenRawWrapper> =
        screenRenderService.renderScreen(RenderScreenRequestModel.emerge(request.data))
            .map { RenderedScreenRawWrapper.emerge(it) }
            .doOnNextWithMeasure { duration, response ->
                log.info("Экран был зарендерен за {} мс", duration.toMillis())
                metrics.incrementMetrics(response.screen.screenName, duration)
            }
}

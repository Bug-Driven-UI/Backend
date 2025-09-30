package ru.hits.bdui.client.screen.controller

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

@RestController
class ScreenRenderController(
    private val screenRenderService: ScreenRenderService,
) {

    @PostMapping("/v1/screen/render")
    fun renderScreen(@RequestBody request: DataModel<RenderScreenRequestRaw>): Mono<RenderedScreenRawWrapper> {
        return screenRenderService.renderScreen(RenderScreenRequestModel.emerge(request.data))
            .map { RenderedScreenRawWrapper.emerge(it) }
    }
}

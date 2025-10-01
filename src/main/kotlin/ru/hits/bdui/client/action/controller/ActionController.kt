package ru.hits.bdui.client.action.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hits.bdui.client.action.ActionHandler
import ru.hits.bdui.client.action.controller.raw.ExecuteActionsRequestRaw
import ru.hits.bdui.client.action.controller.raw.ExecuteActionsResponseRaw
import ru.hits.bdui.common.exceptions.BadRequestException

@RestController
class ActionController(
    handlers: List<ActionHandler>
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val typeToHandler = handlers.associateBy { it.type }

    @PostMapping("/v1/screen/action")
    fun handleAction(@RequestBody request: ExecuteActionsRequestRaw): Mono<ExecuteActionsResponseRaw> =
        Flux.fromIterable(request.actions)
            .flatMap { action ->
                val handler = typeToHandler[action.type]
                    ?: throw BadRequestException("Указан несуществующий тип действия ${action.type}")

                handler.execute(action)
                    .map { action }
            }
            .collectList()
            .map { }

}
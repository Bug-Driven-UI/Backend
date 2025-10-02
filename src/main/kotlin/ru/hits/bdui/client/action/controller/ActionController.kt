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
import ru.hits.bdui.utils.doOnNextWithMeasure

@RestController
class ActionController(
    handlers: List<ActionHandler>
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val typeToHandler = handlers.associateBy { it.type }

    @PostMapping("/v1/screen/action")
    fun handleAction(@RequestBody request: ExecuteActionsRequestRaw): Mono<ExecuteActionsResponseRaw> {
        val (updateScreens, others) = request.actions.partition { it.type == "updateScreen" }

        return Flux.fromIterable(others)
            .doOnSubscribe {
                log.info(
                    "Получен запрос на обработку действий ({} всего, {} updateScreen в конце)",
                    request.actions.size, updateScreens.size
                )
            }
            .flatMap { action ->
                val handler = typeToHandler[action.type]
                    ?: throw BadRequestException("Указан несуществующий тип действия ${action.type}")
                handler.execute(action)
            }
            .collectList()
            .flatMap { list ->
                Flux.fromIterable(updateScreens)
                    .doOnSubscribe { log.info("Выполняем действия на обновление экрана") }
                    .flatMap { action ->
                        val handler = typeToHandler[action.type]
                            ?: throw BadRequestException("Указан несуществующий тип действия ${action.type}")

                        handler.execute(action)
                    }
                    .collectList()
                    .map { it + list }
            }
            .map { list -> ExecuteActionsResponseRaw(list) }
            .doOnNextWithMeasure { duration, _ ->
                log.info("Действия успешно обработаны за {} мс", duration.toMillis())
            }
    }
}
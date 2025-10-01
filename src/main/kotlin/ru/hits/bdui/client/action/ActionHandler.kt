package ru.hits.bdui.client.action

import reactor.core.publisher.Mono
import ru.hits.bdui.client.action.models.ExecutableActionRaw

interface ActionHandler {
    val type: String

    fun execute(action: ExecutableActionRaw): Mono<ActionResponse>
}

interface ActionResponse
package ru.hits.bdui.client.action

import reactor.core.publisher.Mono
import ru.hits.bdui.client.action.controller.raw.actions.request.ActionRawRequest

interface ActionHandler {
    val type: String

    fun execute(action: ActionRawRequest): Mono<ActionResponse>
}

interface ActionResponse
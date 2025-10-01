package ru.hits.bdui.client.action

import reactor.core.publisher.Mono
import ru.hits.bdui.client.action.controller.raw.actions.request.ActionRawRequest
import ru.hits.bdui.client.action.controller.raw.actions.response.ActionResponseRaw

interface ActionHandler {
    val type: String

    fun execute(action: ActionRawRequest): Mono<ActionResponseRaw>
}

package ru.hits.bdui.client.action.controller.raw

import ru.hits.bdui.client.action.controller.raw.actions.request.ActionRawRequest

data class ExecuteActionsRequestRaw(
    val actions: List<ActionRawRequest>,
)
package ru.hits.bdui.client.screen.controller.raw.utils

import ru.hits.bdui.client.screen.controller.raw.RenderScreenRequestRaw
import ru.hits.bdui.client.screen.models.RenderScreenRequestModel

fun RenderScreenRequestModel.Companion.emerge(request: RenderScreenRequestRaw): RenderScreenRequestModel {
    return RenderScreenRequestModel(
        screenName = request.screenName,
        variables = request.variables ?: emptyMap(),
    )
}

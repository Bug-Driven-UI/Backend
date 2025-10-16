package ru.hits.bdui.client.screen.controller.raw.utils

import ru.hits.bdui.client.screen.controller.raw.RenderScreenByIdRequestRaw
import ru.hits.bdui.client.screen.controller.raw.RenderScreenRequestRaw
import ru.hits.bdui.client.screen.models.RenderScreenByIdRequestModel
import ru.hits.bdui.client.screen.models.RenderScreenRequestModel

fun RenderScreenRequestModel.Companion.emerge(request: RenderScreenRequestRaw): RenderScreenRequestModel =
    RenderScreenRequestModel(
        screenName = request.screenName,
        variables = request.variables ?: emptyMap(),
    )

fun RenderScreenByIdRequestModel.Companion.emerge(request: RenderScreenByIdRequestRaw): RenderScreenByIdRequestModel =
    RenderScreenByIdRequestModel(
        screenId = request.screenId,
        versionId = request.versionId,
        variables = request.variables ?: emptyMap(),
    )

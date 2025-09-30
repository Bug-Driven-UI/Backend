package ru.hits.bdui.client.screen.controller.raw

import ru.hits.bdui.common.models.client.raw.components.RenderedComponentRaw

data class RenderedScaffoldRaw(
    val topBar: RenderedComponentRaw?,
    val bottomBar: RenderedComponentRaw?,
)

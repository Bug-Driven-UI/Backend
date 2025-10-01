package ru.hits.bdui.client.screen.controller.raw.utils

import ru.hits.bdui.client.screen.controller.raw.RenderedScaffoldRaw
import ru.hits.bdui.client.screen.controller.raw.RenderedScreenRaw
import ru.hits.bdui.client.screen.controller.raw.RenderedScreenRawWrapper
import ru.hits.bdui.common.models.client.raw.utils.toRaw
import ru.hits.bdui.domain.screen.ScreenFromDatabase

fun RenderedScreenRawWrapper.Companion.emerge(screen: ScreenFromDatabase): RenderedScreenRawWrapper =
    RenderedScreenRawWrapper(
        screen = RenderedScreenRaw(
            screenName = screen.screen.screenName.value,
            version = screen.version,
            components = screen.screen.components.map { it.toRaw() },
            scaffold = RenderedScaffoldRaw(
                topBar = screen.screen.scaffold?.topBar?.toRaw(),
                bottomBar = screen.screen.scaffold?.bottomBar?.toRaw(),
            )
        ),
    )

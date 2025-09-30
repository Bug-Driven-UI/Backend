package ru.hits.bdui.domain.screen.meta

import ru.hits.bdui.domain.ScreenName
import java.util.UUID

data class ScreenMeta(
    val name: ScreenName,
    val description: String,
    val versionId: UUID?,
)
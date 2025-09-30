package ru.hits.bdui.admin.screen.controller.raw.update

import ru.hits.bdui.admin.screen.controller.raw.ScreenRaw
import java.util.UUID

data class ScreenUpdateRequestRaw(
    val screenId: UUID,
    val versionId: UUID,
    val screen: ScreenRaw
)
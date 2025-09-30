package ru.hits.bdui.admin.screen.models

import ru.hits.bdui.domain.ScreenId
import ru.hits.bdui.domain.screen.Screen
import java.util.UUID

data class ScreenUpdateCommand(
    val screenId: ScreenId,
    val versionId: UUID,
    val screen: Screen
)
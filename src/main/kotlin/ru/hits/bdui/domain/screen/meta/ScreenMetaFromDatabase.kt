package ru.hits.bdui.domain.screen.meta

import ru.hits.bdui.domain.ScreenId
import ru.hits.bdui.domain.ScreenName
import java.util.UUID

data class ScreenMetaFromDatabase(
    val id: ScreenId,
    val name: ScreenName,
    val description: String,
    val versionId: UUID?,
) {
    companion object
}
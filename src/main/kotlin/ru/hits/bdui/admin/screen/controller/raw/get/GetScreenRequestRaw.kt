package ru.hits.bdui.admin.screen.controller.raw.get

import java.util.UUID

data class GetScreenRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val screenId: UUID,
        val versionId: UUID,
    )
}
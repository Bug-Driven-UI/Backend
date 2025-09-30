package ru.hits.bdui.admin.screen.controller.raw.update

import java.util.UUID

data class SetProductionVersionRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val screenId: UUID,
        val versionId: UUID,
    )
}
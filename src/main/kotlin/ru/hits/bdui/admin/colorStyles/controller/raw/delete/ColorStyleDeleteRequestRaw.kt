package ru.hits.bdui.admin.colorStyles.controller.raw.delete

import java.util.UUID

data class ColorStyleDeleteRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID
    )
}

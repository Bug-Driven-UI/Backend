package ru.hits.bdui.admin.colorStyles.controller.raw.get

import java.util.UUID

data class ColorStyleGetRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID
    )
}

package ru.hits.bdui.textStyles.controller.raw.get

import java.util.UUID

data class TextStyleGetRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID
    )
}

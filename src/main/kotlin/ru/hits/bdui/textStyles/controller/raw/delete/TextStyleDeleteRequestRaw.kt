package ru.hits.bdui.textStyles.controller.raw.delete

import java.util.UUID

data class TextStyleDeleteRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID
    )
}

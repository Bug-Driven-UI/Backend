package ru.hits.bdui.textStyles.controller.raw.update

import ru.hits.bdui.textStyles.controller.raw.TextStyleForSaveRaw
import java.util.UUID

data class TextStyleUpdateRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID,
        val textStyle: TextStyleForSaveRaw
    )
}

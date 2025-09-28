package ru.hits.bdui.admin.colorStyles.controller.raw.update

import ru.hits.bdui.admin.colorStyles.controller.raw.ColorStyleForSaveRaw
import java.util.UUID

data class ColorStyleUpdateRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID,
        val colorStyle: ColorStyleForSaveRaw
    )
}

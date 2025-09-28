package ru.hits.bdui.textStyles.controller.raw.save

import ru.hits.bdui.textStyles.controller.raw.TextStyleForSaveRaw

data class TextStyleSaveRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val textStyle: TextStyleForSaveRaw
    )
}

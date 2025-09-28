package ru.hits.bdui.admin.textStyles.controller.raw.save

import ru.hits.bdui.admin.textStyles.controller.raw.TextStyleForSaveRaw

data class TextStyleSaveRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val textStyle: TextStyleForSaveRaw
    )
}

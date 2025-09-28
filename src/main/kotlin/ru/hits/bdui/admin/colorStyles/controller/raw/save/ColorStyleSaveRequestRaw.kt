package ru.hits.bdui.admin.colorStyles.controller.raw.save

import ru.hits.bdui.admin.colorStyles.controller.raw.ColorStyleForSaveRaw

data class ColorStyleSaveRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val colorStyle: ColorStyleForSaveRaw
    )
}

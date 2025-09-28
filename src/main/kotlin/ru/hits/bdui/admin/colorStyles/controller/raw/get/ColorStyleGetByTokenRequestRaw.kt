package ru.hits.bdui.admin.colorStyles.controller.raw.get

data class ColorStyleGetByTokenRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val query: String,
    )
}

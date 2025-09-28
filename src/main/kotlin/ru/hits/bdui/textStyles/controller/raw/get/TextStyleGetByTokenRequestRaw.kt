package ru.hits.bdui.textStyles.controller.raw.get

data class TextStyleGetByTokenRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val query: String,
    )
}

package ru.hits.bdui.admin.colorStyles.controller.raw

import java.util.UUID

data class ColorStyleRaw(
    val id: UUID,
    val token: String,
    val color: String
) {
    companion object
}

data class ColorStyleForSaveRaw(
    val token: String,
    val color: String
)
package ru.hits.bdui.textStyles.controller.raw

import java.util.UUID

data class TextStyleRaw(
    val id: UUID,
    val token: String,
    val size: Int,
    val weight: Int?,
    val lineHeight: Int,
    val decoration: String?
)

data class TextStyleForSaveRaw(
    val token: String,
    val size: Int,
    val weight: Int?,
    val lineHeight: Int,
    val decoration: String?
)
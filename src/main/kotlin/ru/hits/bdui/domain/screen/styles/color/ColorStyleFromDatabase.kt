package ru.hits.bdui.domain.screen.styles.color

import java.util.UUID

data class ColorStyleFromDatabase(
    val id: UUID = UUID.randomUUID(),
    val colorStyle: ColorStyle
) {
    companion object
}
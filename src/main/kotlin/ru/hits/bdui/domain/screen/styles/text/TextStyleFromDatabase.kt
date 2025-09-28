package ru.hits.bdui.domain.screen.styles.text

import java.util.UUID

data class TextStyleFromDatabase(
    val id: UUID,
    val textStyle: TextStyle,
) {
    companion object
}
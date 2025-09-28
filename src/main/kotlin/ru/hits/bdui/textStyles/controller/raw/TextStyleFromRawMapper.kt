package ru.hits.bdui.textStyles.controller.raw

import ru.hits.bdui.domain.screen.styles.text.TextDecoration
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextStyleFromDatabase
import ru.hits.bdui.exceptions.BadRequestException
import java.util.UUID

object TextStyleFromRawMapper {
    fun TextStyle(raw: TextStyleForSaveRaw): TextStyle =
        TextStyle(
            token = raw.token,
            decoration = raw.decoration?.let(::extractDecoration),
            weight = raw.weight,
            size = raw.size,
            lineHeight = raw.lineHeight
        )

    fun TextStyleFromDatabase(id: UUID, raw: TextStyleForSaveRaw): TextStyleFromDatabase =
        TextStyleFromDatabase(
            id = id,
            textStyle = TextStyle(
                token = raw.token,
                decoration = raw.decoration?.let(::extractDecoration),
                weight = raw.weight,
                size = raw.size,
                lineHeight = raw.lineHeight
            )
        )

    private fun extractDecoration(decoration: String): TextDecoration =
        runCatching {
            decoration.let { TextDecoration.valueOf(it.uppercase()) }
        }
            .getOrElse { throw BadRequestException("Неопознанный тип декорации текста: $decoration") }
}
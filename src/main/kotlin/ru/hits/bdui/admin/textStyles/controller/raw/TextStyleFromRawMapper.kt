package ru.hits.bdui.admin.textStyles.controller.raw

import ru.hits.bdui.domain.screen.styles.text.TextDecoration
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextStyleFromDatabase
import java.util.UUID

object TextStyleFromRawMapper {
    fun TextStyle(raw: TextStyleForSaveRaw): TextStyle =
        TextStyle(
            token = raw.token,
            decoration = raw.decoration?.let(TextStyleFromRawMapper::TextDecoration),
            weight = raw.weight,
            size = raw.size,
            lineHeight = raw.lineHeight
        )

    fun TextStyleFromDatabase(id: UUID, raw: TextStyleForSaveRaw): TextStyleFromDatabase =
        TextStyleFromDatabase(
            id = id,
            textStyle = TextStyle(
                token = raw.token,
                decoration = raw.decoration?.let(TextStyleFromRawMapper::TextDecoration),
                weight = raw.weight,
                size = raw.size,
                lineHeight = raw.lineHeight
            )
        )

    fun TextDecoration(decoration: TextDecorationRaw): TextDecoration =
        when (decoration) {
            TextDecorationRaw.BOLD -> TextDecoration.BOLD
            TextDecorationRaw.ITALIC -> TextDecoration.ITALIC
            TextDecorationRaw.UNDERLINE -> TextDecoration.UNDERLINE
            TextDecorationRaw.STRIKETHROUGH -> TextDecoration.STRIKETHROUGH
            TextDecorationRaw.STRIKETHROUGH_RED -> TextDecoration.STRIKETHROUGH_RED
            TextDecorationRaw.REGULAR -> TextDecoration.REGULAR
            TextDecorationRaw.OVERLINE -> TextDecoration.OVERLINE
        }
}
package ru.hits.bdui.admin.textStyles.controller.raw

import ru.hits.bdui.domain.screen.styles.text.TextDecoration
import ru.hits.bdui.domain.screen.styles.text.TextStyleFromDatabase

fun TextStyleRaw.Companion.of(data: TextStyleFromDatabase): TextStyleRaw =
    TextStyleRaw(
        id = data.id,
        token = data.textStyle.token,
        size = data.textStyle.size,
        weight = data.textStyle.weight,
        lineHeight = data.textStyle.lineHeight,
        decoration = data.textStyle.decoration?.toRaw()
    )

private fun TextDecoration.toRaw(): TextDecorationRaw =
    when (this) {
        TextDecoration.ITALIC -> TextDecorationRaw.ITALIC
        TextDecoration.UNDERLINE -> TextDecorationRaw.UNDERLINE
        TextDecoration.STRIKETHROUGH -> TextDecorationRaw.STRIKETHROUGH
        TextDecoration.STRIKETHROUGH_RED -> TextDecorationRaw.STRIKETHROUGH_RED
        TextDecoration.REGULAR -> TextDecorationRaw.REGULAR
        TextDecoration.OVERLINE -> TextDecorationRaw.OVERLINE
    }


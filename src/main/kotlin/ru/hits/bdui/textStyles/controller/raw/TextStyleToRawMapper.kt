package ru.hits.bdui.textStyles.controller.raw

import ru.hits.bdui.domain.screen.styles.text.TextStyleFromDatabase

object TextStyleToRawMapper {
    fun TextStyleRaw(data: TextStyleFromDatabase): TextStyleRaw =
        TextStyleRaw(
            id = data.id,
            token = data.textStyle.token,
            size = data.textStyle.size,
            weight = data.textStyle.weight,
            lineHeight = data.textStyle.lineHeight,
            decoration = data.textStyle.decoration?.name
        )
}
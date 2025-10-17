package ru.hits.bdui.domain.screen.styles.text

import ru.hits.bdui.domain.ValueOrExpression
import ru.hits.bdui.domain.screen.styles.color.ColorStyle

data class TextWithStyle(
    val text: ValueOrExpression,
    val textStyle: TextStyle,
    val color: ColorStyle,
    val textAlignment: TextAlignment?
)

enum class TextAlignment {
    START,
    CENTER,
    END
}
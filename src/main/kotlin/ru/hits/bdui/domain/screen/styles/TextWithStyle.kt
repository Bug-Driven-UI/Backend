package ru.hits.bdui.domain.screen.styles

import ru.hits.bdui.domain.ValueOrExpression

data class TextWithStyle(
    val text: ValueOrExpression,
    val textStyle: TextStyle,
    val color: ColorStyle,
)
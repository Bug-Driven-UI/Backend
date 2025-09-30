package ru.hits.bdui.domain.screen.styles.color

import ru.hits.bdui.domain.ValueOrExpression

/**
 * Стиль цвета
 *
 * @property token - токен цвета, хранящийся в бд
 * @property color - hex значение цвета
 */
data class ColorStyle(
    val token: ValueOrExpression,
    val color: ValueOrExpression,
)

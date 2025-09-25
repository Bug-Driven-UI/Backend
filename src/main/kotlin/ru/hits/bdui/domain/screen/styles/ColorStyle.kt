package ru.hits.bdui.domain.screen.styles

/**
 * Стиль цвета
 *
 * @property token - токен цвета, хранящийся в бд
 * @property color - hex значение цвета
 */
data class ColorStyle(
    val token: String,
    val color: String
)
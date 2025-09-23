package ru.hits.bdui.domain.screen.styles

/**
 * @param token - токен цвета, хранящийся в бд
 * @param color - hex значение цвета
 */
data class ColorStyle(
    val token: String,
    val color: String
)
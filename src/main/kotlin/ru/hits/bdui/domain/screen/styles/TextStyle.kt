package ru.hits.bdui.domain.screen.styles

/**
 * Стиль текста
 *
 * @property token токен стиля
 * @property decoration декорация текста (italic и т.д)
 * @property weight жирность текста
 * @property size размер текста
 */
data class TextStyle(
    val token: String,
    val decoration: String,
    val weight: String,
    val size: Int
)

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
    val decoration: TextDecoration?,
    val weight: Int?,
    val size: Int
)

enum class TextDecoration {
    BOLD,
    ITALIC,
    UNDERLINE,
    STRIKETHROUGH,
    OVERLINE,
    STRIKETHROUGH_RED
}

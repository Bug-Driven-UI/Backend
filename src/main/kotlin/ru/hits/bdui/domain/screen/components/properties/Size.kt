package ru.hits.bdui.domain.screen.components.properties

sealed interface Size {
    /**
     * @property value >= 0
     */
    data class Fixed(
        val value: Int
    ) : Size

    /**
     * @property fraction значение в диапазоне от 0 до 1 (включительно)
     */
    data class Weighted(
        val fraction: Double
    ) : Size

    data object MatchParent : Size

    data object WrapContent : Size
}
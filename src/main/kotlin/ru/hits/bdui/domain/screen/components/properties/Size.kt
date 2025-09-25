package ru.hits.bdui.domain.screen.components.properties

sealed interface Size {
    val type: String

    /**
     * @property value >= 0
     */
    data class Fixed(
        val value: Int
    ) : Size {
        override val type: String = "fixed"
    }

    /**
     * @property fraction значение в диапазоне от 0 до 1 (включительно)
     */
    data class Weighted(
        val fraction: Int
    ) : Size {
        override val type: String = "weighted"
    }

    class MatchParent : Size {
        override val type: String = "matchParent"
    }

    class WrapContent : Size {
        override val type: String = "wrapContent"
    }
}
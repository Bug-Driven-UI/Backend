package ru.hits.bdui.domain.screen.properties

sealed interface Size {
    val type: String

    data class Fixed(
        val value: Int
    ) : Size {
        override val type: String = "fixed"
    }

    data class Weighted(
        val value: Int
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
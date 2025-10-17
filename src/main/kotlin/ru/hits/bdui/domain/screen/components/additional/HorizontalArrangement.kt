package ru.hits.bdui.domain.screen.components.additional

/**
 * Отвечает за расположение детей в Row
 */
sealed interface HorizontalArrangement {
    val type: String

    data object Start : HorizontalArrangement {
        override val type: String = "start"
    }

    data object End : HorizontalArrangement {
        override val type: String = "end"
    }

    data object Center : HorizontalArrangement {
        override val type: String = "center"
    }

    data object SpaceBetween : HorizontalArrangement {
        override val type: String = "spaceBetween"
    }

    data object SpaceEvenly : HorizontalArrangement {
        override val type: String = "spaceEvenly"
    }

    data object SpaceAround : HorizontalArrangement {
        override val type: String = "spaceAround"
    }
}
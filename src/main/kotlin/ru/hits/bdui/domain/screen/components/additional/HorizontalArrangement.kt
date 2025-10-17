package ru.hits.bdui.domain.screen.components.additional

/**
 * Отвечает за расположение детей в Row
 */
sealed interface HorizontalArrangement {
    data object Start : HorizontalArrangement
    data object End : HorizontalArrangement
    data object Center : HorizontalArrangement
    data object SpaceBetween : HorizontalArrangement
    data object SpaceEvenly : HorizontalArrangement
    data object SpaceAround : HorizontalArrangement
}
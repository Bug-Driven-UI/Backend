package ru.hits.bdui.domain.screen.components.additional

/**
 * Отвечает за расположение детей в Column
 */
sealed interface VerticalArrangement {
    data object Top : VerticalArrangement
    data object Bottom : VerticalArrangement
    data object Center : VerticalArrangement
    data object SpaceBetween : VerticalArrangement
    data object SpaceEvenly : VerticalArrangement
    data object SpaceAround : VerticalArrangement
}
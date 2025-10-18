package ru.hits.bdui.domain.screen.interactions.actions

/**
 * Действие, отвечающее за навигацию обратно
 */
data class NavigateBackAction(
    val updatePreviousScreen: Boolean
) : LocalAction
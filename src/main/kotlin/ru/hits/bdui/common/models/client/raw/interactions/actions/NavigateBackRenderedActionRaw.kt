package ru.hits.bdui.common.models.client.raw.interactions.actions

/**
 * Действие, для перехода на предыдущий экран
 */
data class NavigateBackRenderedActionRaw(
    val updatePreviousScreen: Boolean
) : RenderedActionRaw

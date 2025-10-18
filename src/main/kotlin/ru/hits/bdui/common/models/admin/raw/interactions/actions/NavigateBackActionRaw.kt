package ru.hits.bdui.common.models.admin.raw.interactions.actions

/**
 * Действие, для перехода на предыдущий экран
 */
data class NavigateBackActionRaw(
    val updatePreviousScreen: Boolean
) : ActionRaw
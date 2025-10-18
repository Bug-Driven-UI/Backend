package ru.hits.bdui.common.models.admin.entity.interactions.actions

/**
 * Действие, для перехода на предыдущий экран
 */
data class NavigateBackActionEntity(
    val updatePreviousScreen: Boolean = false,
) : ActionEntity
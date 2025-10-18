package ru.hits.bdui.common.models.admin.entity.interactions.actions

/**
 * Действие, отвечающее за установку локального состояния из инпута
 */
data class SetLocalStateFromInputActionEntity(
    val target: String,
) : ActionEntity
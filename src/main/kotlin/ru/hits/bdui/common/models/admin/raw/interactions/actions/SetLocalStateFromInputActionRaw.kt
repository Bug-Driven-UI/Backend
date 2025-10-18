package ru.hits.bdui.common.models.admin.raw.interactions.actions

/**
 * Действие, отвечающее за установку локального состояния из инпута
 */
data class SetLocalStateFromInputActionRaw(
    val target: String,
) : ActionRaw
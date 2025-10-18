package ru.hits.bdui.common.models.client.raw.interactions.actions

/**
 * Действие, отвечающее за установку локального состояния из инпута
 */
data class SetLocalStateFromInputRenderedActionRaw(
    val target: String,
) : RenderedActionRaw
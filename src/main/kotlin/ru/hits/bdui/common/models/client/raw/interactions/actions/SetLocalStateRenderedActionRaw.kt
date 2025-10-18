package ru.hits.bdui.common.models.client.raw.interactions.actions

/**
 * Действие, отвечающее за установку локального состояния
 */
data class SetLocalStateRenderedActionRaw(
    val target: String,
    val value: String
) : RenderedActionRaw
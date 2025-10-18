package ru.hits.bdui.common.models.admin.raw.interactions.actions

/**
 * Действие, отвечающее за установку локального состояния из инпута
 */
data class SetLocalStateActionRaw(
    val target: String,
    val value: String
) : ActionRaw
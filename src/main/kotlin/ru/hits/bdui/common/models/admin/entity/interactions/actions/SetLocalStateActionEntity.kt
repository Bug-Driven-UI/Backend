package ru.hits.bdui.common.models.admin.entity.interactions.actions

/**
 * Действие, отвечающее за установку локального состояния
 */
data class SetLocalStateActionEntity(
    val target: String,
    val value: String
) : ActionEntity
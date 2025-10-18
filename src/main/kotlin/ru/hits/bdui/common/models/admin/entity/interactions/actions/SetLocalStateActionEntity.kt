package ru.hits.bdui.common.models.admin.entity.interactions.actions

/**
 * Действие, отвечающее за установку локального состояния из инпута
 */
data class SetLocalStateActionEntity(
    val target: String,
    val value: String
) : ActionEntity
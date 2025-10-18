package ru.hits.bdui.domain.screen.interactions.actions

import ru.hits.bdui.domain.ValueOrExpression

/**
 * Действие, отвечающее за установку локального состояния
 */
data class SetLocalStateAction(
    val target: ValueOrExpression,
    val value: ValueOrExpression
) : LocalAction
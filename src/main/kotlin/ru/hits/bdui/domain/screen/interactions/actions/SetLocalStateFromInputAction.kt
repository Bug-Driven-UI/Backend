package ru.hits.bdui.domain.screen.interactions.actions

import ru.hits.bdui.domain.ValueOrExpression

/**
 * Действие, отвечающее за установку локального состояния из инпута
 */
data class SetLocalStateFromInputAction(
    val target: ValueOrExpression,
) : LocalAction
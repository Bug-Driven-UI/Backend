package ru.hits.bdui.domain.screen.interactions

import ru.hits.bdui.domain.screen.interactions.actions.Action

data class Interaction(
    val type: InteractionType,
    val actions: List<Action>
)

enum class InteractionType {
    ON_CLICK,
    ON_SHOW
}
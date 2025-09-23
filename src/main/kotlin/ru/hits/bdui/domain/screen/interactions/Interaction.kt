package ru.hits.bdui.domain.screen.interactions

import ru.hits.bdui.domain.screen.interactions.actions.Action

sealed interface Interaction {
    val type: String
    val actions: List<Action>
}
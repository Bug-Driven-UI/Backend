package ru.hits.bdui.domain.components

sealed interface Interaction {
    val type: String
    val actions: List<Action>
}
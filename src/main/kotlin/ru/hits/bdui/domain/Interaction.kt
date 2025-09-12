package ru.hits.bdui.domain

sealed interface Interaction {
    val type: String
    val actions: List<Action>
}
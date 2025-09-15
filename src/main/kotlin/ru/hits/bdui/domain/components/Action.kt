package ru.hits.bdui.domain.components

sealed interface Action {
    val type: String
}
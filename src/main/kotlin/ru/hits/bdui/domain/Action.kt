package ru.hits.bdui.domain

sealed interface Action {
    val type: String
}
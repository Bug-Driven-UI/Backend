package ru.hits.bdui.domain

sealed interface Leaf : Component

data class Text(
    val text: String
) : Leaf

data class TextField(
    val text: String
) : Leaf

data class Image(
    val field: String
) : Leaf

data class Spacer(
    val field: String
) : Leaf

data class Divider(
    val field: String
) : Leaf

data class ProgressBar(
    val field: String
) : Leaf

data class Switch(
    val field: String
) : Leaf

data class Button(
    val text: String,
    val enabled: Boolean
) : Leaf
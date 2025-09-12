package ru.hits.bdui.domain

sealed interface Composite : Component {
    val children: List<Component>
}

data class Row(
    override val children: List<Component>
) : Composite

data class Box(
    override val children: List<Component>
) : Composite

data class Column(
    override val children: List<Component>
) : Composite
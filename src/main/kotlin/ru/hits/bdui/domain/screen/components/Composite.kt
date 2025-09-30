package ru.hits.bdui.domain.screen.components

sealed interface Composite : Component {
    val children: List<Component>
}

data class Row(
    override val children: List<Component>,
    override val base: ComponentBaseProperties,
) : Composite {
    override val type: String = "row"
}

data class Box(
    override val children: List<Component>,
    override val base: ComponentBaseProperties,
) : Composite {
    override val type: String = "box"
}

data class Column(
    override val children: List<Component>,
    override val base: ComponentBaseProperties,
) : Composite {
    override val type: String = "column"
}
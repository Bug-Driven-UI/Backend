package ru.hits.bdui.domain.components

import ru.hits.bdui.domain.components.size.Size
import ru.hits.bdui.parser.visitor.ComponentVisitor

sealed interface Composite : Component {
    val children: List<Component>
}

data class Row(
    override val children: List<Component>,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Composite {
    override val type: String = "row"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class Box(
    override val children: List<Component>,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Composite {
    override val type: String = "box"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class Column(
    override val children: List<Component>,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Composite {
    override val type: String = "column"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}
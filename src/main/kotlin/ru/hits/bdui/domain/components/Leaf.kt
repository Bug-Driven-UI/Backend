package ru.hits.bdui.domain

import ru.hits.bdui.domain.components.size.Size
import ru.hits.bdui.parser.visitor.ComponentVisitor

sealed interface Leaf : Component

data class Text(
    val text: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.visit(this)
}

data class TextField(
    val text: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.visit(this)
}

data class Image(
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class Spacer(
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class Divider(
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class ProgressBar(
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class Switch(
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class Button(
    val text: String,
    val enabled: Boolean,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.visit(this)
}